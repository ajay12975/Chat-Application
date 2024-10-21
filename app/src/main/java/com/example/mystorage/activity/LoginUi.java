package com.example.mystorage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mystorage.R;
import com.example.mystorage.util.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUi extends AppCompatActivity {

    //Globally declared id
    //this can be predefined
    TextView signup;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView txtNeedAccount, txtSignup;
    FirebaseAuth auth;
    String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    android.app.ProgressDialog progressDialog;

    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        sharedPreference = new SharedPreference(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.editTexLogEmail);
        edtPassword = findViewById(R.id.editTextLogPassword);
        btnLogin = findViewById(R.id.logbutton);
        signup = findViewById(R.id.logsignup);

        // Handle the signup button click to navigate to Registration UI
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUi.this, RegistrationUi.class);
                startActivity(intent);
                finish();
            }
        });

        // Handle the login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = edtEmail.getText().toString();
                String Password = edtPassword.getText().toString();

                // Validate email and password inputs
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(LoginUi.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(LoginUi.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!Email.matches(emailPattern)) {
                    edtEmail.setError("Give Proper Email Address");
                } else if (Password.length() < 6) {
                    edtPassword.setError("Password should be more than six characters");
                    Toast.makeText(LoginUi.this, "Password should be more than six characters", Toast.LENGTH_SHORT).show();
                } else {
                    // Show ProgressDialog while the login process is ongoing
                    progressDialog.show();

                    auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Dismiss ProgressDialog after task completion
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                // Successful login
                                sharedPreference.setLogin(true);
                                Intent intent = new Intent(LoginUi.this, MainActivity.class);
                                startActivity(intent);
                                finish();  // Close LoginUi after successful login
                            } else {
                                // Show error message in case of login failure
                                Toast.makeText(LoginUi.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
