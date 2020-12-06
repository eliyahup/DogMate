package com.oreo.DogMate.advertiserSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oreo.DogMate.Navigation.Advertiser_Navigation;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * In this activity, the baker can add a new dog or edit an existing one
 *
 */
public class AddDogActivity extends Advertiser_Navigation {
    public static final String TAG = "TAG_ADD_PASTRY";
    String userID;
    String priceIn, nameIn, descIn, allergicIn;
    private FirebaseAuth FireLog;// fire base authentication
    EditText price, name, description, allergenic;
    DatabaseReference pastryRef;
    FirebaseDatabase DB;
    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);
        FireLog = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance();
        pastryRef = DB.getReference("Menu/" + FireLog.getCurrentUser().getUid());
        FirebaseUser user = FireLog.getCurrentUser();
        userID = user.getUid();
        retrieve(); //retrieve all the Edit texts
        Intent intent = getIntent();
        if(intent.hasExtra("Dog")) {
            dog = (Dog) intent.getSerializableExtra("Dog");
            price.setText(dog.getPrice());
            name.setText(dog.getName());
            description.setText(dog.getDescription());
            allergenic.setText(dog.getAllerganics());
        }
    }

    public void addItem(View view) {

        priceIn = price.getText().toString().trim();
        nameIn = name.getText().toString().trim();
        descIn = description.getText().toString().trim();
        allergicIn = allergenic.getText().toString().trim();
        if (TextUtils.isEmpty(priceIn)) {
            price.setError("נא למלא מחיר");
            return;
        }
        if (TextUtils.isEmpty(nameIn)) {
            name.setError("נא למלא שם");
            return;
        }
        if (TextUtils.isEmpty(descIn)) {
            description.setError("נא למלא תיאור");
            return;
        }
        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError!=null){
                    Toast.makeText(getApplicationContext(), "הוספת מאפה נכשלה",
                            Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "מאפה נוסף בהצלחה!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        if(dog ==null) {
            dog = new Dog(priceIn, nameIn, allergicIn, descIn, userID);
            dog.setDocID(pastryRef.push().getKey());
        }
        //if the dog exist and we just want to edit it
        else{
            dog.setPrice(priceIn);
            dog.setName(nameIn);
            dog.setDescription(descIn);
            dog.setAllerganics(allergicIn);
        }
        pastryRef.child(dog.getDocID()).setValue(dog, completionListener);
        DB.getReference("Pastries").child(dog.getDocID()).setValue(dog,completionListener);
        addPicture();
    }

    public void retrieve() {
        price = (EditText) findViewById(R.id.priceInput);
        name = (EditText) findViewById(R.id.nameInput);
        description = (EditText) findViewById(R.id.desInput);
        allergenic = (EditText) findViewById(R.id.alerganicInput);
    }

    public void addPicture() {
        Intent intent = new Intent(AddDogActivity.this, addDogPicturesActivity.class);
        intent.putExtra("Dog", dog);
        startActivity(intent);
    }
}