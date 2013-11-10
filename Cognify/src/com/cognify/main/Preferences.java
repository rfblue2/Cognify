package com.cognify.main;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class Preferences extends Activity{
	MediaPlayer player = null;
	ToggleButton musicToggle;
	RadioGroup musicGroup;
	int currentMusic = R.raw.passion;
	boolean playing = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		
		musicToggle = (ToggleButton)findViewById(R.id.musicToggle);
		musicToggle.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				if(player == null)
				{
					player = MediaPlayer.create(Preferences.this, currentMusic);
					player.start();
					playing = true;
				}
				else
				{
					player.stop();
					player = null;
					playing = false;
				}
					 
			}
		});
		
		musicGroup = (RadioGroup)findViewById(R.id.musicGroup);
		musicGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			
			public void onCheckedChanged(RadioGroup group, int checkId){
				if(checkId == R.id.exciteRadio)
				{
					currentMusic = R.raw.excite;
					
					if(player != null)
						player.stop();
					if(playing)
					{
						player = MediaPlayer.create(Preferences.this, currentMusic);
						player.start();
					}
				}
				else
				{
					currentMusic = R.raw.passion;
					if(player != null)
						player.stop();
					if(playing)
					{
						player = MediaPlayer.create(Preferences.this, currentMusic);
						player.start();
					}
				}
			}
			
		});
		
	}

}
