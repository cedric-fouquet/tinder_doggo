package arrossage_fouqueterie.tinder_doggo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DoggoMatcher extends BaseActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = "DoggoMatcher";

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    private TextView nameView;
    private TextView ageView;
    private TextView raceView;
    private ImageButton acceptDoggoButton;
    private ImageButton rejectDoggoButton;
    private ImageView profilePicture;
    private int currentProfileIndex;
    private long fetchedListSize;
    private ArrayList<String> stringMatchedList = new ArrayList<>();
    private ArrayList<DoggoProfile> matchedUserList =  new ArrayList<>();
    private DoggoProfile myDoggoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doggo_matcher);

        DatabaseReference myRefMatchedList = database.getReference().child("user").child(mAuth.getUid().toString());
        myRefMatchedList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshotId : dataSnapshot.child("matchedIdList").getChildren()){
                    stringMatchedList.add(snapshotId.getValue(String.class));

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

        rejectDoggoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRejectDoggoButton();
            }
        });
        acceptDoggoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAcceptDoggoButton();
            }
        });
        profilePicture.setVisibility(View.INVISIBLE);


        final TextView nameView = findViewById(R.id.textViewName);

        DatabaseReference myRef = database.getReference().child("user");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotList) {
                Log.d(TAG, "onDataChange: "+dataSnapshotList.getChildrenCount());
                fetchedListSize = dataSnapshotList.getChildrenCount();
                for (DataSnapshot currentSnapshot: dataSnapshotList.getChildren()) {

                    String currentSnapshotId = currentSnapshot.getKey();
                    if( currentSnapshotId == mAuth.getUid() && !stringMatchedList.contains(currentSnapshotId)){
                        continue;
                    }

                    final DoggoProfile doggerino = new  DoggoProfile( currentSnapshotId);
                    doggerino.loadProfileInfo(currentSnapshot);

                    //getimage
                    StorageReference image = storageRef.child(currentSnapshotId+".jpg");

                    final long ONE_MEGABYTE = 1024 * 1024 * 100;

                    image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            doggerino.loadImage(bytes);
                            matchedUserList.add(doggerino);

                            if(!matchedUserList.isEmpty())
                            {
                                if(matchedUserList.size()==fetchedListSize)
                                {
                                    currentProfileIndex = 0;
                                    showCurrentUser();
                                }

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

    @Override
    protected void onStop() {
         myDoggoUser = new DoggoProfile(mAuth.getUid());
        final DatabaseReference myRef = database.getReference().child("user").child(mAuth.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Log.d(TAG, dataSnapshot.toString());
                    myDoggoUser.loadProfileInfo(dataSnapshot);
                    myDoggoUser.setMatchedIdList(stringMatchedList);
                    myRef.setValue(myDoggoUser);
                    database.getReference().child("user").child(mAuth.getUid()).setValue(myDoggoUser);

                } else {
                    Log.w(TAG, "loadPost:noData");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }

        });
        super.onStop();

    }

    protected void showCurrentUser(){

        int listIndex = currentProfileIndex;
        nameView.setText(matchedUserList.get(listIndex).getUsername());
        nameView.setVisibility(View.VISIBLE);

        ageView.setText(Integer.toString(matchedUserList.get(listIndex).getage()));
        ageView.setVisibility(View.VISIBLE);
        raceView.setText(matchedUserList.get(listIndex).getrace());
        raceView.setVisibility(View.VISIBLE);

        acceptDoggoButton.setVisibility(View.VISIBLE);
        rejectDoggoButton.setVisibility(View.VISIBLE);
        profilePicture.setImageBitmap(matchedUserList.get(listIndex).getProfilePicture());
        profilePicture.setVisibility(View.VISIBLE);

    }
    protected void nextDoggo(){
        currentProfileIndex += 1;
        if(currentProfileIndex< matchedUserList.size())
        {
            showCurrentUser();


        }
        else{

            ageView.setVisibility(View.INVISIBLE);
            raceView.setVisibility(View.INVISIBLE);

            acceptDoggoButton.setVisibility(View.INVISIBLE);
            rejectDoggoButton.setVisibility(View.INVISIBLE);
            profilePicture.setVisibility(View.INVISIBLE);


            nameView.setText("No more doggo to match");
        }
    }
    protected void setAcceptDoggoButton()
    {
        stringMatchedList.add(matchedUserList.get(currentProfileIndex).getProfileId());

        nextDoggo();
    }
    protected void setRejectDoggoButton(){
        nextDoggo();

    }
}
