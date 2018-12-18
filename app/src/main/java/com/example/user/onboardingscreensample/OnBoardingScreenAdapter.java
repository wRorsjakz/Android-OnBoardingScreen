package com.example.user.onboardingscreensample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OnBoardingScreenAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView imageViewIcon;
    private TextView textViewTitle;
    private TextView textViewDescription;

    //Constructor
    public OnBoardingScreenAdapter(Context context) {
        this.context = context;
    }

    /**
     * Resources to be shown on the onBoardingScreen (Icon, title, Description)
     */
    private int[] icons = {
            R.drawable.icon_food,R.drawable.icon_study,R.drawable.icon_sleep
    };

    private String[] titles = {
            "Food","Study","Sleep"
    };
    private String[] descriptions = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"
    };



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.onboarding_slide_layout,container,false);

        imageViewIcon = view.findViewById(R.id.onboarding_icon_id);
        textViewTitle = view.findViewById(R.id.onboarding_title_id);
        textViewDescription = view.findViewById(R.id.onboarding_description_id);

        imageViewIcon.setImageResource(icons[position]);
        textViewTitle.setText(titles[position]);
        textViewDescription.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
