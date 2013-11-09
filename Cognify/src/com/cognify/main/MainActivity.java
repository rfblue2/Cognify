package com.cognify.main;

import java.io.IOException;

import com.cognify.loader.LevelLoader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

	Button play;
	Button prefs;
	Button about;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		Log.v("test", "help");
		setContentView(R.layout.activity_main);
		
		play = (Button) findViewById(R.id.bPlay);
		prefs = (Button) findViewById(R.id.bPrefs);
		about = (Button) findViewById(R.id.bAbout);
		
		play.setOnClickListener(this);
		prefs.setOnClickListener(this);
		about.setOnClickListener(this);
		LevelLoader lvl = new LevelLoader(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())	{
		case R.id.bPlay:
			
			break;
		case R.id.bPrefs:
			
			break;
		case R.id.bAbout:
			Intent i = new Intent("com.cognify.main.ABOUT");
			startActivity(i);
			break;
		}
		
	}

}
