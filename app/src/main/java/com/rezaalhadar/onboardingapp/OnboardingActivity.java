package com.rezaalhadar.onboardingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicator;
    private MaterialButton buttonOnboarding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_activity);

        layoutOnboardingIndicator = findViewById(R.id.onboardingLayoutIndicator);
        buttonOnboarding = findViewById(R.id.onboardingActionButton);

        setupOnboardingItems();
        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void  setupOnboardingItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem page1 = new OnboardingItem();
        page1.setTittle(getString(R.string.onboarding_title_page1));
        page1.setDescription(getString(R.string.onboarding_desc_page1));
        page1.setImage(R.drawable.onboarding_1);

        OnboardingItem page2 = new OnboardingItem();
        page2.setTittle(getString(R.string.onboarding_title_page2));
        page2.setDescription(getString(R.string.onboarding_desc_page2));
        page2.setImage(R.drawable.onboarding_2);

        OnboardingItem page3 = new OnboardingItem();
        page3.setTittle(getString(R.string.onboarding_title_page3));
        page3.setDescription(getString(R.string.onboarding_desc_page3));
        page3.setImage(R.drawable.onboarding_3);

        onboardingItems.add(page1);
        onboardingItems.add(page2);
        onboardingItems.add(page3);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index) {
        int childcount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childcount; i++) {
            ImageView imageView = (ImageView)layoutOnboardingIndicator.getChildAt(i);
            if ( i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.onboarding_indicator_inactive));
            }
        }

        if ( index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboarding.setText("START");
        } else {
            buttonOnboarding.setText("NEXT");
        }
    }
}