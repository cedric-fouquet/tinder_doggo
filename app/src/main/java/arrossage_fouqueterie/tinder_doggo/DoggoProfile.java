package arrossage_fouqueterie.tinder_doggo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

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

/**
 * Created by f16007622 on 08/06/18.
 */

public class DoggoProfile {

    private String username;
    private int age;
    private String race;
    private Bitmap profilePicture;
    private String profileId;

    private boolean availableprofile;
    private static final String TAG = "DoggoProfile";

    public DoggoProfile(String id){
        this.profileId = id;
        availableprofile =false;
    }
    public DoggoProfile(String id,String username, String race, int age,Bitmap profilePicture)
    {
        availableprofile =true;
        this.age = age;
        this.race = race;
        this.profilePicture = profilePicture;

        this.username = username;
        this.profileId = id;
    }

    public void loadProfileInfo(DataSnapshot snapshot){

        availableprofile =true;
        age = snapshot.child("age").getValue(Integer.class);
        username = snapshot.child("username").getValue(String.class);
        race = snapshot.child("race").getValue(String.class);


    }
    public void loadImage(byte[] image){
        profilePicture = BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    public String getUsername() {
        return username;
    }

    public int getage() {
        return age;
    }

    public String getrace() {
        return race;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public String getProfileId() {
        return profileId;
    }

    public boolean isAvailableprofile() {
        return availableprofile;
    }
}
