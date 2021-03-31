package com.example.miumessenger.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miumessenger.models.Users;
import com.example.miumessenger.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setTitle("Creating Account ");
        dialog.setMessage("We're creating your account.....");

        binding.btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String mail = binding.etEmail.getText().toString();
                String pass = binding.etPassword.getText().toString();

                if(mail.isEmpty() || pass.isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "PLease fill all the requirements!", Toast.LENGTH_SHORT).show();


                }else{
                        if (mail.contains("@manarat.ac.bd")) {

                            auth.createUserWithEmailAndPassword
                                    (binding.etEmail.getText().toString(),
                                            binding.etPassword.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            dialog.dismiss();

                                            if (task.isSuccessful()) {
                                                Users user = new Users(binding.etUserName.getText().toString(), binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                                                String id = task.getResult().getUser().getUid();

                                                database.getReference().child("users").child(id).setValue(user);
                                                Toast.makeText(SignUpActivity.this,
                                                        "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                        } else {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "PLease Enter Your MIU Domain Mail!", Toast.LENGTH_SHORT).show();
                        }

                }


            }
        });

        binding.tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


    }
}