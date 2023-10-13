package com.example.quizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up extends AppCompatActivity {
    EditText email;
    EditText password;
    Button signup;
    ProgressBar progressBar;

    FirebaseAuth auth= FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email= findViewById(R.id.editTextEmailAddress);
        password= findViewById(R.id.editTextTextPassword);
        signup= findViewById(R.id.buttonsignup);
        progressBar= findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signup.setClickable(false);
                String ue= email.getText().toString();
                String up= password.getText().toString();
                signupfirebase(ue,up);
            }
        });

    }
    public void signupfirebase(String umail, String upass)
    {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(umail,upass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(Sign_Up.this,"Account Created",Toast.LENGTH_LONG).show();
                                finish();
                               progressBar.setVisibility(View.INVISIBLE);
                           }
                           else {
                               Toast.makeText(Sign_Up.this,"Please try later",Toast.LENGTH_LONG).show();

                           }
                    }
                });
    }
}