package com.example.githubauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private EditText gitemail;
    private Button gitlogin;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gitemail = findViewById(R.id.github_email);
        gitlogin = findViewById(R.id.btnlogin);
        fAuth = FirebaseAuth.getInstance();

        gitlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(gitemail.getText().toString())){
                    Toast.makeText(MainActivity.this,"Enter Email",Toast.LENGTH_SHORT).show();
                }else {
                    SignInWithGithubProvider(
                            OAuthProvider.newBuilder("github.com")
                                    .addCustomParameter("login",gitemail.getText().toString()).setScopes(
                                            new ArrayList<String>(){
                                                {
                                                    add("user:email");
                                                }
                                            })
                                    .build()

                    );
                }

            }
        });
    }

    private void SignInWithGithubProvider(OAuthProvider login)
    {
        Task<AuthResult> pendingAuthTask = fAuth.getPendingAuthResult();
        if (pendingAuthTask != null){
            pendingAuthTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this,"User Exits",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            fAuth.startActivityForSignInWithProvider(this,login).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    FirebaseUser user = fAuth.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
                    intent.putExtra("githubname",user.getEmail());
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}