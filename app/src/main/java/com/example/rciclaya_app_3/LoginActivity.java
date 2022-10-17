package com.example.rciclaya_app_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            finish();
            return;
        }

        Button btIniciarSesion = findViewById(R.id.btIniciarSesion);
        btIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticarUsuario();
            }
        });

        Button btIrRegistro = findViewById(R.id.btRegistrarse);
        btIrRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarRegistrarse();
            }
        });
    }

    private void autenticarUsuario(){
        EditText correo = findViewById(R.id.edtMail);
        EditText contraseña = findViewById(R.id.edtPassword);

        String correo1 = correo.getText().toString();
        String contraseña1 = contraseña.getText().toString();

        if (correo1.isEmpty() || contraseña1.isEmpty()){
            Toast.makeText(this, "Porfavor llene todos los campos", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(correo1, contraseña1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }
}