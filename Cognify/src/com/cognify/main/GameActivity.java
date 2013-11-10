package com.cognify.main;

import com.cognify.geometry.Shape;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameActivity extends Activity implements OnTouchListener {

	MySurfaceView sv;
	Shape test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.game_activity);
		//sv = (SurfaceView) findViewById(R.id.svGame);
		sv = new MySurfaceView(this);
		sv.setOnTouchListener(this);
		test = new Shape(Shape.SHAPE.SQUARE, 10, 10, Shape.COLOR.BLUE, false, this);
		setContentView(sv);
	}

	public class MySurfaceView extends SurfaceView implements Runnable	{

		Thread t = null;
		SurfaceHolder holder;
		boolean valid = false;
		
		public MySurfaceView(Context context) {
			super(context);
			holder = getHolder();
			
		}

		@Override
		public void run() {
			while(valid)	{
				//draw
				if(!holder.getSurface().isValid())	{
					continue;
				}
				
				Canvas c = holder.lockCanvas();
				c.drawARGB(255, 150, 150, 10);
				c.drawBitmap(test.getBmp(), test.getPosX(), test.getPosY(), null);
				holder.unlockCanvasAndPost(c);
			}
		}
		
		public void pause()	{
			valid = false;
			while(true)	{
				try	{
					t.join();
				}catch(InterruptedException e)	{
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}
		
		public void resume()	{
			valid = true;
			t = new Thread(this);
			t.start();
		}
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		sv.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		sv.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		
		return false;
	}
}
