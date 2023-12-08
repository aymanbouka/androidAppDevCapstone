package com.example.termproject2023;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class OnboardingAdapter extends FragmentStateAdapter{
    private final FragmentActivity activity;
    private final int itemsCount;

    public OnboardingAdapter(@NonNull FragmentActivity fragmentActivity, int itemsCount) {
        super(fragmentActivity);
        this.activity = fragmentActivity;
        this.itemsCount = itemsCount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return OnboardingViewPagerFragment.getInstance(position);
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }
}