package arrossage_fouqueterie.tinder_doggo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by f16007622 on 24/05/18.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String race;
    public int age;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String race, int Age) {
        this.username = username;
        this.email = email;
        this.race = race;
        this.age = Age;

    }

}