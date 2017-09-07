package br.com.kiko.desafios2.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.kiko.desafios2.Entities.Music;

import static android.content.ContentValues.TAG;

/**
 * Created by kiko on 06/09/17.
 */

public class MusicDatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "musicDatabase";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    private static final String TABLE_MUSICS = "musics";

    // Music Table Columns
    private static final String KEY_MUSIC_ID = "id";
    private static final String KEY_MUSIC_ARTIST_ID = "artistId";
    private static final String KEY_MUSIC_ARTIST_NAME = "artistName";
    private static final String KEY_MUSIC_ARTIST_VIEW_URL = "artistViewUrl";
    private static final String KEY_MUSIC_ARTWORK_URL_100 = "artworkUrl100";
    private static final String KEY_MUSIC_ARTWORK_URL_30 = "artworkUrl30";
    private static final String KEY_MUSIC_ARTWORK_URL_60 = "artworkUrl60";
    private static final String KEY_MUSIC_COLLECTION_CENSORED_NAME = "collectionCensoredName";
    private static final String KEY_MUSIC_COLLECTIONEXPLICITNESS = "collectionExplicitness";
    private static final String KEY_MUSIC_COLLECTION_ID = "collectionId";
    private static final String KEY_MUSIC_COLLECTION_NAME = "collectionName";
    private static final String KEY_MUSIC_COLLECTION_PRICE = "collectionPrice";
    private static final String KEY_MUSIC_COLLECTION_VIEW_URL = "collectionViewUrl";
    private static final String KEY_MUSIC_COUNTRY = "country";
    private static final String KEY_MUSIC_CURRENCY = "currency";
    private static final String KEY_MUSIC_DISC_COUNT = "discCount";
    private static final String KEY_MUSIC_DISC_NUMBER = "discNumber";
    private static final String KEY_MUSIC_IS_STREAMABLE = "isStreamable";
    private static final String KEY_MUSIC_KIND = "kind";
    private static final String KEY_MUSIC_PREVIEW_URL = "previewUrl";
    private static final String KEY_MUSIC_PRIMARY_GENRE_NAME = "primaryGenreName";
    private static final String KEY_MUSIC_RELEASE_DATE = "releaseDate";
    private static final String KEY_MUSIC_TRACK_CENSORED_NAME = "trackCensoredName";
    private static final String KEY_MUSIC_TRACK_COUNT = "trackCount";
    private static final String KEY_MUSIC_TRACK_EXPLICITNESS = "trackExplicitness";
    private static final String KEY_MUSIC_TRACK_ID = "trackId";
    private static final String KEY_MUSIC_TRACK_NAME = "trackName";
    private static final String KEY_MUSIC_TRACK_NUMBER = "trackNumber";
    private static final String KEY_MUSIC_TRACK_PRICE = "trackPrice";
    private static final String KEY_MUSIC_TRACK_TIME_MILLIS = "trackTimeMillis";
    private static final String KEY_MUSIC_TRACK_VIEW_URL = "trackViewUrl";
    private static final String KEY_MUSIC_WRAPPER_TYPE = "wrapperType";

    private static MusicDatabaseHelper sInstance;

    public static synchronized MusicDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MusicDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private MusicDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MUSICS_TABLE = "CREATE TABLE " + TABLE_MUSICS +
                "(" +
                KEY_MUSIC_ID + " INTEGER PRIMARY KEY," +
                KEY_MUSIC_ARTIST_ID + " INTEGER," +
                KEY_MUSIC_ARTIST_NAME + " TEXT," +
                KEY_MUSIC_ARTIST_VIEW_URL + " TEXT," +
                KEY_MUSIC_ARTWORK_URL_100 + " TEXT," +
                KEY_MUSIC_ARTWORK_URL_30 + " TEXT," +
                KEY_MUSIC_ARTWORK_URL_60 + " TEXT," +
                KEY_MUSIC_COLLECTION_CENSORED_NAME + " TEXT," +
                KEY_MUSIC_COLLECTIONEXPLICITNESS + " TEXT," +
                KEY_MUSIC_COLLECTION_ID + " INTEGER," +
                KEY_MUSIC_COLLECTION_NAME + " TEXT," +
                KEY_MUSIC_COLLECTION_PRICE + " TEXT," +
                KEY_MUSIC_COLLECTION_VIEW_URL + " TEXT," +
                KEY_MUSIC_COUNTRY + " TEXT," +
                KEY_MUSIC_CURRENCY + " TEXT," +
                KEY_MUSIC_DISC_COUNT + " INTEGER," +
                KEY_MUSIC_DISC_NUMBER + " INTEGER," +
                KEY_MUSIC_IS_STREAMABLE + " INTEGER," +
                KEY_MUSIC_KIND + " TEXT," +
                KEY_MUSIC_PREVIEW_URL + " TEXT," +
                KEY_MUSIC_PRIMARY_GENRE_NAME + " TEXT," +
                KEY_MUSIC_RELEASE_DATE + " TEXT," +
                KEY_MUSIC_TRACK_CENSORED_NAME + " TEXT," +
                KEY_MUSIC_TRACK_COUNT + " INTEGER," +
                KEY_MUSIC_TRACK_EXPLICITNESS + " TEXT," +
                KEY_MUSIC_TRACK_ID + " INTEGER," +
                KEY_MUSIC_TRACK_NAME + " TEXT," +
                KEY_MUSIC_TRACK_NUMBER + " INTEGER," +
                KEY_MUSIC_TRACK_PRICE + " TEXT," +
                KEY_MUSIC_TRACK_TIME_MILLIS + " INTEGER," +
                KEY_MUSIC_TRACK_VIEW_URL + " TEXT," +
                KEY_MUSIC_WRAPPER_TYPE + " TEXT" +
                ")";

        db.execSQL(CREATE_MUSICS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSICS);
            onCreate(db);
        }
    }

    public void addMusic(Music music) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_MUSIC_ARTIST_ID, music.artistId);
            values.put(KEY_MUSIC_ARTIST_NAME, music.artistName);
            values.put(KEY_MUSIC_ARTIST_VIEW_URL, music.artistViewUrl);
            values.put(KEY_MUSIC_ARTWORK_URL_100, music.artworkUrl100);
            values.put(KEY_MUSIC_ARTWORK_URL_30, music.artworkUrl30);
            values.put(KEY_MUSIC_ARTWORK_URL_60, music.artworkUrl60);
            values.put(KEY_MUSIC_COLLECTION_CENSORED_NAME, music.collectionCensoredName);
            values.put(KEY_MUSIC_COLLECTIONEXPLICITNESS, music.collectionExplicitness);
            values.put(KEY_MUSIC_COLLECTION_ID, music.collectionId);
            values.put(KEY_MUSIC_COLLECTION_NAME, music.collectionName);
            values.put(KEY_MUSIC_COLLECTION_PRICE, music.collectionPrice);
            values.put(KEY_MUSIC_COLLECTION_VIEW_URL, music.collectionViewUrl);
            values.put(KEY_MUSIC_COUNTRY, music.country);
            values.put(KEY_MUSIC_CURRENCY, music.currency);
            values.put(KEY_MUSIC_DISC_COUNT, music.discCount);
            values.put(KEY_MUSIC_DISC_NUMBER, music.discNumber);
            values.put(KEY_MUSIC_IS_STREAMABLE, music.isStreamable);
            values.put(KEY_MUSIC_KIND, music.kind);
            values.put(KEY_MUSIC_PREVIEW_URL, music.previewUrl);
            values.put(KEY_MUSIC_PRIMARY_GENRE_NAME, music.primaryGenreName);
            values.put(KEY_MUSIC_RELEASE_DATE, music.releaseDate);
            values.put(KEY_MUSIC_TRACK_CENSORED_NAME, music.trackCensoredName);
            values.put(KEY_MUSIC_TRACK_COUNT, music.trackCount);
            values.put(KEY_MUSIC_TRACK_EXPLICITNESS, music.trackExplicitness);
            values.put(KEY_MUSIC_TRACK_ID, music.trackExplicitness);
            values.put(KEY_MUSIC_TRACK_NAME, music.trackName);
            values.put(KEY_MUSIC_TRACK_NUMBER, music.trackNumber);
            values.put(KEY_MUSIC_TRACK_PRICE, music.trackPrice);
            values.put(KEY_MUSIC_TRACK_TIME_MILLIS, music.trackTimeMillis);
            values.put(KEY_MUSIC_TRACK_VIEW_URL, music.trackViewUrl);
            values.put(KEY_MUSIC_WRAPPER_TYPE, music.wrapperType);

            db.insertOrThrow(TABLE_MUSICS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<Music> getAllMusics() {
        List<Music> musics = new ArrayList<>();

        String MUSICS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_MUSICS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(MUSICS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Music newMusic = new Music();
                    newMusic.artistId = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_ARTIST_ID));
                    newMusic.artistName = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_ARTIST_NAME));
                    newMusic.artistViewUrl = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_ARTIST_VIEW_URL));
                    newMusic.artworkUrl100 = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_ARTWORK_URL_100));
                    newMusic.artworkUrl30 = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_ARTWORK_URL_30));
                    newMusic.artworkUrl60 = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_ARTWORK_URL_60));
                    newMusic.collectionCensoredName = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_COLLECTION_CENSORED_NAME));
                    newMusic.collectionExplicitness = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_COLLECTIONEXPLICITNESS));
                    newMusic.collectionId = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_COLLECTION_ID));
                    newMusic.collectionName = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_COLLECTION_NAME));
                    newMusic.collectionPrice = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_COLLECTION_PRICE));
                    newMusic.collectionViewUrl = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_COLLECTION_VIEW_URL));
                    newMusic.country = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_COUNTRY));
                    newMusic.currency = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_CURRENCY));
                    newMusic.discCount = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_DISC_COUNT));
                    newMusic.discNumber = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_DISC_NUMBER));
                    newMusic.isStreamable = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_IS_STREAMABLE));
                    newMusic.kind = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_KIND));
                    newMusic.previewUrl = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_PREVIEW_URL));
                    newMusic.primaryGenreName = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_PRIMARY_GENRE_NAME));
                    newMusic.releaseDate = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_RELEASE_DATE));
                    newMusic.trackCensoredName = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_TRACK_CENSORED_NAME));
                    newMusic.trackCount = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_TRACK_COUNT));
                    newMusic.trackExplicitness = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_TRACK_EXPLICITNESS));
                    newMusic.trackId = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_TRACK_ID));
                    newMusic.trackName = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_TRACK_NAME));
                    newMusic.trackNumber = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_TRACK_NUMBER));
                    newMusic.trackPrice = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_TRACK_PRICE));
                    newMusic.trackTimeMillis = cursor.getInt(cursor.getColumnIndex(KEY_MUSIC_TRACK_TIME_MILLIS));
                    newMusic.trackViewUrl = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_TRACK_VIEW_URL));
                    newMusic.wrapperType = cursor.getString(cursor.getColumnIndex(KEY_MUSIC_WRAPPER_TYPE));

                    musics.add(newMusic);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return musics;
    }

    public void deleteAllMusics() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_MUSICS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}