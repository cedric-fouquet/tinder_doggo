package arrossage_fouqueterie.tinder_doggo;

import android.graphics.Bitmap;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by f16007622 on 24/05/18.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String race;
    public int age;
    public Bitmap profilImage;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String race, int Age) {
        this.username = username;
        this.race = race;
        this.age = Age;

    }

}