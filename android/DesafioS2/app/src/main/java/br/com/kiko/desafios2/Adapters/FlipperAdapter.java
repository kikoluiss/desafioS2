package br.com.kiko.desafios2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.kiko.desafios2.R;

/**
 * Created by kiko on 05/09/17.
 */

public class FlipperAdapter extends BaseAdapter {

    private Context context;
    private JSONArray musicList;

    public FlipperAdapter(Context context, JSONArray musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return musicList.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void setData(JSONArray j) {
        musicList = j;
        notifyDataSetChanged();
    }

    public void removeItem(int i) {
        musicList.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        JSONObject musicItem = null;
        if (musicList.length() > 0) {
            if (convertView == null) {
                view = inflater.inflate(R.layout.fragment_music, null);
                final ImageView imgAlbumArtwork;
                final TextView lblSongTitle;
                final TextView lblArtist;
                final TextView lblAlbumTitle;

                try {
                    musicItem = musicList.getJSONObject(position);
                    imgAlbumArtwork = (ImageView) view.findViewById(R.id.imgAlbumArtwork);
                    String url = musicItem.getString("artworkUrl100");
                    Glide
                            .with(context)
                            .load(url)
                            .into(imgAlbumArtwork);

                    lblSongTitle = (TextView) view.findViewById(R.id.lblSongTitle);
                    lblSongTitle.setText(musicItem.getString("trackName"));

                    lblArtist = (TextView) view.findViewById(R.id.lblArtist);
                    lblArtist.setText(musicItem.getString("artistName"));

                    lblAlbumTitle = (TextView) view.findViewById(R.id.lblAlbumTitle);
                    lblAlbumTitle.setText(musicItem.getString("collectionName"));

                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
            else {
                view = convertView;
            }
        }
        else {
            view = inflater.inflate(R.layout.fragment_welcome, null);
        }
        return view;

    }
}
