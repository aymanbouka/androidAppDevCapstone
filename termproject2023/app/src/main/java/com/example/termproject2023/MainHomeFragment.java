package com.example.termproject2023;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

//import com.example.termproject2023.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainHomeFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{



    private GoogleMap googleMap;

    private Event event;

    private static final String ARG_PARAM1 = "param1";
    private LocationLatLng locationLatLng;

    Polyline currentPolyLine;

    public MainHomeFragment() {
        // Required empty public constructor
    }

    public static MainHomeFragment newInstance(Event event) {
        MainHomeFragment fragment = new MainHomeFragment();
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
        setHasOptionsMenu(true);
        if (event != null) {
            Log.d("TAG", "The Single Event is : " + event.getLocation());
            performGeocoding(event.getLocation());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Home");

        Log.d("demo", "currently onCreateView :");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


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

    OkHttpClient client = new OkHttpClient();


    private void performGeocoding(String placeName) {
        //https://maps.googleapis.com/maps/api/geocode/json?address=HuntHallUNCC,Charlotte,NC&key=API-KEY


        //API-KEY GOES INSIDE URL Where API-KEY = API KEY
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/geocode/json?address="+placeName+"UNCC,Charlotte,NC&key=API-KEY")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful())
                {
                    try {

                        JSONObject rootJsonObject = new JSONObject(response.body().string());
                        JSONArray resultsArray = rootJsonObject.getJSONArray("results");

                        if (resultsArray.length() > 0) {
                            JSONObject firstResult = resultsArray.getJSONObject(0);
                            JSONObject location = firstResult.getJSONObject("geometry").getJSONObject("location");

                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");

                            // Create a LocationLatLng object and set the coordinates
                            locationLatLng = new LocationLatLng();
                            locationLatLng.setLat(lat);
                            locationLatLng.setLng(lng);


                            //****************** routing part starts here TODO


                            MarkerOptions place1 = new MarkerOptions().position(new LatLng(locationLatLng.getLat(),locationLatLng.getLng())).title("Destination");
                            MarkerOptions place2 = new MarkerOptions().position(new LatLng(35.3071476, -80.7364515)).title("Start");





                            // Use the coordinates as needed
                            Log.d("TAG", "Latitude: " + lat + ", Longitude: " + lng);
                        } else {
                            Log.d("TAG", "No results found");
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
                else
                {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("TAG", "onResponse: " + body);
                }

            }
        });


    }


    private void drawRouteLine(LatLng origin, LatLng destination) {
        if (googleMap == null) {
            Log.e("TAG", "drawRouteLine: googleMap is null");
            return;
        }

        // Draw the route on the map using Polyline
        if (currentPolyLine != null) {
            currentPolyLine.remove();
        }
        currentPolyLine = googleMap.addPolyline(new PolylineOptions()
                .add(origin, destination)
                .width(10)
                .color(Color.BLUE));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        //set user location
        LatLng markerLocation = new LatLng(35.3071476, -80.7364515);
        googleMap.addMarker(new MarkerOptions().position(markerLocation).title("Marker"));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerLocation));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 12));  // Adjust the zoom level as needed

        //checking if locationLatLng is not null because a null object causes crashes
        if (locationLatLng != null) {
            Log.d("TAG", "locationLatLng inside MapReady: " + locationLatLng.getLng() + " " + locationLatLng.getLat());

            LatLng destinationLocation = new LatLng(locationLatLng.getLat(), locationLatLng.getLng());
            googleMap.addMarker(new MarkerOptions().position(destinationLocation).title("Destination"));

            Log.d("TAG", "onMapReady destinationLocation: " + destinationLocation);

            Log.d("TAG", "onMapReady: Calling drawRouteLine");
            drawRouteLine(markerLocation, destinationLocation);

        }

    }






}//end of class