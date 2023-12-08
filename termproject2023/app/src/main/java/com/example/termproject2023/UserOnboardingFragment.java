package com.example.termproject2023;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.termproject2023.databinding.FragmentUserOnboardingBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UserOnboardingFragment extends Fragment {
    private OnboardingAdapter viewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentUserOnboardingBinding binding = FragmentUserOnboardingBinding.inflate(inflater, container, false);
        viewPagerAdapter = new OnboardingAdapter(requireActivity(), 4);
        binding.viewPagerOnboarding.setAdapter(viewPagerAdapter);
        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabLayoutOnboarding, binding.viewPagerOnboarding, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        });
        mediator.attach();

        binding.bttnNextOnboarding.setOnClickListener(view -> {
            if(binding.viewPagerOnboarding.getCurrentItem() < 3){
                binding.viewPagerOnboarding.setCurrentItem(binding.viewPagerOnboarding.getCurrentItem() + 1, true);
            }
            else{
                getFragmentManager().beginTransaction().replace(R.id.contentView, new MainHomeFragment())
                        .commit();
            }
        });


        return binding.getRoot();
    }
}