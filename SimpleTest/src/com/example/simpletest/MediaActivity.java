package com.example.simpletest;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MediaActivity extends ListActivity {

	List<MediaObject> mFilesList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
		mFilesList = new ArrayList<MediaObject>();
		
		// Retrieve all audio files from device
		getAllSongsFromSDCARD();
		
		// Fill array with data for ArrayAdapter
		MediaObject[] mediaObjArr = new MediaObject[mFilesList.size()];
		for(int i=0; i<mFilesList.size(); i++){
			mediaObjArr[i] = mFilesList.get(i);
		}
		
		// Create and set adapter
	    ArrayAdapter<MediaObject> adapter = new ArrayAdapter<MediaObject>(this,
	    																	android.R.layout.simple_list_item_1, 
	    																	mediaObjArr);
		setListAdapter(adapter);
	    
		// Create Item Click Listener
	    ListView listView = getListView();
	    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	public void onItemClick (AdapterView<?> parent, View itemClicked, int position, long id){
	    		Log.v("SIMPLE_TEST_APP", "onItemClick - position: " + position + " id: " + id);
	    		
	    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
	    		MediaObject obj = mFilesList.get(position);
	            Uri data = Uri.parse("file://" + obj.getmPath());
	            intent.setDataAndType(data,"audio/*");

	            try {
	                startActivity(intent);
	            } catch (ActivityNotFoundException e) {
	                e.printStackTrace();
	            	Toast.makeText(MediaActivity.this, "Failed to play: " + obj.getmName() , Toast.LENGTH_LONG).show();
	            } 
	    	}
	    });	
	}

	public void getAllSongsFromSDCARD() 
	{
	    String[] STAR = { "*" };        
	    Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	    String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

	    Cursor cursor = managedQuery(allsongsuri, STAR, selection, null, null);

	    //cursor.getCount();
	    
	    if (cursor != null) {
	        if (cursor.moveToFirst()) {
	            do {
	            	Log.v("SIMPLE_TEST_APP", "~~~~~~~~~~~~");
	                String song_name = cursor
	                        .getString(cursor
	                                .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
	                
	                
	                
	                int song_id = cursor.getInt(cursor
	                        .getColumnIndex(MediaStore.Audio.Media._ID));

	                Log.v("SIMPLE_TEST_APP", "Song id: " + song_id);
	                Log.v("SIMPLE_TEST_APP", "Song name: " + song_name);
	                
	                String fullpath = cursor.getString(cursor
	                        .getColumnIndex(MediaStore.Audio.Media.DATA));

	                Log.v("SIMPLE_TEST_APP", "Song fullpath: " + fullpath);

	                String album_name = cursor.getString(cursor
	                        .getColumnIndex(MediaStore.Audio.Media.ALBUM));
	                
	                Log.v("SIMPLE_TEST_APP", "Song album name: " + album_name);
	                
	                int album_id = cursor.getInt(cursor
	                        .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

	                String artist_name = cursor.getString(cursor
	                        .getColumnIndex(MediaStore.Audio.Media.ARTIST));
	                
	                Log.v("SIMPLE_TEST_APP", "Song artist name: " + artist_name);
	                
	                int artist_id = cursor.getInt(cursor
	                        .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));

	                // Add file to the list
	                MediaObject obj = new MediaObject(song_name, fullpath);
	                mFilesList.add(obj);


	            } while (cursor.moveToNext());

	        }
	        cursor.close();
	    }
	}
}
