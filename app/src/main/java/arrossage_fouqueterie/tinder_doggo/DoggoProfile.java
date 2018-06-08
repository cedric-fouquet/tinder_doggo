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
    private int Age;
    private String Race;
    private Bitmap profilePicture;
    private String profileId;
    private FirebaseAuth mAuth =  FirebaseAuth.getInstance();
    private FirebaseDatabase database =FirebaseDatabase.getInstance();
    private StorageReference storageRef =FirebaseStorage.getInstance().getReference();
    private boolean availableprofile;
    private static final String TAG = "DoggoProfile";

    public DoggoProfile(String id){
        this.profileId = id;

    }
    public DoggoProfile(String id,String username, String race, int Age,Bitmap profilePicture)
    {
        this.Age = Age;
        this.Race = race;
        this.profilePicture = profilePicture;

        this.username = username;
        this.profileId = id;
    }
    public void loadProfile(){
        availableprofile =true;
        DatabaseReference myRef = database.getReference().child("user").child(mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, dataSnapshot.toString());
                    username = dataSnapshot.child("username").getValue(String.class);
                    Age = dataSnapshot.child("age").getValue(Integer.class);

                    Race = dataSnapshot.child("race").getValue(String.class);

                } else {
                    availableprofile =true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                availableprofile =true;
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }

        });
        StorageReference pathReference = storageRef.child(mAuth.getUid()+".jpg");
        final long ONE_MEGABYTE = 1024 * 1024 * 10;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                profilePicture =  BitmapFactory.decodeByteArray(bytes, 0, bytes.length);





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception exception) {
                availableprofile =false;
            }
        });

    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return Age;
    }

    public String getRace() {
        return Race;
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
