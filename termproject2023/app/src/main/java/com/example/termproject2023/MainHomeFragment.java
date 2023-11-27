package com.example.termproject2023;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.example.termproject2023.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainHomeFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{



    public MainHomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("demo", "entering onOptionItemSelected:");
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Home");

        Log.d("demo", "currently onCreateView :");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);



        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // Handle Home item selection
                // For example, replace the current fragment with a HomeFragment
                getFragmentManager().beginTransaction().replace(R.id.contentView, new MainHomeFragment()).commit();
                return true;
            case R.id.profile:
                // Handle Profile item selection
                // For example, replace the current fragment with a ProfileFragment
                getFragmentManager().beginTransaction().replace(R.id.contentView, new ProfileFragment()).commit();
                return true;
            case R.id.events:
                // Handle Events item selection
                // For example, replace the current fragment with an EventsFragment
                getFragmentManager().beginTransaction().replace(R.id.contentView, new EventsFragment()).commit();
                return true;
            default:
                return false;

    }
   }//end of nav_selected

}//end of class