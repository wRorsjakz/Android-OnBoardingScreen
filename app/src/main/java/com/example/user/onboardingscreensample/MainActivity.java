package com.example.user.onboardingscreensample;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    private ViewPager viewPager;
    private LinearLayout dotsLinearLayout;
    private OnBoardingScreenAdapter adapter;
    private TextView[] dots;
    private int noOfScreens;
    private Button backButton;
    private Button nextButton;
    private int currentScreenIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        currentScreenIndex = 0;
        //Pass adapter to viewpager
        adapter = new OnBoardingScreenAdapter(this);
        viewPager.setAdapter(adapter);

        //Get no of screens from the adapter
        noOfScreens = adapter.getCount();
        addDotsToArray(noOfScreens,currentScreenIndex);

        //Pass listener to viewPager
        viewPager.addOnPageChangeListener(listener);
    }

    /**
     * Initialize the views
     */
    private void initializeViews(){
        viewPager = findViewById(R.id.onboarding_viewpager_id);
        dotsLinearLayout = findViewById(R.id.onboarding_dots_container_id);
        nextButton = findViewById(R.id.next_button_id);
        backButton = findViewById(R.id.back_button_id);

        nextButton.setText(getString(R.string.next));
        backButton.setEnabled(false);

        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    /**
     * Adds the number of dots to the TextView[], according to number of screens to be shown
     */
    private void addDotsToArray(int noOfSlides,int currentPositon){
        dots = new TextView[noOfSlides];

        // Adds dots to the textview array in translucent white (unselected color)
        for (int i = 0; i < noOfSlides; i++){
            dots[i] = new TextView(this);
            //Min api is 24
            dots[i].setText(Html.fromHtml("&#8226",noOfSlides));
            dots[i].setTextSize(35);
            dots[i].setTextColor(ContextCompat.getColor(this,R.color.colorTranslucentWhite));
        }

        // Set the dot for the current screen to white (selected color)
        dots[currentPositon].setTextColor(ContextCompat.getColor(this,R.color.colorWhite));

        displayDots(dots);
    }

    /**
     * Display dots onto the screen
     * @param dots
     */
    private void displayDots(TextView[] dots){
        // Add dots to the layout
        for(int i = 0; i < dots.length; i++){
            dotsLinearLayout.addView(dots[i]);
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            currentScreenIndex = i;
            // Remove all the dots
            dotsLinearLayout.removeAllViews();
            //Pass the current position of the screen to addDotsToArray method
            addDotsToArray(noOfScreens,i);

            // if onBoardingScreen is at the first screen
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
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_button_id:
                // Check what the text on the next button is
                String buttonText = nextButton.getText().toString();
                if (buttonText.equals(this.getResources().getString(R.string.next))){
                    viewPager.setCurrentItem(currentScreenIndex + 1,true);
                }
                else if (buttonText.equals(this.getResources().getString(R.string.finish))){
                    Toast.makeText(this, "Finish Pressed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back_button_id:
                viewPager.setCurrentItem(currentScreenIndex-1,true);
                break;
        }
    }
}
