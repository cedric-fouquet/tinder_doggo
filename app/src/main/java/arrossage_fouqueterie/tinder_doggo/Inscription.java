package arrossage_fouqueterie.tinder_doggo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inscription extends BaseActivity {


    private EditText mNameView;
    private EditText mRaceView;
    private EditText mAgeView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mNameView = findViewById(R.id.editTextName);

        mRaceView = findViewById(R.id.editTextRace);

        mAgeView = findViewById(R.id.editTextAge);


//        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(pickPhoto , 1)

        Button sendInfo = findViewById(R.id.validateInfo);
        sendInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                writeNewUser(mAuth.getUid(),mNameView.getText().toString(),mAuth.get);
            }
        });


    }
    private void updateInformation(){
        DatabaseReference myRef = database.getReference("userId");

        myRef.setValue(mAuth.getCurrentUser().getUid());
    }
    private void writeNewUser(String userId, String username, String email, String race, int age) {
        User user = new User(username,email,race, age);
        database.getReference().child(userId).setValue(user);
    }
}
