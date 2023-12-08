package com.example.termproject2023;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termproject2023.databinding.FragmentMyPersonalEventsBinding;
import com.example.termproject2023.databinding.FragmentPastEventsBinding;
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


public class PastEventsFragment extends Fragment {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FragmentPastEventsBinding binding;


    private Event mEvent;

    ArrayList<Event> events = new ArrayList<>();

  PastEventsAdapter adapter;

    public PastEventsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("My Personal Events");
        // Inflate the layout for this fragment
        binding = FragmentPastEventsBinding.inflate(inflater, container, false);

//        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new PastEventsFragment.PastEventsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        db.collection("UsersPastEvents")
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

    class PastEventsAdapter extends RecyclerView.Adapter<PastEventsAdapter.PastEventsHolder> {
        @NonNull
        @Override
        public PastEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = getLayoutInflater().inflate(R.layout.event_row_item, parent, false);
            return new PastEventsHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PastEventsHolder holder, int position) {
            Event event = events.get(position);
            holder.setupUI(event);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        class PastEventsHolder extends RecyclerView.ViewHolder {
            Event mEvent;
            TextView textViewTitle;

            TextView textViewLocationOfEvent;

            //newly added to document
            TextView textViewTimeOfEvent;

            ImageView imageViewOfEvent;

            public PastEventsHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                textViewLocationOfEvent = itemView.findViewById(R.id.textViewLocationOfEvent);
                textViewTimeOfEvent = itemView.findViewById(R.id.textViewTimeOfEvent);
                imageViewOfEvent = itemView.findViewById(R.id.imageViewOfEvent);

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



}//end class