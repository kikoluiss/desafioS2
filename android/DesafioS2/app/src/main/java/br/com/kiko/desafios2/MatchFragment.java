package br.com.kiko.desafios2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.kiko.desafios2.Adapters.FlipperAdapter;
import br.com.kiko.desafios2.Entities.Music;
import br.com.kiko.desafios2.Helpers.MusicDatabaseHelper;
import br.com.kiko.desafios2.Utils.HttpGetRequest;

public class MatchFragment extends Fragment {
    public static MatchFragment newInstance() {
        MatchFragment fragment = new MatchFragment();
        return fragment;
    }

    private AdapterViewFlipper adapterViewFlipper;

    private int currentPosition;

    private LinkedHashMap<String, String> conditions;

    private String baseUrl = "https://itunes.apple.com/search";

    private HttpGetRequest getRequest;

    private JSONArray musicList;

    private SearchView matchSearch;

    private LinearLayout layoutButtons;

    private ImageButton btnDislike;
    private ImageButton btnLike;

    private FlipperAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        adapterViewFlipper = (AdapterViewFlipper) view.findViewById(R.id.adapterViewFlipper);

        layoutButtons = (LinearLayout) view.findViewById(R.id.layoutButtons);

        btnDislike = (ImageButton) view.findViewById(R.id.btnDislike);
        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = adapterViewFlipper.getDisplayedChild();
                FlipperAdapter tmpAdapter = (FlipperAdapter) adapterViewFlipper.getAdapter();

                tmpAdapter.removeItem(currentPosition);

                if (tmpAdapter.getCount() > 0) {
                    layoutButtons.setVisibility(View.VISIBLE);
                }
                else {
                    layoutButtons.setVisibility(View.GONE);
                }
            }
        });

        btnLike = (ImageButton) view.findViewById(R.id.btnLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = adapterViewFlipper.getDisplayedChild();
                FlipperAdapter tmpAdapter = (FlipperAdapter) adapterViewFlipper.getAdapter();

                JSONObject jsonMusic = (JSONObject) tmpAdapter.getItem(currentPosition);
                Music newMusic = new Music();

                try {
                    newMusic.artistId = jsonMusic.getInt("artistId");
                    newMusic.artistName = jsonMusic.getString("artistName");
                    newMusic.artistViewUrl = jsonMusic.getString("artistViewUrl");
                    newMusic.artworkUrl100 = jsonMusic.getString("artworkUrl100");
                    newMusic.artworkUrl30 = jsonMusic.getString("artworkUrl30");
                    newMusic.artworkUrl60 = jsonMusic.getString("artworkUrl60");
                    newMusic.collectionCensoredName = jsonMusic.getString("collectionCensoredName");
                    newMusic.collectionExplicitness = jsonMusic.getString("collectionExplicitness");
                    newMusic.collectionId = jsonMusic.getInt("collectionId");
                    newMusic.collectionName = jsonMusic.getString("collectionName");
                    newMusic.collectionPrice = jsonMusic.getString("collectionPrice");
                    newMusic.collectionViewUrl = jsonMusic.getString("collectionViewUrl");
                    newMusic.country = jsonMusic.getString("country");
                    newMusic.currency = jsonMusic.getString("currency");
                    newMusic.discCount = jsonMusic.getInt("discCount");
                    newMusic.discNumber = jsonMusic.getInt("discNumber");
                    newMusic.isStreamable = jsonMusic.getBoolean("isStreamable") ? 1 : 0;
                    newMusic.kind = jsonMusic.getString("kind");
                    newMusic.previewUrl = jsonMusic.getString("previewUrl");
                    newMusic.primaryGenreName = jsonMusic.getString("primaryGenreName");
                    newMusic.releaseDate = jsonMusic.getString("releaseDate");
                    newMusic.trackCensoredName = jsonMusic.getString("trackCensoredName");
                    newMusic.trackCount = jsonMusic.getInt("trackCount");
                    newMusic.trackExplicitness = jsonMusic.getString("trackExplicitness");
                    newMusic.trackId = jsonMusic.getInt("trackId");
                    newMusic.trackName = jsonMusic.getString("trackName");
                    newMusic.trackNumber = jsonMusic.getInt("trackNumber");
                    newMusic.trackPrice = jsonMusic.getString("trackPrice");
                    newMusic.trackTimeMillis = jsonMusic.getInt("trackTimeMillis");
                    newMusic.trackViewUrl = jsonMusic.getString("trackViewUrl");
                    newMusic.wrapperType = jsonMusic.getString("wrapperType");

                    MusicDatabaseHelper databaseHelper = MusicDatabaseHelper.getInstance(getActivity());

                    databaseHelper.addMusic(newMusic);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                tmpAdapter.removeItem(currentPosition);

                if (tmpAdapter.getCount() > 0) {
                    layoutButtons.setVisibility(View.VISIBLE);
                }
                else {
                    layoutButtons.setVisibility(View.GONE);
                }
            }
        });


        matchSearch = (SearchView) view.findViewById(R.id.matchSearch); // inititate a search view

        matchSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                conditions = new LinkedHashMap<String, String>();
                conditions.put("term", query);
                conditions.put("country", "BR");
                conditions.put("media", "music");

                StringBuilder strBuilder = new StringBuilder(baseUrl);
                for (String key : conditions.keySet()) {
                    try {
                        if (strBuilder.indexOf("?") > -1) {
                            strBuilder.append("&");
                        }
                        else {
                            strBuilder.append("?");
                        }
                        strBuilder.append(key);
                        strBuilder.append("=");
                        strBuilder.append(URLEncoder.encode(conditions.get(key), "UTF-8").toString());
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                getRequest = new HttpGetRequest();
                try {
                    String url = strBuilder.toString();
                    String result = getRequest.execute(url).get();
                    JSONObject jsonObject = new JSONObject(result);
                    musicList = jsonObject.getJSONArray("results");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                FlipperAdapter tmpAdapter = (FlipperAdapter) adapterViewFlipper.getAdapter();
                tmpAdapter.setData(musicList);

                adapterViewFlipper.setDisplayedChild(0);

                if (musicList.length() > 0) {
                    layoutButtons.setVisibility(View.VISIBLE);
                }
                else {
                    layoutButtons.setVisibility(View.GONE);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        musicList = new JSONArray();
        adapter = new FlipperAdapter(getContext(), musicList);
        adapterViewFlipper.setAdapter(adapter);

        final GestureDetector gesture = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                Log.i("Kiko", "onFling has been called!");
                final int SWIPE_MIN_DISTANCE = 120;
                final int SWIPE_MAX_OFF_PATH = 250;
                final int SWIPE_THRESHOLD_VELOCITY = 200;
                try {
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        adapterViewFlipper.setInAnimation(getActivity(), R.animator.right_in);
                        adapterViewFlipper.setOutAnimation(getActivity(), R.animator.right_out);
                        adapterViewFlipper.showNext();
                        Log.i("Kiko", "Right to Left");
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Log.i("Kiko", "Left to Right");
                        adapterViewFlipper.setInAnimation(getActivity(), R.animator.left_in);
                        adapterViewFlipper.setOutAnimation(getActivity(), R.animator.left_out);
                        adapterViewFlipper.showPrevious();
                    }
                } catch (Exception e) {
                    // nothing
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        return view;
    }

}