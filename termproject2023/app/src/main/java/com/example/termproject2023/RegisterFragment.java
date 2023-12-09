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


public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;


    public RegisterFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    EditText editTextTextEmailAddress, editTextTextPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Register");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editTextTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = view.findViewById(R.id.editTextTextPassword);

        view.findViewById(R.id.buttonSignup).setOnClickListener(new View.OnClickListener() {
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

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Log.d("demo", "onComplete: Register in successful ");
                                Log.d("demo", "onComplete: Register in successful " + mAuth.getCurrentUser().getUid());

                                Log.d("demo", "onComplete: Register in successful " + Thread.currentThread().getId());
                                // yaha par onboarding ayega
                                getFragmentManager().beginTransaction().replace(R.id.contentView, new UserOnboardingFragment())
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

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentView, new LogInFragment())
                        .commit();
            }
        });

        return view;
    }
}