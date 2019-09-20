package com.example.firebaseandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up_Form extends AppCompatActivity {

    EditText txtFullName, txtUserName, txtEmail, txtPassword;
    Button btn_register;
    RadioButton radioMasculino, radioFemenino;

    String fullName = "", userName = "", email = "", password = "", genero = "";

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__form);

        txtFullName = (EditText) findViewById(R.id.txt_fullName);
        txtUserName = (EditText) findViewById(R.id.txt_userName);
        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        btn_register = (Button) findViewById(R.id.buttonRegister);
        radioMasculino = (RadioButton) findViewById(R.id.radioM);
        radioFemenino = (RadioButton) findViewById(R.id.radioF);

        databaseReference = FirebaseDatabase.getInstance().getReference("Person");
        firebaseAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fullName = txtFullName.getText().toString();
                userName = txtUserName.getText().toString();
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();

                if (radioFemenino.isChecked()) {
                    genero = "F";
                }
                if (radioMasculino.isChecked()) {
                    genero = "M";
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Sign_up_Form.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Sign_up_Form.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(Sign_up_Form.this, "Please Enter Full Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(Sign_up_Form.this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Sign_up_Form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Person information = new Person(
                                            fullName,
                                            userName,
                                            email,
                                            genero
                                    );
                                    FirebaseDatabase.getInstance().getReference("Person").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Sign_up_Form.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });
                                } else {

                                }

                                // ...
                            }
                        });
            }
        });
    }
}
