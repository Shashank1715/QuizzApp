package com.example.quizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {
    EditText email;
    Button send;
    FirebaseAuth auth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email= findViewById(R.id.editTextTextEmailAddress);
        send= findViewById(R.id.button1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String umal= email.getText().toString();
                resetpassword(umal);
            }
        });
    }
    public void resetpassword(String umail)
    {
        auth.sendPasswordResetEmail(umail)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(Forgot_Password.this,"We sent an email",Toast.LENGTH_SHORT).show();
                             send.setClickable(false);
                             finish();
                         }
                         else{
                             Toast.makeText(Forgot_Password.this,"Provide Correct email",Toast.LENGTH_SHORT).show();

                         }
                    }
                });
    }
}