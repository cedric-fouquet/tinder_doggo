package arrossage_fouqueterie.tinder_doggo;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DoggoMatcher extends BaseActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = "DoggoMatcher";

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    private TextView nameView;
    private TextView ageView;
    private TextView raceView;
    private ImageButton acceptDoggoButton;
    private ImageButton rejectDoggoButton;
    private ImageView profilePicture;
    private List<DoggoProfile> usersList =  new ArrayList<DoggoProfile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doggo_matcher);

        nameView = findViewById(R.id.textViewName);
        ageView = findViewById(R.id.textViewAge);
        raceView = findViewById(R.id.textViewRace);
        acceptDoggoButton = findViewById(R.id.imageButtonAccept);
        rejectDoggoButton = findViewById(R.id.imageButtonReject);
        profilePicture = findViewById(R.id.imageViewProfileMatcher);

        nameView.setVisibility(View.INVISIBLE);
        ageView.setVisibility(View.INVISIBLE);
        raceView.setVisibility(View.INVISIBLE);
        acceptDoggoButton.setVisibility(View.INVISIBLE);
        rejectDoggoButton.setVisibility(View.INVISIBLE);
        profilePicture.setVisibility(View.INVISIBLE);

        final TextView nameView = findViewById(R.id.textViewName);

        DatabaseReference myRef = database.getReference().child("user");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    String currentId = dataSnapshot.child("profileId").getValue(String.class);
                    if( currentId == mAuth.getUid()){
                        break;
                    }

                    final DoggoProfile doggerino = new  DoggoProfile( dataSnapshot.child("profileId").getValue(String.class));
                    doggerino.loadProfileInfo(postSnapshot);

                    //getimage
                    StorageReference image = storageRef.child(mAuth.getUid()+".jpg");

                    final long ONE_MEGABYTE = 1024 * 1024 * 100;

                    image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            doggerino.loadImage(bytes);
                            usersList.add(doggerino);
                            if(!usersList.isEmpty())
                            {
                                showCurrentUser(0);
                            }

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        //TODO : Implémenter la position si t'as le temps :'(
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//            return;
//        }
//        Log.d(TAG, mFusedLocationClient.toString());
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//
//                            mDatabase.getReference().child("positions").child(mAuth.getUid()).setValue(location);
//                            Log.d(TAG, location.toString());
//                        }
//                    }
//
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onfailure: ");
//            }
//        });




    }
    protected void showCurrentUser(int listIndex){

        nameView.setText(usersList.get(listIndex).getUsername());
        nameView.setVisibility(View.VISIBLE);

        ageView.setText(Integer.toString(usersList.get(listIndex).getage()));
        ageView.setVisibility(View.VISIBLE);
        raceView.setText(usersList.get(listIndex).getrace());
        raceView.setVisibility(View.VISIBLE);

        acceptDoggoButton.setVisibility(View.VISIBLE);
        rejectDoggoButton.setVisibility(View.VISIBLE);
        profilePicture.setImageBitmap(usersList.get(listIndex).getProfilePicture());
        profilePicture.setVisibility(View.VISIBLE);

    }


}
