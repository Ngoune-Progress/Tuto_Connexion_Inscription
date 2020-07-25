package com.example.tuto_connexion_inscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activityConnexion extends AppCompatActivity {
    private EditText email, motDePasse;
    private TextView mdpOublie, Inscription;
    private Button btnConnexion;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser utilisateur;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        initializeGUI();

        utilisateur = firebaseAuth.getCurrentUser();

        if(utilisateur != null) {
            finish();
            startActivity(new Intent(activityConnexion.this,MainActivity.class));
        }

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inEmail = email.getText().toString();
                String inPassword = motDePasse.getText().toString();

                if(validateInput(inEmail, inPassword)){
                    signUser(inEmail, inPassword);
                }

            }
        });


        Inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activityConnexion.this,activityInscription.class));
            }
        });

        mdpOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activityConnexion.this,ActivityReinitialisation.class));
            }
        });

    }



    public void signUser(String email, String password){

        progressDialog.setMessage("Verification en cours...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(activityConnexion.this,"Connexion réussi",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(activityConnexion.this,MainActivity.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(activityConnexion.this,"Email ou mot de passe invalide.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void initializeGUI(){

        email = findViewById(R.id.connexion_email);
        motDePasse = findViewById(R.id.connexion_mdp);
        mdpOublie = findViewById(R.id.mdpOublie);
        Inscription = findViewById(R.id.lien_inscription);
        btnConnexion = findViewById(R.id.btn_connectez);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

    }


    public boolean validateInput(String enEmail, String enMdp){

        if(enEmail.isEmpty()){
            email.setError("Entrez un adresse email.");
            return false;
        }
        if(enMdp.isEmpty()){
            motDePasse.setError("Entrez un mot de passe.");
            return false;
        }

        return true;
    }

}
