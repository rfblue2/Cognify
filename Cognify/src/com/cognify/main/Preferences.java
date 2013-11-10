package com.cognify.main;

import com.cognify.loader.PlayAudio;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class Preferences extends Activity{
	static MediaPlayer player = null;
	ToggleButton musicToggle;
	RadioGroup musicGroup;
	static int currentMusic = R.raw.passion;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		
		//player = MediaPlayer.create(Preferences.this, currentMusic);
		
		musicToggle = (ToggleButton)findViewById(R.id.musicToggle);
		if(player != null)
		{

			if(player.isPlaying())
				musicToggle.setChecked(true);
		}
		musicToggle.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				if(player == null)
				{
					player = MediaPlayer.create(Preferences.this, currentMusic);
					player.start();
				}
				else
				{
					if(player.isPlaying())
						player.stop();
					player = null;
				}
					 
			}
		});
		
		musicGroup = (RadioGroup)findViewById(R.id.musicGroup);
		RadioButton peaceRadio = (RadioButton) findViewById(R.id.peaceRadio);
		RadioButton exciteRadio = (RadioButton) findViewById(R.id.exciteRadio);
		
		if(currentMusic == R.raw.passion)
			peaceRadio.setChecked(true);
		else
			exciteRadio.setChecked(true);
		musicGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			
			public void onCheckedChanged(RadioGroup group, int checkId){
				if(checkId == R.id.exciteRadio)
				{
					currentMusic = R.raw.excite;
					Intent mediaIntent = new Intent(Preferences.this, PlayAudio.class);
					startService(mediaIntent);
				}
				else
				{
					currentMusic = R.raw.passion;
					Intent mediaIntent = new Intent(Preferences.this, PlayAudio.class);
					stopService(mediaIntent);
				}
			}
			
		});
		
	}

}
