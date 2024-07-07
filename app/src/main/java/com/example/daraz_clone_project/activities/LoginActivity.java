package com.example.daraz_clone_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void signup(View view) {
        startActivity(new Intent(LoginActivity.this , MainActivity.class)
        );
    }

    public void LogIn(View view) {
        String useremail = email.getText().toString().trim();
        String userpassword = password.getText().toString().trim();

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

        auth.signInWithEmailAndPassword(useremail,userpassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(LoginActivity.this, "Login Successful ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this , RegistrationActivity.class));
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


    }
}
