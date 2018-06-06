package arrossage_fouqueterie.tinder_doggo;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Console;
import java.io.File;

public class MainMenu extends BaseActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final String TAG = "MainMenu";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private StorageReference storageRef ;
    private Bitmap profileImage;


    private ImageView profileImageView;
    private TextView userNameView;

    private Button getNewDoggosButton;
    private Button matchedDoggosButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getApplicationContext();
        storageRef = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_main_menu);

        profileImageView = findViewById(R.id.imageViewprofile);
        userNameView = findViewById(R.id.textViewUserName);
        matchedDoggosButton = findViewById(R.id.buttonMatchedDoggos);

        getNewDoggosButton = findViewById(R.id.buttonGetNewDoggos);

        userNameView.setVisibility(View.GONE);
        profileImageView.setVisibility(View.GONE);
        matchedDoggosButton.setVisibility(View.GONE);
        getNewDoggosButton.setVisibility(View.GONE);
        super.onCreate(savedInstanceState);

        DatabaseReference myRef = database.getReference().child("user").child(mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.v(TAG,dataSnapshot.toString());
                    userNameView.setText(dataSnapshot.child("username").getValue().toString());
                    userNameView.setVisibility(View.VISIBLE);
                    matchedDoggosButton.setVisibility(View.VISIBLE);
                    getNewDoggosButton.setVisibility(View.VISIBLE);

                } else {
                    Intent page = new Intent(MainMenu.this,Inscription.class);
                    startActivity(page);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }

        });

        StorageReference pathReference = storageRef.child(mAuth.getUid()+".jpg");
        final long ONE_MEGABYTE = 1024 * 1024 * 10;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                profileImage =  BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileImageView.setImageBitmap(profileImage);


                profileImageView.setVisibility(View.VISIBLE);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception exception) {
                userNameView.setText("Error");
                userNameView.setVisibility(View.VISIBLE);
            }
        });


        matchedDoggosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        getNewDoggosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homepage = new Intent(MainMenu.this, DoggoMatcher.class);
                startActivity(homepage);
            }
        });


    }

}
