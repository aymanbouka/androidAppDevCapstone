package com.example.termproject2023;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.termproject2023.databinding.FragmentEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class EditProfileFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{

FragmentEditProfileBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public EditProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentEditProfileBinding.inflate(getLayoutInflater(),container,false);








//        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

          //TODO
//        binding.buttonUploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
//                        registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
//                            // Callback is invoked after the user selects a media item or closes the
//                            // photo picker.
//
//
//
//
//                            if (uri != null) {
//                                Log.d("PhotoPicker", "Selected URI: " + uri);
//                            } else {
//                                Log.d("PhotoPicker", "No media selected");
//                            }
//                        });
//
//                pickMedia.launch(new PickVisualMediaRequest.Builder()
//                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
//                        .build());
//            }
//        });







        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String bio = binding.editTextTextBio.getText().toString();
                String interests = binding.editTextTextPersonInterests.getText().toString();
                String username = binding.editTextTextPersonUserName.getText().toString();
                int id = binding.imageViewOfUser.getId(); //still need image id

                Log.d("TAG", "bio: " + bio + "interests: " + username + "interests " + interests);



                HashMap<String,Object> data = new HashMap<>();
                data.put("bio", bio);
                data.put("interests",interests);
                data.put("username", username);
                data.put("imageId",id); //still need imageId
                data.put("userId",mAuth.getCurrentUser().getUid());
                //upload to database
                DocumentReference documentReference = db.collection("userProfile").document();

                documentReference.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mListner.goToProfile();
                        }else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



    }



    backToProfile mListner;
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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (backToProfile) context;
    }

    interface backToProfile {
        void goToProfile();
    }
}



