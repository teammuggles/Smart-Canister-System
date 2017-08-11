package com.himanshu.smartcanister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN=1;
    private TextView head;
    private EditText email,password;
    private Button signIn,signUp;
    private ProgressDialog progressDialog;
    private GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        signInButton= (SignInButton) findViewById(R.id.sign_in_button);
        signIn = (Button) findViewById(R.id.bt_signIn);
        signUp = (Button) findViewById(R.id.bt_signUp);
        email= (EditText) findViewById(R.id.et_name);
        password= (EditText) findViewById(R.id.et_pass);
        signInButton.setOnClickListener(this);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
       switch (v.getId())
       {
           case R.id.sign_in_button:
               signIn();
               break;

           case R.id.bt_signIn:
               signInFirebase();
               break;

           case R.id.bt_signUp:
               signUp();
               break;
       }
    }

    public void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void handleSignInResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            account=result.getSignInAccount();
            firebaseAuthWithGoogle(account);


        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        AuthCredential credential=GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Signing In..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    String name=account.getDisplayName();
                    String email=account.getEmail();
                    Uri photoUri=account.getPhotoUrl();
                    String photo=photoUri.toString();
                    DatabaseReference userLocation= FirebaseDatabase.getInstance().getReference();
                    FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
                    String curr = current.getUid();
                    DatabaseReference mDatabase=userLocation.child("Users").child(curr);
                    HashMap<String,String> data = new HashMap<>();
                    data.put("Name",name);

                    data.put("Email",email);

                    data.put("Image",photo);
                    mDatabase.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(LoginActivity.this, "Data Updated Succesfully", Toast.LENGTH_LONG).show();

                            }
                            else {

                                Toast.makeText(LoginActivity.this, "Data Update Failed", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    progressDialog.dismiss();
                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null)
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    public void signUp()
    {
             startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    public void signInFirebase()
    {
        progressDialog=new ProgressDialog(this);
       String emailText=email.getText().toString();
       String passText=password.getText().toString();
        if(TextUtils.isEmpty(emailText)||TextUtils.isEmpty(passText))
        {
            Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setMessage("Signing In...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(emailText, passText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {   progressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}
