package arrossage_fouqueterie.tinder_doggo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Inscription extends BaseActivity {


    private EditText mNameView;
    private EditText mRaceView;
    private EditText mAgeView;
    private ImageButton imageButton;

    private Bitmap profilImage;

    private StorageReference imageRef;
    private StorageReference pathImageRef;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        mNameView = findViewById(R.id.editTextName);

        mRaceView = findViewById(R.id.editTextRace);

        mAgeView = findViewById(R.id.editTextAge);


        imageRef = storageRef.child(mAuth.getUid()+".jpg");
        pathImageRef = storageRef.child("images/" + mAuth.getUid()+".jpg");


        Button sendInfo = findViewById(R.id.validateInfo);
        imageButton = findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
            }
        });
        sendInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(profilImage == null){
                    profilImage = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();

                }


                writeNewUser(mAuth.getUid(),mNameView.getText().toString(),mRaceView.getText().toString()
                        ,Integer.parseInt(mAgeView.getText().toString()));
            }
        });


    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                profilImage = BitmapFactory.decodeStream(imageStream);
                Bitmap imageRedim = Bitmap.createScaledBitmap(profilImage,imageButton.getWidth(),imageButton.getHeight(),false);

                imageButton.setImageBitmap(imageRedim);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Inscription.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(Inscription.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void writeNewUser(String userId, String username, String race, int age) {


        DoggoProfile user = new DoggoProfile(mAuth.getUid(),username,race,age);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profilImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Failure gestion
            }
        })
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        mDatabase.getReference().child("user").child(userId).setValue(user);
        Intent page =  new Intent(Inscription.this,MainMenu.class);
        startActivity(page);
    }
}
