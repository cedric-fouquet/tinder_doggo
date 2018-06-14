package arrossage_fouqueterie.tinder_doggo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by f16007622 on 14/06/18.
 */

public class MatchedProfileAdapter extends ArrayAdapter<DoggoProfile> {
    public MatchedProfileAdapter( Context context, @Nullable int resource,  List<DoggoProfile> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_profile,parent, false);
        }

        ProfileViewHolder viewHolder = (ProfileViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ProfileViewHolder();
            viewHolder.pseudo =  convertView.findViewById(R.id.pseudo);
            viewHolder.avatar =  convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        DoggoProfile profile = getItem(position);
        viewHolder.pseudo.setText(profile.getUsername());
        viewHolder.avatar.setImageBitmap(profile.getProfilePicture());

        return convertView;
    }
    private class ProfileViewHolder {
        public TextView pseudo;
        public ImageView avatar;

    }
}
