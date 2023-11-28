package com.example.termproject2023;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termproject2023.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ProfileFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Announcement> announcementsArrayList;
    AnnouncementAdapter announcementAdapter;
    DatabaseReference rtdb = FirebaseDatabase.getInstance().getReference("announcements");
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
        announcementsArrayList = new ArrayList<>();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        announcementAdapter = new AnnouncementAdapter(getActivity(),announcementsArrayList);
        binding.recyclerView.setAdapter(announcementAdapter);
        rtdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren() ) {
                    announcementsArrayList.add(datasnapshot.getValue(Announcement.class));
//                    Log.d(datasnapshot.getKey(),datasnapshot.getValue().toString());
                }
                    announcementAdapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.INVISIBLE);
                    Log.d("Announcements",announcementsArrayList.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to fetch data from database...", Toast.LENGTH_SHORT).show();
            }
        });
        if(mAuth.getCurrentUser().getEmail().toString().equals("yashsomething@gmail.com"))//TODO : Change email to moderator's email
        {
            binding.buttonModeratorOnlyAccess.setVisibility(View.VISIBLE);
        }
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

        binding.buttonModeratorOnlyAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnnouncementDialogBox();
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
                            for (QueryDocumentSnapshot doc : value){
                                UserProfile user = doc.toObject(UserProfile.class);
                                binding.textViewBio.setText("Your Bio:" +user.getBio());
                                binding.textViewInterests.setText("Interests:" + user.getInterests());
                                binding.textViewUserName.setText(user.getUsername());
                                //image display
                            }
                        }
                    }
                });
    }

    private void showAnnouncementDialogBox() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        final EditText announcementEditText = dialogView.findViewById(R.id.announcementEditText);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setTitle("Make an Announcement!")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String announcement = announcementEditText.getText().toString();
                        saveAnnouncementToDb(announcement);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void saveAnnouncementToDb(String announcement) {
        Map<String,Object> announcementData = new HashMap<>();
        announcementData.put("text",announcement);
        rtdb.child(UUID.randomUUID().toString()).updateChildren(announcementData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "Announcement made!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error debug", e.toString());
                Toast.makeText(getActivity(), "There was an error, Please try again later...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // Handle Home item selection
                getFragmentManager().beginTransaction().replace(R.id.contentView, new MainHomeFragment()).commit();
                return true;
            case R.id.profile:
                // Handle Profile item selection
                getFragmentManager().beginTransaction().replace(R.id.contentView, new ProfileFragment()).commit();
                return true;
            case R.id.events:
                // Handle Events item selection
                getFragmentManager().beginTransaction().replace(R.id.contentView, new EventsFragment()).commit();
                return true;
            default:
                return false;
    }
}
}