package com.johnfe.miercoles;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button btnSingup;
    private Button btnSingin;
    private EditText txtEmail;
    private EditText txtPassword;
    private TextView lbNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSingin= (Button) findViewById(R.id.btnSignin);
        btnSingup= (Button) findViewById(R.id.btnSignup);
        txtEmail= (EditText) findViewById(R.id.txtEmail);
        txtPassword= (EditText) findViewById(R.id.txtPassword);
        lbNombre= (TextView) findViewById(R.id.lbNombre);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("MainAcitvity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("MainActivity", "onAuthStateChanged:signed_out");
                }
            }
        };

        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString().trim(), txtPassword.getText().toString().trim())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("OnComplete Registro", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Error en autenticcacion. "+task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    System.out.println(task.getException().getMessage());
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Registro Exitoso ",
                                            Toast.LENGTH_SHORT).show();
                                    mAuth.getCurrentUser().sendEmailVerification()
                                    .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            System.out.println(e);
                                            Toast.makeText(MainActivity.this, "Error en Enviar Email. "+e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }

                                // ...
                            }
                        });
            }
        });

        btnSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signInWithEmailAndPassword(txtEmail.getText().toString().trim(), txtPassword.getText().toString().trim())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("Inicio Sesion", "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("Inicio sesion tarea", "signInWithEmail", task.getException());
                                    Toast.makeText(MainActivity.this, "Inicio de sesion Fallida: "+task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.w("Inicio sesion tarea", "signInWithEmail", task.getException());

                                    if(mAuth.getCurrentUser().isEmailVerified()){

                                        UserProfileChangeRequest usuario = new UserProfileChangeRequest.Builder()
                                                .setDisplayName("pepito perez")
                                                .setPhotoUri(Uri.parse("https://fbcdn-sphotos-e-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/30382_10151143006550458_1277185577_n.jpg?oh=75fd5ad8033c23b7deb3f30fbec965c1&oe=58CC7890&__gda__=1490377027_e3d19a221bbcaded909296e7fab737fe"))
                                                .build();

                                        mAuth.getCurrentUser().updateProfile(usuario);
                                        Intent intent = new Intent(MainActivity.this, Home.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(MainActivity.this, "Debe Validar Email",
                                                Toast.LENGTH_SHORT).show();
                                    }


                                }

                                // ...
                            }
                        });
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}