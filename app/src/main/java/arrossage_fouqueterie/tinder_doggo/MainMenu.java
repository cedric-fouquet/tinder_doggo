package arrossage_fouqueterie.tinder_doggo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends BaseActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final String TAG = "MainMenu";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        DatabaseReference myRef = database.getReference().child(mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.v(TAG,dataSnapshot.toString());
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


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


    }
}
