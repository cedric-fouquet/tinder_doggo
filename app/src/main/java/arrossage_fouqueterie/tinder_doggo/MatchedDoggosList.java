package arrossage_fouqueterie.tinder_doggo;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MatchedDoggosList extends ListActivity {

    private ListView mListView;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doggos_matched_list);
        mListView =  findViewById(R.id.listView);


//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MatchedDoggosList.this,
//                android.R.layout.simple_list_item_1, );
//        mListView.setAdapter(adapter);
    }
}
