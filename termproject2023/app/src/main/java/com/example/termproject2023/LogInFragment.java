package com.example.termproject2023;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogInFragment extends Fragment {

    private FirebaseAuth mAuth;


    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //Initialize Elements
    EditText editTextTextEmailAddress, editTextTextPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Log In");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        editTextTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = view.findViewById(R.id.editTextTextPassword);


        //the login button
        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextTextEmailAddress.getText().toString();
                String password = editTextTextPassword.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter an email", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter a password", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d("demo", "onComplete: logged in successful ");
                                Log.d("demo", "onComplete: logged in successful " + mAuth.getCurrentUser().getUid());

                                getParentFragmentManager().beginTransaction().replace(R.id.contentView, new MainHomeFragment())
                                        .commit();


                            }else{
                                Log.d("demo", "onComplete: Error!!! ");
                                Log.d("demo", "onComplete:" + task.getException().getMessage());
                            }
                        }
                    });
                }



            }
        });

        //the register button
        view.findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentView, new RegisterFragment())
                        .commit();
            }
        });


        return view;
    }
}