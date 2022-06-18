package hcmute.edu.vn.ecapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import hcmute.edu.vn.ecapplication.R;

public class AddAddressActivity extends AppCompatActivity {
    // EditText get information of user: address, postalCode,..etc
    EditText name,address,city,postalCode,phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;
    // Khai bao lien ke FireBase
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar=findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        // Show action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Back Page <-
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // mapping object
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        phoneNumber = findViewById(R.id.ad_phone);
        postalCode = findViewById(R.id.ad_code);
        addAddressBtn = findViewById(R.id.ad_add_address);

        // Click event of the Add Address's button
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Data to save on Firebase
                String userName = name.getText().toString();
                String userCity = city.getText().toString();
                String userAddress = address.getText().toString();
                String userCode = postalCode.getText().toString();
                String userNumber = phoneNumber.getText().toString();

                String final_address = "";

                if (!userName.isEmpty()){
                    final_address+=userName+", ";
                }

                if (!userCity.isEmpty()){
                    final_address+=userCity+", ";
                }

                if (!userAddress.isEmpty()){
                    final_address+=userAddress+", ";
                }

                if (!userCode.isEmpty()){
                    final_address+=userCode+", ";
                }

                if (!userNumber.isEmpty()){
                    final_address+=userNumber+". ";
                }

                if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()){

                    Map<String,String> map = new HashMap<>();
                    map.put("userAddress",final_address);
                    // Generate address of user in CurrentUser document
                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    //if  add successful then display the message and back to address_activity
                                    if (task.isSuccessful()){
                                        Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(AddAddressActivity.this,DetailedActivity.class));
                                        finish();
                                    }

                                }
                            });

                }else {
                    Toast.makeText(AddAddressActivity.this, "Kindly Fill All Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}