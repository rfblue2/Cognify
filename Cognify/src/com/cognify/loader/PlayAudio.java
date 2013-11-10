package com.cognify.loader;

import com.cognify.main.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlayAudio extends Service{

	MediaPlayer media;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		media = MediaPlayer.create(this, R.raw.excite);
		
	}
	
	public int onStartCommand(Intent intent, int flags, int startId){
		media.start();
		return 1;
	}
	
	public void onStop(){
		media.stop();
		media.release();
	}
	
	public void onPause(){
		media.stop();
		media.release();
	}
	
	public void onDestroy(){
		media.stop();
		media.release();
	}
	
	

}
