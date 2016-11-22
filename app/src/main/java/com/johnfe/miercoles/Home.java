package com.johnfe.miercoles;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    Button btnDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDatos= (Button) findViewById(R.id.btnDatos);
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Home.this,DatosUsuario.class);
                startActivity(intent);
            }
        });


       // mAuth = FirebaseAuth.getInstance();

/*
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("home", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("home", "onAuthStateChanged:signed_out");
                }
            }
        };
*/


       // database= FirebaseDatabase.getInstance();
       // referenciaClave = database.getReference("miercoles");


      //  referenciaClave.setValue("Clase Miercoles");
//        System.out.println("Token");
//        MyFirebaseInstanceIDService instanceIDService = new MyFirebaseInstanceIDService();
//        instanceIDService.onTokenRefresh();
//        System.out.println("fin del token");

        // Read from the database
   /*     referenciaClave.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String valor = dataSnapshot.getValue(String.class);
                Log.d("Home Activity", "Value is: " + valor);
                tvMensaje.setText("Mensaje en RealTime: "+valor);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Home Activity", "Failed to read value.", error.toException());
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referenciaClave.setValue(txtMensaje.getText().toString().trim());

            }
        });
*/


    }
}
