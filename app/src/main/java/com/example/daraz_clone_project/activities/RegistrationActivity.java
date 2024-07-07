package com.example.daraz_clone_project.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.daraz_clone_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG ="RegistrationActivity" ;
    EditText name,email,password;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        Log.d(TAG, "onCreate: RegistrationActivity started");


        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
         {
             startActivity(new Intent(RegistrationActivity.this , MainActivity.class));
             finish();
        }
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        sharedPreferences= getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime" ,true);

        if(isFirstTime){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",true);
            editor.apply();

            Intent Intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(Intent);
            finish();
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void signup(View view) {
        String username = name.getText().toString().trim();
        String useremail = email.getText().toString().trim();
        String userpassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Enter Username!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(useremail)){
            Toast.makeText(this, "Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userpassword)){
            Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userpassword.length()<6){
            Toast.makeText(this, "Password too Short! , Enter Minimum 6 Characters", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(useremail,userpassword)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegistrationActivity.this, "Successfully Registered ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistrationActivity.this ,MainActivity.class));

                                }
                                else {
                                    Toast.makeText(RegistrationActivity.this, "Registration failed "+task.getException(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


    }

    public void LogIn(View view) {
        startActivity(new Intent(RegistrationActivity.this , LoginActivity.class));

    }
    }
