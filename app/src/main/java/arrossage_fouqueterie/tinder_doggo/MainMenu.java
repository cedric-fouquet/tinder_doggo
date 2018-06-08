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

    private static final String TAG = "MainMenu";




    private ImageView profileImageView;
    private TextView userNameView;

    private Button getNewDoggosButton;
    private Button matchedDoggosButton;
    private DoggoProfile doggoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getApplicationContext();
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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        doggoUser = new DoggoProfile(mAuth.getUid());
        doggoUser.loadProfile();
        if(doggoUser.isAvailableprofile())
        {
            userNameView.setText(doggoUser.getUsername());
            profileImageView.setImageBitmap(doggoUser.getProfilePicture());

            userNameView.setVisibility(View.VISIBLE);
            profileImageView.setVisibility(View.VISIBLE);
            matchedDoggosButton.setVisibility(View.VISIBLE);
            getNewDoggosButton.setVisibility(View.VISIBLE);
        }



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
