package com.example.termproject2023;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.termproject2023.databinding.FragmentEventsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class EventsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {


    public EventsFragment() {
        // Required empty public constructor
    }

    FragmentEventsBinding binding;
    ArrayList<Event> events = new ArrayList<>();


    EventsAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Events");
        // Inflate the layout for this fragment
        binding = FragmentEventsBinding.inflate(inflater, container, false);

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new EventsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        getEvents();


    }

    OkHttpClient client = new OkHttpClient();

    private void getEvents(){
    //https://ninerengage.charlotte.edu/events.rss

        Request request = new Request.Builder()
                .url("https://ninerengage.charlotte.edu/events.rss")
                .build();

    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            Log.d("DEMO", "onResponse: " + Thread.currentThread().getId());

            if (response.isSuccessful()){
//            ResponseBody responseBody = response.body();

                try {
                    events.clear();
                    EventsXMLParser eventsXMLParser = new EventsXMLParser();
                     events = eventsXMLParser.parse(response.body().byteStream());
                    Log.d("DEMO", "onResponse: this is an event: " + events + "this is an event");





                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (SAXException e) {
                    throw new RuntimeException(e);
                }


            }else{

                ResponseBody responseBody = response.body();
                String body = responseBody.string();
                Log.d("TAG", "onResponse: " + body);
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


    class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder>{


        @NonNull
        @Override
        public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = getLayoutInflater().inflate(R.layout.event_row_item, parent, false);
            return new EventsViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        @Override
        public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
            Event event = events.get(position);
            holder.setupUI(event);
        }

        class EventsViewHolder extends RecyclerView.ViewHolder{

            //initialize the items
            Event mEvent;
            TextView textViewTitle;

            TextView textViewLocationOfEvent;

            //newly added to document
            TextView textViewTimeOfEvent;
            TextView textViewDescription;

            ImageView imageViewOfEvent;

            public EventsViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                textViewLocationOfEvent = itemView.findViewById(R.id.textViewLocationOfEvent);
                textViewTimeOfEvent = itemView.findViewById(R.id.textViewTimeOfEvent);
                imageViewOfEvent = itemView.findViewById(R.id.imageViewOfEvent);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListner.viewMoreEventDetails(mEvent);
                    }
                });


            }
            //setUpUI and set items text value

            public void setupUI(Event event){
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





    EventListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (EventListener) context;
    }

    interface EventListener{
        void viewMoreEventDetails(Event event);
    }

}