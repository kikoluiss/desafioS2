package br.com.kiko.desafios2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.kiko.desafios2.Entities.Music;
import br.com.kiko.desafios2.LikedFragment.OnListFragmentInteractionListener;
import br.com.kiko.desafios2.dummy.DummyContent.DummyItem;

import java.util.List;

public class LikedRecyclerViewAdapter extends RecyclerView.Adapter<LikedRecyclerViewAdapter.ViewHolder> {

    private final List<Music> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public LikedRecyclerViewAdapter(List<Music> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_liked, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        String url = mValues.get(position).artworkUrl100;
        Glide
            .with(context)
            .load(url)
            .into(holder.rImgAlbumArtwork);

        holder.rLblSongTitle.setText(mValues.get(position).trackName);
        holder.rLblArtist.setText(mValues.get(position).artistName);
        holder.rLblAlbumTitle.setText(mValues.get(position).collectionName);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView rImgAlbumArtwork;
        public final TextView rLblSongTitle;
        public final TextView rLblArtist;
        public final TextView rLblAlbumTitle;
        public Music mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            rImgAlbumArtwork = (ImageView) view.findViewById(R.id.rImgAlbumArtwork);
            rLblSongTitle = (TextView) view.findViewById(R.id.rLblSongTitle);
            rLblArtist = (TextView) view.findViewById(R.id.rLblArtist);
            rLblAlbumTitle = (TextView) view.findViewById(R.id.rLblAlbumTitle);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + rLblSongTitle.getText() + "'";
        }
    }
}
