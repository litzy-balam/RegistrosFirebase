package com.example.registrosfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import  android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail,inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get firebase auth instance
        auth=FirebaseAuth.getInstance();

        btnSignIn=(Button)findViewById(R.id.sign_in_button);
        btnSignUp=(Button)findViewById(R.id.sign_up_button);
        inputEmail=(EditText)findViewById(R.id.email);
        inputPassword=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        btnResetPassword=(Button)findViewById(R.id.btn_reset_password);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=inputEmail.getText().toString().trim();
                String password=inputPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){

                    Toast.makeText(getApplicationContext(),"Introduzca la direccion de correo electrónico!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Introducir la ontraseña!",Toast.LENGTH_SHORT).show();
                    return;

                }

                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Contraseña demasiado corta ingrese un mínimo de 6 caracteres!",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //crear usuario
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this,"createUserWithEmail:onComplete:"+task.isSuccessful(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                /*si falla el inicio de sesion muestre un mesanje al ususario.Si el inicio tiene exito se notificara
                                al oyente del estado de autentificacion y se le asignara logica para manejar usuario con sesion iniciada puede manejar
                                en el oyente*/

                                if(task.isSuccessful()){

                                    Toast.makeText(SignupActivity.this,"Auntetificación fallida."+task.getException(),Toast.LENGTH_SHORT).show();

                                    }else{
                                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                }
                            }

                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
