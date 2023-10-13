package com.example.quizzapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quizz_App extends AppCompatActivity {
    TextView time,correct,wrong;
    TextView question,a,b,c,d;
    Button next,finish;

    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference= database.getReference().child("Questions");
     FirebaseAuth auth= FirebaseAuth.getInstance();
     FirebaseUser user= auth.getCurrentUser();
     DatabaseReference databaseReferenceSecond= database.getReference();
    String quizQuestion;
    String quizAnswerA;
    String quizAnswerB;
    String quizAnswerC;
    String quizAnswerD;
    String quizCorrectAnswer;
    int questionCount;
    int questionNumber= 1;
    String userAnswer;
    int userCorrect=0;
    int userWrong= 0;
    CountDownTimer countDownTimer;
    private static final long TOTAL_TIME= 2500;
    Boolean timerContinue;
    long leftTime= TOTAL_TIME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_app);
        next= findViewById(R.id.next);
        finish= findViewById(R.id.finish);
        question= findViewById(R.id.questions);
        a= findViewById(R.id.optiona);
        b= findViewById(R.id.optionb);
        c= findViewById(R.id.optionc);
        d= findViewById(R.id.optiond);
        time= findViewById(R.id.countdown);
        correct=  findViewById(R.id.canswer);
        wrong= findViewById(R.id.wronganswer);
        game();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                game();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendScore();
                Intent i= new Intent(Quizz_App.this,Score_Page.class);
                startActivity(i);
                finish();
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer= "a";
                if(quizCorrectAnswer.equals(userAnswer))
                {
                    a.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    correct.setText(""+userCorrect);
                }
                else {
                    a.setBackgroundColor(Color.RED);
                    a.setEnabled(false);
                    userWrong++;
                    wrong.setText(""+userWrong);
                    findAnswer();
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer= "b";

                if(quizCorrectAnswer.equals(userAnswer))
                {
                    b.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    correct.setText(""+userCorrect);
                }
                else {
                    b.setBackgroundColor(Color.RED);
                    b.setEnabled(false);
                    userWrong++;
                    wrong.setText(""+userWrong);
                    findAnswer();
                }
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer= "c";

                if(quizCorrectAnswer.equals(userAnswer))
                {
                    c.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    correct.setText(""+userCorrect);

                }
                else {
                    c.setBackgroundColor(Color.RED);
                    c.setEnabled(false);
                    userWrong++;
                    wrong.setText(""+userWrong);
                    findAnswer();
                }
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer= "d";

                if(quizCorrectAnswer.equals(userAnswer))
                {
                    d.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    correct.setText(""+userCorrect);
                }
                else {
                    d.setBackgroundColor(Color.RED);
                    d.setEnabled(false);
                    userWrong++;
                    wrong.setText(""+userWrong);
                    findAnswer();
                }
            }
        });

    }
    public void game()
    {   startTimer();
        a.setBackgroundColor(Color.WHITE);
        b.setBackgroundColor(Color.WHITE);
        c.setBackgroundColor(Color.WHITE);
        d.setBackgroundColor(Color.WHITE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               //  String value= snapshot.getValue(String.class);
                questionCount=(int) snapshot.getChildrenCount();
                quizQuestion= snapshot.child(String.valueOf(questionNumber)).child("q").getValue().toString();
                quizAnswerA= snapshot.child(String.valueOf(questionNumber)).child("a").getValue().toString();
                quizAnswerB= snapshot.child(String.valueOf(questionNumber)).child("b").getValue().toString();
                quizAnswerC= snapshot.child(String.valueOf(questionNumber)).child("c").getValue().toString();
                quizAnswerD= snapshot.child(String.valueOf(questionNumber)).child("d").getValue().toString();
                quizCorrectAnswer= snapshot.child(String.valueOf(questionNumber)).child("answer").getValue().toString();
                question.setText(quizQuestion);
                a.setText(quizAnswerA);
                b.setText(quizAnswerB);
                c.setText(quizAnswerC);
                d.setText(quizAnswerD);
                if(questionNumber < questionCount)
                {
                    questionNumber++;
                }
                else {
                    next.setClickable(false);
                    Toast.makeText(Quizz_App.this,"You answered all questions",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quizz_App.this,"there is an error",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void findAnswer()
    {
        if(quizCorrectAnswer.equals("a"))
        {
            a.setBackgroundColor(Color.GREEN);
        }
        else  if(quizCorrectAnswer.equals("b"))
        {
            b.setBackgroundColor(Color.GREEN);
        }
        else  if(quizCorrectAnswer.equals("c"))
        {
            c.setBackgroundColor(Color.GREEN);
        }
        else  if(quizCorrectAnswer.equals("d"))
        {
            d.setBackgroundColor(Color.GREEN);
        }
    }
    public void startTimer()
    {
        countDownTimer= new CountDownTimer(leftTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftTime= millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
               timerContinue= false;
               pauseTimer();
               question.setText("Sorry! Time is up");
            }
        }.start();
        timerContinue= true;
    }
    public void resetTimer()
    {
        leftTime= TOTAL_TIME;
        updateCountDownText();
    }
    public void updateCountDownText(){
        int seconds= (int)(leftTime/1000)%60;
        time.setText(""+seconds);
    }
    public void pauseTimer()
    {
        countDownTimer.cancel();
        timerContinue= false;
    }
    public void sendScore()
    {
        String userId= user.getUid();
        databaseReferenceSecond.child("score").child(userId).child("correct").setValue(userCorrect)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                               Toast.makeText(Quizz_App.this,"Score send successfully",Toast.LENGTH_SHORT).show();

                            }
                        });

        databaseReferenceSecond.child("score").child(userId).child("wrong").setValue(userWrong);
    }
}