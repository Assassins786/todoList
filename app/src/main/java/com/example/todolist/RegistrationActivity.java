package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText regEmail, regPass;
    private Button regButton;
    private TextView regQn;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        regEmail = findViewById(R.id.registration_Email);
        regPass = findViewById(R.id.registration_Pass);
        regButton = findViewById(R.id.registration_Button);
        regQn = findViewById(R.id.registration_Page_Question);

        regQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regEmail.getText().toString().trim();
                String password = regPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    regEmail.setError("Email is Required");
                    return;
                }if(TextUtils.isEmpty(password)){
                    regPass.setError("Password Required");
                    return;
                }else {
                    loader.setMessage("Registration in Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegistrationActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }else {
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this, "Registration Failed" + error, Toast.LENGTH_LONG).show();
                                loader.dismiss();
                            }

                        }
                    });
                }
            }
        });
    }


}