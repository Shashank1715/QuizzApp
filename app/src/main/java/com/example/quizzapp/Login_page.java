package com.example.quizzapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login_page extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signin;
    SignInButton signingoogle;
    TextView signup;
    TextView forgot;

    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth= FirebaseAuth.getInstance();
    ActivityResultLauncher<Intent> activityResultLauncher;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        registerActivityforsigninGoogle();
        email= findViewById(R.id.EmailAddress);
        password= findViewById(R.id.Password);
        signin= findViewById(R.id.buttonsignin);
        signingoogle= findViewById(R.id.signIngoogleButton);
        signup= findViewById(R.id.signup);
        forgot= findViewById(R.id.forgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login_page.this,Forgot_Password.class);
                startActivity(i);
            }
        });
        signingoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signinGoogle();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Login_page.this, Sign_Up.class);
                startActivity(i);

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ue= email.getText().toString();
                String up= password.getText().toString();
                signinwithfirebase(ue,up);
            }
        });

    }

    public void signinGoogle()
    {
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("836669511694-0reum5u9d0qpmrua7s2a8qu4d6bn0je9.apps.googleusercontent.com")
                .requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,gso);
        signin();
    }
    public void signin()
    {
        Intent signinIntent= googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signinIntent);
    }
    public void registerActivityforsigninGoogle()
    {   activityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            //int resultcode= o.getData();
            Intent data= o.getData();
            if(data!= null){
                Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
                firebasesigninwithgoogle(task);
            }
        }
    });

    }
    private void firebasesigninwithgoogle(Task<GoogleSignInAccount> task)
    {
        try {
            GoogleSignInAccount account= task.getResult(ApiException.class);
            Toast.makeText(this,"successful",Toast.LENGTH_SHORT).show();
            Intent i= new Intent(Login_page.this,MainActivity.class);
            startActivity(i);
            finish();
            firebaseGoogleAccount(account);
        } catch (ApiException e) {
            throw new RuntimeException(e);

        }
    }
    private void firebaseGoogleAccount(GoogleSignInAccount account)
    {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful())
                             {

                             }else{

                             }
                    }
                });
    }
    public void signinwithfirebase(String unamed, String upassed)
    {
        signin.setClickable(false);
        auth.signInWithEmailAndPassword(unamed,upassed)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent i= new Intent(Login_page.this,MainActivity.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(Login_page.this, "Login success",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Toast.makeText(Login_page.this, "Enter Correct Details",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    protected void onStart(){
        super.onStart();
        FirebaseUser user= auth.getCurrentUser();
        if(user != null)
        { Intent i= new Intent(Login_page.this,MainActivity.class);
            startActivity(i);
            finish();

        }
    }
}