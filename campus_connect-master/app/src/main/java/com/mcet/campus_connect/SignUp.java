package com.mcet.campus_connect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtName;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btnSignup);

        btnSignUp.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            signup(name, email, password);
        });
    }

    private void signup(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : null;
                        if (uid != null) {
                            addUserToDatabase(name, email, uid);
                        }
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, "some error occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addUserToDatabase(String name, String email, String uid) {
        mDbRef = FirebaseDatabase.getInstance().getReference();
        mDbRef.child("user").child(uid).setValue(new user(name, email, uid));
    }
}
