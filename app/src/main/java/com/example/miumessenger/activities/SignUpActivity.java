package com.example.miumessenger.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.miumessenger.models.Users;
import com.example.miumessenger.databinding.ActivitySignUpBinding;
import com.github.pgreze.reactions.ReactionViewState;
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
                if(binding.etUserName.getText().toString().isEmpty()){
                    binding.etUserName.setError("Enter Your Name!");
                    return;
                }
                if(binding.etEmail.getText().toString().isEmpty()){
                    binding.etEmail.setError("Enter Your Email!");
                    return;
                }
                if(binding.etPassword.getText().toString().isEmpty()){
                    binding.etPassword.setError("Enter Your Password!");
                    return;
                }


                if ( binding.etEmail.getText().toString().contains("@manarat.ac.bd")) {
                    dialog.show();
                    auth.createUserWithEmailAndPassword
                            (binding.etEmail.getText().toString(),
                                    binding.etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        Users user = new Users(binding.etUserName.getText().toString(), binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                                        String id = task.getResult().getUser().getUid();

                                        database.getReference().child("users").child(id).setValue(user);
                                        Toast.makeText(SignUpActivity.this,
                                                "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }, 3000);


                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                } else {
                    dialog.dismiss();
                    binding.etEmail.setError("Enter MIU Domain Email!");
                    return;
                }

                }



        });

        binding.tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


    }
}