package com.NHC.Discount;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emailText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);


        //Kullanıcıyı hatırlar
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){

            Intent feed = new Intent(getApplicationContext(), feedActivity.class);
            startActivity(feed);

        }
    }


    public void signIn(View view) {

        mAuth.signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Intent feed = new Intent(getApplicationContext(), feedActivity.class);
                    startActivity(feed);

                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void signUp(View view) {

        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(signInActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    Intent feed = new Intent(getApplicationContext(), feedActivity.class);
                    startActivity(feed);

                }


            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
