package com.johnfe.miercoles;

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
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    TextView tvBienvenido;
    TextView tvMensaje;
    Button btnEnviar;
    EditText txtMensaje;
    FirebaseDatabase database;
    DatabaseReference referenciaClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvBienvenido= (TextView) findViewById(R.id.tvBienvenido);
        tvMensaje= (TextView) findViewById(R.id.tvTiempoReal);
        btnEnviar= (Button) findViewById(R.id.btnEnviar);
        txtMensaje= (EditText) findViewById(R.id.etMensaje);


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

        tvBienvenido.setText("Bienvenido : "+mAuth.getCurrentUser().getDisplayName());

        database= FirebaseDatabase.getInstance();
        referenciaClave = database.getReference("miercoles");


        referenciaClave.setValue("Clase Miercoles");
        MyFirebaseInstanceIDService instanceIDService = new MyFirebaseInstanceIDService();
        instanceIDService.onTokenRefresh();

        // Read from the database
        referenciaClave.addValueEventListener(new ValueEventListener() {
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



    }
}
