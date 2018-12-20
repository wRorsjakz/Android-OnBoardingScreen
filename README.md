# Android-OnBoardingScreen
_An example illustrating an OnBoarding Screen in Android, commonly displayed to first-time users of an app._

When you first install a mobile app, an onboarding screen is usually displayed to users. The purpose of it is to introduce first-time users to the app and what it does. Having an attractive and informative onboarding screen can retain help retain users.

This article will show you how to implement your own onboarding screen in Android using [ViewPager](https://developer.android.com/reference/android/support/v4/view/ViewPager).

**Special thanks to [TVAC Studio](https://www.youtube.com/watch?v=byLKoPgB7yA) for providing the graphics used in this tutorial. (Assets download in the description)**

<br>

<img src="https://img.shields.io/badge/minSdkVersion-24-red.svg?style=true" alt="minSdkVersion 21" data-canonical-src="https://img.shields.io/badge/minSdkVersion-24-red.svg?style=true" style="max-width:100%;"> <img src=https://img.shields.io/badge/compileSdkVersion-28-brightgreen.svg alt="compileSdkVersion 28" data-canonical-src="https://img.shields.io/badge/compileSdkVersion-27-yellow.svg?style=true" style="max-width:100%;">

## Onboarding Screen In Action
![finalsample](https://user-images.githubusercontent.com/39665412/50296288-2520a980-04b5-11e9-8d7f-076ba137d7a8.gif)
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

#### PagerAdapter

A [PagerAdapter](https://developer.android.com/reference/android/support/v4/view/PagerAdapter) needs to be passed to the `ViewPager` to generate the pages to be shown. Since the layout of every slide is the same, there is no need to use `fragments`, hence we do not need to use [FragmentPagerAdapter](https://developer.android.com/reference/android/support/v4/app/FragmentPagerAdapter) or [FragmentStatePagerAdapter](https://developer.android.com/reference/android/support/v4/app/FragmentStatePagerAdapter).

In [OnBoardingScreenAdapter.java](https://github.com/wRorsjakz/Android-OnBoardingScreen/blob/master/app/src/main/java/com/example/user/onboardingscreensample/OnBoardingScreenAdapter.java), extend `PagerAdapter` and  declare a constructor which takes in a `Context`.

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

In order to free up resources, override `destroyItem()`.
```java
@Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((layout_type)object);
    }
```
#### MainActivity

Hook an instance of the `PagerAdapter` to the `ViewPager` widget.
```java
adapter = new OnBoardingScreenAdapter(this);
viewPager.setAdapter(adapter);
```
Create an instance `ViewPager.OnPageChangeListener` which is for responding to changing or selected pages in a `ViewPager` and hook it to the instance of it.
```java
viewPager.addOnPageChangeListener(listener);
```
To show the dots which indicates the total number of slides and index of current slide, first create a `TextView` array, using `getCount()` on the adapter to get the number of slides.
```java
int noOfSlides = adapter.getCount();
TextView[] dots = new TextView[noOfSlides];
```
Populate the array with the dots using HTML.
_Note: Min api for `public static Spanned fromHtml (String source, int flags)` is 24._
```java
for (int i = 0; i < noOfSlides; i++){
            dots[i] = new TextView(this);
            //Min api is 24
            dots[i].setText(Html.fromHtml("&#8226",noOfSlides));
            dots[i].setTextSize(35);
            dots[i].setTextColor(ContextCompat.getColor(this,R.color.colorTranslucentWhite));
        }
```
In order to have the dot for the current displayed page be a different color, override `OnPageSelected()` in the `listener` and assign a variable.
```java
private int currentScreenIndex;

// In OnPageSelected()
currentScreenIndex = i
```
Then use this variable for the i index of the array.
```java
 dots[currentScreenIndex].setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
```
One problem I encounted was the number of dots kept increasing when `OnPageSelected()` was called. The simplest solution I found was to simply remove all widgets(the dots) from its parent layout and then re-add it.
```java
private LinearLayout dotsLinearLayout = findViewById(R.id.onboarding_dots_container_id);

// In the listener
{
 @Override
    public void onPageSelected(int i) {
        // Remove all the dots
        dotsLinearLayout.removeAllViews();

        // Add the dots back in after removing them
}
```
When the OnBoarding Screen is at the first slide, the 'Back' button is disabled, while at the last slide, the 'Next' button becomes 'Finish'. To do this, add a Switch statement inside `OnPageSelected()`, with the `currentScreenIndex` as the switch.
```java
if (i == 0){
                nextButton.setText(getString(R.string.next));
                backButton.setText("");
                backButton.setEnabled(false);
                nextButton.setEnabled(true);
            }
            //if onBoardingScreen is at the last screen
            else if (i == noOfScreens-1){
                nextButton.setText(getString(R.string.finish));
                backButton.setText(getString(R.string.back));
                nextButton.setEnabled(true);
                backButton.setEnabled(true);
            }
            // if onBoardingScreen is at the other screens
            else {
                nextButton.setText(getString(R.string.next));
                nextButton.setEnabled(true);
                backButton.setText(getString(R.string.back));
                backButton.setEnabled(true);
            }
```
Hence, the button on the right of the screen does two actions when it is pressed.
- When 'Next' is shown, the next slide is shown
- When 'Finish' is shown, the OnBoarding Screen is done

The easiest method is to simply use the text on the button as the boolean statement in the button's `onClick()`.
```java
// Check what the text on the button is
String buttonText = nextButton.getText().toString();
if (buttonText.equals("Next"){
    // Go to next slide
    viewPager.setCurrentItem(currentScreenIndex + 1,true);
    }
else if (buttonText.equals("Finish")){
    // Do whatever comes after the OnBoarding Screen
    }
```
## Built With
- [Design Support Library](https://developer.android.com/reference/android/support/design/package-summary) - The Design package provides APIs to support adding material design components and patterns to your apps
- [ViewPager](https://developer.android.com/reference/android/support/v4/view/ViewPager) - Layout manager that allows the user to flip left and right through pages of data

## Acknowledgments
- [TVAC Studio](https://www.youtube.com/watch?v=byLKoPgB7yA) for providing the graphics used in this tutorial. (Assets download in the description)

## License
```tex
MIT License

Copyright (c) 2018 Nicholas Gan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
