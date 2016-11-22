package com.johnfe.miercoles;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class DatosUsuario extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference personaRef;

    private EditText txtNombre;
    private EditText txtApellidos;
    private EditText txtTelefono;
    private EditText txtCedula;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_usuario);

        txtApellidos= (EditText) findViewById(R.id.txtApellidos);
        txtCedula= (EditText) findViewById(R.id.txtCedula);
        txtNombre= (EditText) findViewById(R.id.txtNombre);
        txtTelefono= (EditText) findViewById(R.id.txtTelefono);
        btnGuardar= (Button) findViewById(R.id.btnGuardarDatos);

        mAuth = FirebaseAuth.getInstance();
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

        database= FirebaseDatabase.getInstance();
        personaRef = database.getReference("persona");
       // if(personaRef.child("").)

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserProfileChangeRequest usuario = new UserProfileChangeRequest.Builder()
                        .setDisplayName(txtNombre.getText().toString().trim())
                        //.setPhotoUri(Uri.parse("https://fbcdn-sphotos-e-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/30382_10151143006550458_1277185577_n.jpg?oh=75fd5ad8033c23b7deb3f30fbec965c1&oe=58CC7890&__gda__=1490377027_e3d19a221bbcaded909296e7fab737fe"))
                        .build();

                mAuth.getCurrentUser().updateProfile(usuario);
                personaRef = database.getReference("persona").child(mAuth.getCurrentUser().getUid());
                personaRef.child("Nombres").setValue(txtNombre.getText().toString());
                personaRef.child("apellidos").setValue(txtApellidos.getText().toString());
                personaRef.child("telefonos").child("celular").setValue(txtTelefono.getText().toString());
                personaRef.child("telefonos").child("fijo").setValue("8639556");
                personaRef.child("cedula").setValue(txtCedula.getText().toString());
                personaRef.child("dispositivoDondeGuardo").setValue(FirebaseInstanceId.getInstance().getToken());

                personaRef.getParent().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                            System.out.println(dataSnapshot.getChildrenCount());

                        //Log.d("Home Activity", "Value is: " + valor);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Home Activity", "Failed to read value.", error.toException());
                    }
                });

            }
        });

    }
}
