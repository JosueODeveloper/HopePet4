package com.example.hopepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarActivity extends AppCompatActivity {

    private Button mRegistrar;
    private EditText mEmail, mSenha, mNome;

    private RadioGroup mRadioGroup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user !=null) {
                    Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mRegistrar = (Button) findViewById(R.id.registrar);

        mEmail  =   (EditText) findViewById(R.id.email);

        mSenha  =   (EditText) findViewById(R.id.senha);

        mNome  =   (EditText) findViewById(R.id.nome);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        mRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectId = mRadioGroup.getCheckedRadioButtonId();

                final RadioButton radioButton = (RadioButton) findViewById(selectId);

                if (radioButton.getText() == null){
                    return;
                }


                final String email = mEmail.getText().toString();
                final String senha = mSenha.getText().toString();
                final String nome = mNome.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(RegistrarActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistrarActivity.this, "Erro ao criar conta!", Toast.LENGTH_SHORT).show();
                        }else{
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(radioButton.getText().toString()).child(userId).child("nome");
                            currentUserDb.setValue(nome);
                            //Map userInfo = new HashMap<>();
                            //userInfo.put("Nome", nome);
                            //userInfo.put("sex", radioButton.getText().toString());
                            //userInfo.put("profileImageUrl", "default");
                            //currentUserDb.updateChildren(userInfo);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}