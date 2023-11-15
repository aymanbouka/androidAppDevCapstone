package com.example.termproject2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

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
}