package com.example.termproject2023;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.termproject2023.databinding.FragmentEventBinding;
import com.example.termproject2023.databinding.FragmentProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;


public class ProfileFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);


        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        //go to login since logout has been pushed
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                getFragmentManager().beginTransaction().replace(R.id.contentView, new LogInFragment())
                        .commit();

                Toast.makeText(getActivity(), "You have been Logged out", Toast.LENGTH_SHORT).show();

            }
        });

        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentView, new EditProfileFragment()).addToBackStack(null)
                        .commit();
            }
        });


        return binding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db.collection("userProfile")
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            Toast.makeText(getActivity(), "Something went wrong try again", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(value != null){

                            for (QueryDocumentSnapshot doc: value){
                                UserProfile user = doc.toObject(UserProfile.class);
                                binding.textViewBio.setText(user.getBio());
                                binding.textViewInterests.setText(user.getInterests());
                                binding.textViewUserName.setText(user.getUsername());
                                //image display
                            }
//

                        }

                    }
                });



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
}
}