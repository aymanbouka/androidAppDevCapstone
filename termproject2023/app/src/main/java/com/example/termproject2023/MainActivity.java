package com.example.termproject2023;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements EventsFragment.EventListener, EventFragment.ListOfPersonalEvents , EditProfileFragment.backToProfile, MyPersonalEventsFragment.SendlocationOfEvents{

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mAuth = FirebaseAuth.getInstance();
       if (mAuth.getCurrentUser() == null){
           getSupportFragmentManager().beginTransaction()
                   .add(R.id.contentView, new LogInFragment())
                   .commit();
       }else{
           getSupportFragmentManager().beginTransaction()
                   .add(R.id.contentView, new MainHomeFragment())
                   .commit();
       }
    }

    @Override
    public void viewMoreEventDetails(Event event) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, EventFragment.newInstance(event))
                .commit();
    }

    @Override
    public void goToPersonalEvents() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new MyPersonalEventsFragment())
                .commit();
    }

    @Override
    public void getLocation(Event event) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, MainHomeFragment.newInstance(event))
                .commit();
    }

    @Override
    public void goToProfile() {
        getSupportFragmentManager().popBackStack();

    }
}