package com.example.miumessenger.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miumessenger.R;
import com.example.miumessenger.databinding.ActivitySignInBinding;
import com.example.miumessenger.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog dialog;
    FirebaseUser fUser;
    TextView Msg;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Msg = findViewById(R.id.verifiedMsg);
        send = findViewById(R.id.reSendBtn);
        fUser = auth.getCurrentUser();


        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setTitle("Log In ");
        dialog.setMessage("Your account is logging.....");

        binding.btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etEmail.getText().toString().isEmpty()){
                    binding.etEmail.setError("Enter Your Email!");
                    return;
                }
                if(binding.etPassword.getText().toString().isEmpty()){
                    binding.etPassword.setError("Enter Your Password!");
                    return;
                }
//                if(!fUser.isEmailVerified()) {
//                    Msg.setVisibility(View.VISIBLE);
//                    send.setVisibility(View.VISIBLE);
//                    send.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            FirebaseUser fUser = auth.getCurrentUser();
//                            fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(SignInActivity.this, "Verification email has been sent!", Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            }).addOnFailureListener(new OnFailureListener() {
//
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(SignInActivity.this, "onFailure: Email not sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//                        }
//                    });
//
//                }
                    dialog.show();
                    auth.signInWithEmailAndPassword(binding.etEmail.getText().toString(),binding.etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    dialog.dismiss();

                                    if(task.isSuccessful()){

                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(SignInActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }

        });
        binding.tvClickForSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

//        if(auth.getCurrentUser() !=null){
//            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }


    }
}