package com.example.termproject2023;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.termproject2023.databinding.FragmentOnboardingViewPagerBinding;

import java.util.ArrayList;
import java.util.List;

public class OnboardingViewPagerFragment extends Fragment {

    private static final String ARG_POSITION = "ARG_POSITION";

    public static OnboardingViewPagerFragment getInstance(int position) {
        OnboardingViewPagerFragment fragment = new OnboardingViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentOnboardingViewPagerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        binding = FragmentOnboardingViewPagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int position = requireArguments().getInt(ARG_POSITION);
        String[] onBoardingTitles = requireContext().getResources().getStringArray(R.array.onboarding_titles);
        String[] onBoardingTexts = requireContext().getResources().getStringArray(R.array.onboarding_texts);
        binding.onBoardingImage.setImageResource(getOnBoardAssets().get(position));
        binding.onBoardingTextTitle.setText(onBoardingTitles[position]);
        binding.onBoardingTextMsg.setText(onBoardingTexts[position]);
    }

    private List<Integer> getOnBoardAssets() {
        List<Integer> onBoardAssets = new ArrayList<>();
        //changes images from here
        onBoardAssets.add(R.drawable.onboarding);
        onBoardAssets.add(R.drawable.onboarding);
        onBoardAssets.add(R.drawable.onboarding);
        onBoardAssets.add(R.drawable.onboarding);
        return onBoardAssets;
    }
}
