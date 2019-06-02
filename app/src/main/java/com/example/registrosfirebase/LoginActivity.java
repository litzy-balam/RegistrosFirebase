package com.example.registrosfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
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

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail,inputPassword;
    private Button btnLogin, btnSignUp, btnReset;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //get firebase auth instance
        auth=FirebaseAuth.getInstance();

        /*if(auth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }*/

        //set view now
        setContentView(R.layout.activity_login);

            //marca error porque no tenemos un toolbar ,y tampoco la usamos asi que como no es necesario la quite
      //  Toolbar toolbar=(Toolbar)findViewById(R.id.toollbar);
        //setSupportActionBar(toolbar)


        btnSignUp=(Button)findViewById(R.id.btn_signup);
        btnLogin=(Button)findViewById(R.id.btn_login);
        inputEmail=(EditText)findViewById(R.id.email);
        inputPassword=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        btnReset=(Button)findViewById(R.id.btn_reset_password);


        //get firebase auth instance
        auth=FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=inputEmail.getText().toString();
                final String password= inputPassword.getText().toString();

                if(TextUtils.isEmpty(email)){

                    Toast.makeText(getApplicationContext(),"Enter Email Address!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Introducir la contraseña!",Toast.LENGTH_SHORT).show();
                    return;

                }

                progressBar.setVisibility(View.VISIBLE);

                //autenticacion de usuario
                auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                /* Si falla el inicio de sesion muestra un mesaje al usuario . sie el inicio de sesion tiene exito se notificara al oyente
                                * del estado de autentificacion y se le asignara logica para manejar en el oyente*/
                                progressBar.setVisibility(View.GONE);

                                if(!task.isSuccessful()){
                                    //aqui marcara un error
                                    if(password.length()<6){
                                        inputPassword.setError(getString(R.string.minimun_password));
                                    }else{

                                        Toast.makeText(LoginActivity.this,getString(R.string.auth_failed),Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
