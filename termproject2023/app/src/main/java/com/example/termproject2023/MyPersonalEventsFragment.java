package com.example.termproject2023;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termproject2023.databinding.FragmentMyPersonalEventsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPersonalEventsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FragmentMyPersonalEventsBinding binding;


    private Event mEvent;

    ArrayList<Event> events = new ArrayList<>();

    public MyPersonalEventsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("My Personal Events");
        // Inflate the layout for this fragment
        binding = FragmentMyPersonalEventsBinding.inflate(inflater, container, false);

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        return binding.getRoot();
    }
    MyPersonalEventsAdapter adapter;


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MyPersonalEventsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        db.collection("User")
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            Toast.makeText(getActivity(), "Something went wrong try again", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(value != null){
                            events.clear();
                            for (QueryDocumentSnapshot doc: value){
                                Event event = doc.toObject(Event.class);
                                events.add(event);
                            }
                            adapter.notifyDataSetChanged();

                        }

                    }
                });

    }


    class MyPersonalEventsAdapter extends RecyclerView.Adapter<MyPersonalEventsAdapter.MyPersonalEventsHolder> {
        @NonNull
        @Override
        public MyPersonalEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = getLayoutInflater().inflate(R.layout.event_row_item_delete, parent, false);
            return new MyPersonalEventsHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyPersonalEventsHolder holder, int position) {
            Event event = events.get(position);
            holder.setupUI(event);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        class MyPersonalEventsHolder extends RecyclerView.ViewHolder {
            Event mEvent;
            TextView textViewTitle;

            TextView textViewLocationOfEvent;

            //newly added to document
            TextView textViewTimeOfEvent;
            TextView textViewDescription;

            ImageView imageViewOfEvent;

            public MyPersonalEventsHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                textViewLocationOfEvent = itemView.findViewById(R.id.textViewLocationOfEvent);
                textViewTimeOfEvent = itemView.findViewById(R.id.textViewTimeOfEvent);
                imageViewOfEvent = itemView.findViewById(R.id.imageViewOfEvent);


                itemView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         CollectionReference collectionReference = db.collection("User");

                        // Method to delete a document without knowing its ID

                            // Create a query to find the document based on a specific field
                            Query query = collectionReference.whereEqualTo("guid", mEvent.getGuid());

                            // Execute the query and delete the document(s) found
                            query.get().addOnSuccessListener(queryDocumentSnapshots -> {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    documentSnapshot.getReference().delete();
                                }
                            }).addOnFailureListener(e -> {
                                // Handle failure
                                // e.g., Log the error or show a Toast message
                            });

                    }//end onClick
                });

            }

            public void setupUI(Event event) {
                this.mEvent = event;
                textViewTitle.setText(mEvent.getTitle());
                textViewLocationOfEvent.setText(mEvent.getLocation());

                Log.d("TAG", "setupUI: " + mEvent.getTime());

                //newly added to document
                textViewTimeOfEvent.setText(mEvent.getTime());
                //textViewDescription.setText(Html.fromHtml(mEvent.getDescription()));
                Picasso.get().load(mEvent.getImg_url()).into(imageViewOfEvent);
            }


        }


    }










}