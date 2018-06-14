package arrossage_fouqueterie.tinder_doggo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by f16007622 on 08/06/18.
 */

public class DoggoProfile {

    private String username;
    private int age;
    private String race;
    private Bitmap profilePicture;
    private String profileId;
    private ArrayList<String> matchedIdList = new ArrayList<>();

    private boolean availableprofile;
    private static final String TAG = "DoggoProfile";

    public DoggoProfile(String id){
        this.profileId = id;
        availableprofile =false;
    }
    public DoggoProfile(String id,String username, String race, int age)
    {
        availableprofile =true;
        this.age = age;
        this.race = race;

        this.username = username;
        this.profileId = id;
    }

    public void loadProfileInfo(DataSnapshot snapshot){

        availableprofile =true;
        age = snapshot.child("age").getValue(Integer.class);
        username = snapshot.child("username").getValue(String.class);
        race = snapshot.child("race").getValue(String.class);


    }
    public void loadImage(byte[] image){
        profilePicture = BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    public String getUsername() {
        return username;
    }

    public int getage() {
        return age;
    }

    public String getrace() {
        return race;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public String getProfileId() {
        return profileId;
    }



    public List<String> getMatchedIdList() {
        return matchedIdList;
    }

    public void setMatchedIdList(ArrayList<String> matchedIdList) {
        this.matchedIdList = matchedIdList;
    }
    private class UpdateProfile extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {

                return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
