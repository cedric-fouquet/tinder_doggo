package arrossage_fouqueterie.tinder_doggo;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

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

public class MatchedDoggosList extends ListActivity {

    private ListView mListView;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private DoggoProfile doggoUser = new DoggoProfile(mAuth.getUid().toString());
    private List<String> matchedIdList = new ArrayList<>();
    private List<DoggoProfile> matchedProfileList = new ArrayList<>();
    private final String TAG = "MatchedDoggoList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doggos_matched_list);
        afficherProfils();

        mListView =  findViewById(R.id.listView);


        final MatchedProfileAdapter adapter = new MatchedProfileAdapter(MatchedDoggosList.this,
                android.R.layout.simple_list_item_1,matchedProfileList );
        mListView.setAdapter(adapter);
    }
    private void afficherProfils(){
        DatabaseReference myRef = database.getReference().child("user").child(mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    doggoUser.loadProfileInfo(dataSnapshot);
                    matchedIdList = doggoUser.getMatchedIdList();
                    for(String stringid : matchedIdList)
                    {

                        final DoggoProfile currentProfile = new DoggoProfile(stringid);
                        DatabaseReference myRef = database.getReference().child("user").child(stringid);
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                currentProfile.loadProfileInfo(dataSnapshot);
                                StorageReference image = storageRef.child(currentProfile+".jpg");

                                final long ONE_MEGABYTE = 1024 * 1024 * 100;

                                image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        currentProfile.loadImage(bytes);
                                        matchedProfileList.add(currentProfile);
                                        if(matchedProfileList.size()==matchedIdList.size())
                                        {
                                            MatchedProfileAdapter adapter = new MatchedProfileAdapter(MatchedDoggosList.this,0, matchedProfileList);
                                            mListView.setAdapter(adapter);
                                        }

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }






                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }
}
