package hcmute.edu.vn.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText name, email, password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        }
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);



    }
    public void signUp(View view){

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        if (TextUtils.isEmpty(userName)){
            Toast.makeText(MainActivity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(MainActivity.this, "Enter Email Address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(MainActivity.this, "Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length()<6){
            Toast.makeText(MainActivity.this, "Password to short, Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Toast.makeText(MainActivity.this, "Successfully Register", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(MainActivity.this,LoginActivity.class));
               }
               else {
                   Toast.makeText(MainActivity.this, "Registration Failed"+task.getException(), Toast.LENGTH_SHORT).show();
               }
            }
        });


    }

    public void signIn(View view){
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
}