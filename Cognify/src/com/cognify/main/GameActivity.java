package com.cognify.main;

import java.util.ArrayList;

import com.cognify.geometry.Shape;
import com.cognify.loader.LevelLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameActivity extends Activity implements OnTouchListener {

	MySurfaceView sv;
	ArrayList<Shape> shapes;
	int currentLevel;
	LevelLoader levelLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sv = new MySurfaceView(this);
		sv.setOnTouchListener(this);
		setContentView(sv);
		
		shapes = new ArrayList<Shape>();

		levelLoader = new LevelLoader(this);
		Bundle extras = getIntent().getExtras();
		currentLevel = extras.getInt("level");
		levelLoader.loadLevel(currentLevel);
	}
	
	public void drawShapes(Shape s)	{
		shapes.add(s);
		
	}
	
	public void nextLevel()	{
		shapes.clear();
		levelLoader.loadLevel(++currentLevel);
	}

	public void previousLevel()	{
		shapes.clear();
		levelLoader.loadLevel(--currentLevel);
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
				/*Paint holeColor = new Paint(Color.LTGRAY);
				LightingColorFilter holeFilter = new LightingColorFilter(Color.RED, 1);
				holeColor.setColorFilter(holeFilter);*/
				//draw
				if(!holder.getSurface().isValid())	{
					continue; 
				}
				
				Canvas c = holder.lockCanvas();
				c.drawARGB(255, 255, 255, 204);
				for(int n = 0; n < shapes.size(); n++)	{
					 
					c.drawBitmap(shapes.get(n).getBmp(), shapes.get(n).getPosX(), shapes.get(n).getPosY(), null);
				}
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
