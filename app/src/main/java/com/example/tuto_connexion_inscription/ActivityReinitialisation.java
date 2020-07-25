package com.example.tuto_connexion_inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityReinitialisation extends AppCompatActivity {

    private TextView textConnexion;
    private EditText email;
    private Button btnReinitialiser;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reinitialisation);
        initializeGUI();

        btnReinitialiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = ActivityReinitialisation.this.email.getText().toString();

                if (email.isEmpty()) {
                    ActivityReinitialisation.this.email.setError("Veuillez entrer l'adresse email",null);
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ActivityReinitialisation.this, "L'adresse email a été envoyé avec succès.", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ActivityReinitialisation.this, activityConnexion.class));
                            } else {
                                Toast.makeText(ActivityReinitialisation.this, "L'adresse email n'est pas valide.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });


        textConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityReinitialisation.this,activityConnexion.class));
            }
        });


    }


    private void initializeGUI(){
        textConnexion = findViewById(R.id.lien_connexion);
        email = findViewById(R.id.mdpOublie_email);
        btnReinitialiser = findViewById(R.id.btn_reinitialiser);

        firebaseAuth = FirebaseAuth.getInstance();

    }
}
