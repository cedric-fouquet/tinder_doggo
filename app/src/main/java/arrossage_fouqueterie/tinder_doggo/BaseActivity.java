package arrossage_fouqueterie.tinder_doggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by f16007622 on 13/04/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        super.onStart();
    }
    protected void signOut(){
        mAuth.signOut();
        updateUI(null);
    }
    protected   void updateUI(FirebaseUser user){
        if(user == null){
            finish();
            Intent homepage = new Intent(this, LoginActivity.class);
            startActivity(homepage);
        }

    }

}
