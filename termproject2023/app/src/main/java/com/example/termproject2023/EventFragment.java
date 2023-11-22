package com.example.termproject2023;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termproject2023.databinding.FragmentEventBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;


public class EventFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Event event;
     FragmentEventBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public EventFragment() {
        // Required empty public constructor
    }


    public static EventFragment newInstance(Event event) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, event);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = (Event) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentEventBinding.inflate(inflater, container, false);





        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String htmlCode = event.getDescription();
        Document document = Jsoup.parse(htmlCode);
        Element descElement = document.select("p").first();
        String descText = (descElement != null) ? descElement.text() : event.getDescription();


        binding.textViewDescription.setText(descText);
        Picasso.get().load(event.getImg_url()).into(binding.imageViewOfTheEvent);
        binding.textViewTimeOfTheEvent.setText(event.getTime());
        binding.textViewLocationTheEvent.setText(event.getLocation());



        binding.buttonAddToPersonalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    String title, guid, description, time, location, img_url, author, host;
                //add to personal list fragment
                HashMap<String,Object> data = new HashMap<>();
                data.put("title", event.getTitle());
                data.put("guid",event.getGuid());
                data.put("description",descText);
                data.put("time",event.getTime());
                data.put("location",event.getLocation());
                data.put("img_url",event.getImg_url());
                data.put("host",event.getHost());
                data.put("userId", mAuth.getCurrentUser().getUid());

                DocumentReference documentReference = db.collection("User").document();

                documentReference.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mListner.goToPersonalEvents();
                        }else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        binding.buttonToLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to link
                GoToURL(event.getGuid());
            }
        });
    }

   /*
   String htmlCode = mEvent.getDescription();
                Document document = Jsoup.parse(htmlCode);
                Element descElement = document.select("p").first();
                String descText = (descElement != null) ? descElement.text() : mEvent.getDescription();
    */

    void GoToURL(String url){
        Uri uri = Uri.parse(url);
        Intent intent= new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }


    ListOfPersonalEvents mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (ListOfPersonalEvents) context;
    }

    interface ListOfPersonalEvents{
        void goToPersonalEvents();
    }
}