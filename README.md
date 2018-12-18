# Android-OnBoardingScreen
_An example illustrating an OnBoarding Screen in Android, commonly displayed to first-time users of an app._

When you first install a mobile app, an onboarding screen is usually displayed to users. The purpose of it is to introduce first-time users to the app and what it does. Having an attractive and informative onboarding screen can retain help retain users.

This article will show you how to implement your own onboarding screen in Android using [ViewPager](https://developer.android.com/reference/android/support/v4/view/ViewPager).

**Special thanks to [TVAC Studio](https://www.youtube.com/watch?v=byLKoPgB7yA) for providing the graphics used in this tutorial. (Assets download in the description)**

<br>

<img src="https://camo.githubusercontent.com/5c711f2fb7b2180cba53c3bac7ba470b1ffdfec3/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6d696e53646b56657273696f6e2d32312d7265642e7376673f7374796c653d74727565" alt="minSdkVersion 21" data-canonical-src="https://img.shields.io/badge/minSdkVersion-21-red.svg?style=true" style="max-width:100%;">

<img src=https://img.shields.io/badge/compileSdkVersion-28-brightgreen.svg alt="compileSdkVersion 28" data-canonical-src="https://img.shields.io/badge/compileSdkVersion-27-yellow.svg?style=true" style="max-width:100%;">

## Onboarding Screen In Action

## Tutorial

### Layout

In the [main xml layout](https://github.com/wRorsjakz/Android-OnBoardingScreen/blob/master/app/src/main/res/layout/activity_main.xml), have a `LinearLayout` as the root layout with the attributes:
```xml
android:orientation="vertical"
android:weightSum="100"
```
`weightSum` is useful in ensuring that child views are rendered proportionally to the length of the device screen. 

For child views, declare:
```xml
android:layout_height="0dp"
android:layout_weight="x"
```
Have a `ViewPager` and `RelativeLayout` as child views:

```xml
 <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="bottom"
    android:orientation="vertical"
    android:weightSum="100">

    <android.support.v4.view.ViewPager
        android:id="@+id/onboarding_viewpager_id"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="90">
    </android.support.v4.view.ViewPager>

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="10"
        android:orientation="horizontal">

        <!--- Other Views -->

    </RelativeLayout>

</LinearLayout>
```

In another xml file, create the [slide layout](https://github.com/wRorsjakz/Android-OnBoardingScreen/blob/master/app/src/main/res/layout/onboarding_slide_layout.xml). Mine has an `ImageView` and 2 `TextView`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout 
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <ImageView
        android:id="@+id/onboarding_icon_id"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        />

    <TextView
        android:id="@+id/onboarding_title_id"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        />

    <TextView
        android:id="@+id/onboarding_description_id"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />

</RelativeLayout>
```
### Java code

A [PagerAdapter](https://developer.android.com/reference/android/support/v4/view/PagerAdapter) needs to be passed to the `ViewPager` to generate the pages to be shown. Since the layout of every slide is the same, there is no need to use `fragments`, hence we do not need to use [FragmentPagerAdapter](https://developer.android.com/reference/android/support/v4/app/FragmentPagerAdapter) or [FragmentStatePagerAdapter](https://developer.android.com/reference/android/support/v4/app/FragmentStatePagerAdapter).

[Creating a class that extends PagerAdapter](https://github.com/wRorsjakz/Android-OnBoardingScreen/blob/master/app/src/main/java/com/example/user/onboardingscreensample/OnBoardingScreenAdapter.java), declare a constructor which takes in a `Context`.

```java
public OnBoardingScreenAdapter(Context context) {
        this.context = context;
    }
```

Declare arrays for the content you want to show on each slide.
```java
private int[] icons = {
        //Add drawables
    };
private String[] titles = {
        //Add titles
    };
private String[] descriptions = {
        //Add descriptions         
    };
```
Override `getCount()` which returns the number of views available. Instead of hardcoding it, pass the length of one of the array.
```java
@Override
    public int getCount() {
        return titles.length;
    }
```
Override `instantiateItem()` which instantiates the views as well as set its contents according to the current position of the `ViewPager`, using a `LayoutInflater`.

```java
@NonNull
@Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_name,container,false);

        <!-- Intialise views Here -->

        <!-- Set views here -->

        container.addView(view);
        return view;
    }
```
Set view content using the `position` passed into the method above with the element at that position. For example, for a textview:
```java
textview.setText(titles[position]);
````

In order to free up resources, override `destroyItem()` method.
```java
@Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((layout_type)object);
    }
```
