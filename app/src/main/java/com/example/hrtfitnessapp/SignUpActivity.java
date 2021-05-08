package com.example.hrtfitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private Button SignUpButton;
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword, editTextPasswordConfirmation, editTextUserName,confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Email input field
        editTextEmail = (EditText) findViewById(R.id.email);

        //Password input field
        editTextPassword = (EditText) findViewById(R.id.password);

        //Username input field
        editTextUserName = (EditText) findViewById(R.id.Name);

        editTextPasswordConfirmation = (EditText) findViewById(R.id.confirmpassword);
        //Register button

        mAuth = FirebaseAuth.getInstance();
        SignUpButton = (Button) findViewById(R.id.SignUpButton);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });

    }

    private void signInUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmpassword = editTextPassword.getText().toString().trim();

        //Check for email input
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        //Check for valid form email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email");
            editTextEmail.requestFocus();
            return;
        }

        //Check for password input
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        //Check password length
        if (password.length() < 6) {
            editTextPassword.setError("Min password length should be 6 characters");
            editTextPassword.requestFocus();
        }
        if (confirmpassword.equals(password)){
            editTextPassword.setError("Your passwords doesnt match");
            editTextPassword.requestFocus();
        }

        //Begin sign in user
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //When all credential input correctly
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){

                        // Creates a Shared Preferences file locally on the device to store whether remember me was checked or not
//                        if(rememberMe.isChecked()){ // Is checked
//                            SharedPreferences sharedPreferences = getSharedPreferences("remember me", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("remember", "true"); // Store that "remember me" is true
//                            editor.apply(); // Send to local file
//                        }else if(!rememberMe.isChecked()){
//                            SharedPreferences sharedPreferences = getSharedPreferences("remember me", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("remember", "false"); // Store that "remember me" is false
//                            editor.apply(); // Send to local file
//
//                        }

                        //Redirect to main menu
                        Intent it = new Intent();
                        //it.setClass(UserAuthentication.this, MainMenu.class);
                        startActivity(it);
                    }
                    else{
                        user.sendEmailVerification();
                        //Toast.makeText(UserAuthentication.this, "Please check your email to verify your account", Toast.LENGTH_LONG).show();
                    }
                }
                //Show error
                else{
                    // Toast.makeText(UserAuthentication.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


}