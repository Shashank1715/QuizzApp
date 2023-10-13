package com.example.quizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Score_Page extends AppCompatActivity {
    TextView correct;
    TextView wrong;
    Button exit;
    Button playagain;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference= database.getReference().child("scores");
    FirebaseAuth auth= FirebaseAuth.getInstance();
    FirebaseUser user= auth.getCurrentUser();
    String usercorrect;
    String userWrong;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);
        correct= findViewById(R.id.textViewCorrect);
        wrong= findViewById(R.id.textViewWrong);
        exit= findViewById(R.id.exit);
        playagain= findViewById(R.id.playAgain);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userId= user.getUid();
                usercorrect= snapshot.child(userId).child("correct").getValue().toString();

                userWrong= snapshot.child(userId).child("wrong").getValue().toString();

                correct.setText(usercorrect);
                wrong.setText(userWrong);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Score_Page.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}