package com.himanshu.smartcanister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import static android.R.attr.password;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

import static com.google.firebase.auth.FirebaseAuth.*;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstatelistener;
    EditText email1,password1,password2,name;
    Button register;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    private ProgressDialog mprogressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name= (EditText) findViewById(R.id.et_name);
        email1 = (EditText) findViewById(R.id.et_email);
        password1 = (EditText) findViewById(R.id.et_pass);
        password2 = (EditText) findViewById(R.id.et_cpass);

        mprogressdialog = new ProgressDialog(this);

        mAuthstatelistener = new AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }
        };



        register = (Button)findViewById(R.id.bu_reg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnSignup();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthstatelistener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthstatelistener != null) {
            mAuth.removeAuthStateListener(mAuthstatelistener);
        }
    }

    public void OnSignup(){
        final String email = email1.getText().toString();
        final String password = password1.getText().toString();
        final String cpassword = password2.getText().toString();
        final String nameText=name.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword) || TextUtils.isEmpty(nameText) ){
            Toast.makeText(RegisterActivity.this,"Fields are Empty",Toast.LENGTH_LONG).show();
        }
        else if(!password.equals(cpassword)) {
            Toast.makeText(RegisterActivity.this, "Password and Confirm Password don't match!", Toast.LENGTH_LONG).show();
        }
        else {

            mprogressdialog.setMessage("Signing Up");
            mprogressdialog.show();
            mprogressdialog.setCanceledOnTouchOutside(false);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Successfully Signed Up", Toast.LENGTH_LONG).show();
                        upload();
                    } else if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                        mprogressdialog.dismiss();
                    }
                }
            });
        }
    }

    private void upload() {
        final String email = email1.getText().toString();
        final String nameText=name.getText().toString();
        FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
        String curr = current.getUid();

        mDatabase = database.getReference().child("Users").child(curr);

        HashMap<String,String> data = new HashMap<>();
        data.put("Name",nameText);

        data.put("Email",email);

        data.put("Image","https://firebasestorage.googleapis.com/v0/b/smart-canister.appspot.com/o/468.png?alt=media&token=3fecec7d-dae6-4d9c-9210-3aaf7f0717b0");
        mDatabase.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Data Updated Succesfully", Toast.LENGTH_LONG).show();
                    mprogressdialog.dismiss();
                }
                else {
                    mprogressdialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Data Update Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
