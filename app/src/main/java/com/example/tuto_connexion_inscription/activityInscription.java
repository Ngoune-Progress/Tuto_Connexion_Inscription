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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activityInscription extends AppCompatActivity {

    private EditText nom, email, motDePasse;
    private Button inscription;
    private TextView connexion;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        bindViews();

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String InscriptionNom = nom.getText().toString().trim();
                final String InscriptionMotDePasse = motDePasse.getText().toString().trim();
                final String InscriptionEmail = email.getText().toString().trim();

                if(validateInput(InscriptionNom, InscriptionMotDePasse, InscriptionEmail))
                    inscrireUnUtilisateur(InscriptionNom, InscriptionMotDePasse, InscriptionEmail);

            }
        });


        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activityInscription.this, activityConnexion.class));
            }
        });

    }


    private void bindViews(){

        nom = findViewById(R.id.inscription_nom);
        email =  findViewById(R.id.inscription_email);
        motDePasse = findViewById(R.id.inscription_mdp);
        connexion = findViewById(R.id.lien_connexion);
        inscription = findViewById(R.id.btn_inscription);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void inscrireUnUtilisateur(final String enNom, final String enMdp, String enEmail) {

        progressDialog.setMessage("Verification en cour...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(enEmail,enMdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    sendUserData(enNom, enMdp);
                    Toast.makeText(activityInscription.this,"vous avez été inscrire avec succès.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(activityInscription.this,MainActivity.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(activityInscription.this,"L'addresse email existe déjà.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void sendUserData(String username, String password){

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference utilisateurs = firebaseDatabase.getReference("utilisateurs");
        Profil profil = new Profil (username, password);
        utilisateurs.push().setValue(profil);

    }

    private boolean validateInput(String EnNom, String EnMdp, String EnEmail){

        if(EnNom.isEmpty()){
            nom.setError("Entrez un nom d'utlisateur.");
            return false;
        }
        if(EnMdp.isEmpty()){
            motDePasse.setError("Entrez un mot depasse.");
            return false;
        }
        if(EnEmail.isEmpty()){
            email.setError("Entrez un adresse email.");
            return false;
        }

        return true;
    }


}
