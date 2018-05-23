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

public class Inscription extends BaseActivity {


    private EditText mNameView;
    private EditText mRaceView;
    private EditText mAgeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
                updateInformation();
            }
        });


    }
    private void updateInformation(){

    }
}
