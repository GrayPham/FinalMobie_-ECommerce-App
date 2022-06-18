package hcmute.edu.vn.ecapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hcmute.edu.vn.ecapplication.R;

public class RegistrationActivity extends AppCompatActivity {
    EditText name, email, password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(RegistrationActivity.this,HomeActivity.class));
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
            Toast.makeText(RegistrationActivity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(RegistrationActivity.this, "Enter Email Address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(RegistrationActivity.this, "Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length()<6){
            Toast.makeText(RegistrationActivity.this, "Password to short, Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Successfully Register", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                }
                else {
                    Toast.makeText(RegistrationActivity.this, "Registration Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void signIn(View view){
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
    }

    public static class SliderAdapter extends PagerAdapter {

        Context context;
        public SliderAdapter(Context context){
            this.context = context;
        }
        int imagesArray[] = {
                R.drawable.onboardscreen1,
                R.drawable.onboardscreen2,
                R.drawable.onboardscreen3,
        };

        int headingArray[]={
                R.string.first_slide,
                R.string.second_slide,
                R.string.third_slide,
        };
        int descriptionArray[]={
                R.string.description,
                R.string.description,
                R.string.description,
        };

        @Override
        public int getCount() {
            return headingArray.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == (ConstraintLayout) object;
        }


        @NonNull
        @Override
        public Object instantiateItem (@NonNull ViewGroup container, int position){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view  = layoutInflater.inflate(R.layout.sliding_layout,container,false);

            ImageView imageView = view.findViewById(R.id.slider_img);
            TextView heading  = view.findViewById(R.id.heading);
            TextView description = view.findViewById(R.id.description);

            imageView.setImageResource(imagesArray[position]);
            heading.setText(headingArray[position]);
            description.setText(descriptionArray[position]);

            container.addView(view);

            return view;
        }
        @Override
        public void destroyItem (@NonNull ViewGroup container, int position, @NonNull Object object){
            container.removeView((ConstraintLayout)object);
        }
    }
}