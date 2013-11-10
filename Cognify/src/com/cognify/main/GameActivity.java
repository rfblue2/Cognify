package com.cognify.main;

import java.util.ArrayList;

import com.cognify.geometry.Shape;
import com.cognify.geometry.TouchManager;
import com.cognify.geometry.Vector2D;
import com.cognify.loader.LevelLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
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
	private ArrayList<Shape> onlyHoles;
	int currentLevel;
	LevelLoader levelLoader;

	int activeShapeIndex = 0;
	private TouchManager touchManager = new TouchManager(2);

	private ArrayList<Vector2D> position;

	// Work with TouchManager
	private ArrayList<Vector2D> vca;
	private ArrayList<Vector2D> vcb;
	private ArrayList<Vector2D> vpa;
	private ArrayList<Vector2D> vpb;

	private ArrayList<Matrix> transform;
	private ArrayList<Integer> bitmapWidth;
	private ArrayList<Integer> bitmapHeight;
	private ArrayList<Rect> bitmapBounds;

	private ArrayList<Float> scale;
	private ArrayList<Float> angle;

	private ArrayList<Boolean> holeClear;

	public boolean isInitialized = false;
	private boolean doNotModify;
	private boolean checkDownPress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sv = new MySurfaceView(this);
		sv.setOnTouchListener(this);
		setContentView(sv);

		shapes = new ArrayList<Shape>();
		onlyHoles = new ArrayList<Shape>();
		position = new ArrayList<Vector2D>();
		vca = new ArrayList<Vector2D>();
		vcb = new ArrayList<Vector2D>();
		vpa = new ArrayList<Vector2D>();
		vpb = new ArrayList<Vector2D>();
		transform = new ArrayList<Matrix>();
		bitmapWidth = new ArrayList<Integer>();
		bitmapHeight = new ArrayList<Integer>();
		bitmapBounds = new ArrayList<Rect>();
		scale = new ArrayList<Float>();
		angle = new ArrayList<Float>();
		holeClear = new ArrayList<Boolean>();

		levelLoader = new LevelLoader(this);
		Bundle extras = getIntent().getExtras();
		currentLevel = extras.getInt("level");
		levelLoader.loadLevel(currentLevel);
	}

	public void drawShapes(Shape s) {
		shapes.add(s);
		position.add(new Vector2D(s.getPosX() + s.getBmp().getWidth() / 2, s
				.getPosY() + s.getBmp().getHeight() / 2));
		bitmapWidth.add(s.getBmp().getWidth());
		bitmapHeight.add(s.getBmp().getHeight());
		bitmapBounds.add(new Rect((int) s.getPosX(), (int) s.getPosY(), (int) s
				.getPosX() + s.getBmp().getWidth(), (int) s.getPosY()
				+ s.getBmp().getHeight()));
		transform.add(new Matrix());
		scale.add((float) 1);
		angle.add((float) 0);
		vca.add(null);
		vcb.add(null);
		vpa.add(null);
		vpb.add(null);
		if (s.isHole()) {
			onlyHoles.add(s);
			holeClear.add(false);
		}
	}

	private void refreshArrays() {
		shapes.clear();
		position.clear();
		vca.clear();
		vcb.clear();
		vpa.clear();
		vpb.clear();
		transform.clear();
		bitmapWidth.clear();
		bitmapHeight.clear();
		bitmapBounds.clear();
		scale.clear();
		angle.clear();
	}

	public void nextLevel() {
		try {
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		shapes.clear();
		refreshArrays();
		levelLoader.loadLevel(++currentLevel);
	}

	public void previousLevel() {
		shapes.clear();
		refreshArrays();
		levelLoader.loadLevel(--currentLevel);
	}

	private boolean checkCompletion() {
		for (int x = 0; x < holeClear.size(); x++) {
			if (!holeClear.get(x))
				return false;
		}
		return true;
	}

	public class MySurfaceView extends SurfaceView implements Runnable {

		Thread t = null;
		SurfaceHolder holder;
		boolean valid = false;

		public MySurfaceView(Context context) {
			super(context);
			holder = getHolder();

		}

		private float getDegreesFromRadians(float angle) {
			return (float) (angle * 180.0 / Math.PI);
		}

		@Override
		public void run() {
			while (valid) {
				/*
				 * Paint holeColor = new Paint(Color.LTGRAY);
				 * LightingColorFilter holeFilter = new
				 * LightingColorFilter(Color.RED, 1);
				 * holeColor.setColorFilter(holeFilter);
				 */
				// draw
				if (!holder.getSurface().isValid() || !isInitialized) {
					continue;
				}

				Canvas c = holder.lockCanvas();
				c.drawARGB(255, 255, 255, 204);
				for (int n = 0; n < shapes.size(); n++) {

					Paint paint = new Paint();

					transform.get(n).reset();
					transform.get(n).postTranslate(-bitmapWidth.get(n) / 2.0f,
							-bitmapHeight.get(n) / 2.0f);
					transform.get(n).postRotate(
							getDegreesFromRadians(angle.get(n)));
					transform.get(n).postScale(scale.get(n), scale.get(n));
					transform.get(n).postTranslate(position.get(n).getX(),
							position.get(n).getY());
					
					// c.drawBitmap(shapes.get(n).getBmp(),
					// shapes.get(n).getPosX(), shapes.get(n).getPosY(), null);
				}
				for (int x = 0; x < holeClear.size(); x++) { // onlyHoles array
					for ( int y = 0; y < shapes.size(); y++ ) { // shapes array
						if ( shapes.get(y).isHole() || (shapes.get(y).getShape() != onlyHoles.get(x).getShape()) )
								continue;
						else {							
							if ( shapes.get(y).getPosX() - onlyHoles.get(x).getPosX() <= 5 && 
									shapes.get(y).getPosX() - onlyHoles.get(x).getPosX() >= -5 && 
									shapes.get(y).getPosY() - onlyHoles.get(x).getPosY() <= 5 &&
									shapes.get(y).getPosY() - onlyHoles.get(x).getPosY() >= -5) {
									holeClear.set(x, true);
									transform.get(y).reset();
									transform.get(y).postTranslate(onlyHoles.get(x).getPosX(), onlyHoles.get(x).getPosY());
							}
						}
					}
				}
				for (int n = 0; n < shapes.size(); n++) {
					Paint paint = new Paint();
					c.drawBitmap(shapes.get(n).getBmp(), transform.get(n),
							paint);
					
					float[] temp = new float[9];
					transform.get(n).getValues(temp);

					shapes.get(n).setPosX(temp[2]);
					shapes.get(n).setPosY(temp[5]);
					bitmapBounds.get(n).set(
							(int) shapes.get(n).getPosX(),
							(int) shapes.get(n).getPosY(),
							(int) shapes.get(n).getPosX()
									+ shapes.get(n).getBmp().getWidth(),
							(int) shapes.get(n).getPosY()
									+ shapes.get(n).getBmp().getHeight());
				}
				if (checkCompletion()) {
					nextLevel();
				}
				holder.unlockCanvasAndPost(c);
			}
		}

		public void pause() {
			valid = false;
			while (true) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}

		public void resume() {
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
		int actionCode = me.getAction() & MotionEvent.ACTION_MASK;
		if (actionCode == MotionEvent.ACTION_POINTER_UP
				|| actionCode == MotionEvent.ACTION_UP) {
			checkDownPress = false;
			doNotModify = true;
		} else if (actionCode == MotionEvent.ACTION_MOVE) {
			checkDownPress = true;
			doNotModify = true;
		} else {
			if (bitmapBounds.get(activeShapeIndex).contains(
					(int) touchManager.getPoint(0).getX(),
					(int) touchManager.getPoint(0).getY())) {
				checkDownPress = true;
			}
			doNotModify = false;
		}

		for (int x = 0; x < shapes.size(); x++) {
			vca.set(x, null);
			vcb.set(x, null);
			vpa.set(x, null);
			vpb.set(x, null);
		}

		try {
			touchManager.update(me);
			for (int x = 0; x < shapes.size(); x++) {
				if (bitmapBounds.get(x).contains(
						(int) touchManager.getPoint(0).getX(),
						(int) touchManager.getPoint(0).getY())
						&& !doNotModify) {
					activeShapeIndex = x;
					break;
				}
			}

			if (checkDownPress) {
				if (touchManager.getPressCount() == 1
						&& !shapes.get(activeShapeIndex).isHole()) {
					vca.set(activeShapeIndex, touchManager.getPoint(0));
					vpa.set(activeShapeIndex, touchManager.getPreviousPoint(0));
					if (bitmapBounds.get(activeShapeIndex).contains(
							(int) touchManager.getPoint(0).getX(),
							(int) touchManager.getPoint(0).getY())) {
						position.get(activeShapeIndex).add(
								touchManager.moveDelta(0));
					}
				} else {
					if (touchManager.getPressCount() == 2
							&& shapes.get(activeShapeIndex).isHole()) {
						vca.set(activeShapeIndex, touchManager.getPoint(0));
						vpa.set(activeShapeIndex,
								touchManager.getPreviousPoint(0));
						vcb.set(activeShapeIndex, touchManager.getPoint(1));
						vpb.set(activeShapeIndex,
								touchManager.getPreviousPoint(1));

						Vector2D current = touchManager.getVector(0, 1);
						Vector2D previous = touchManager
								.getPreviousVector(0, 1);

						angle.set(
								activeShapeIndex,
								angle.get(activeShapeIndex)
										- Vector2D.getSignedAngleBetween(
												current, previous));
					}
				}
			}
			sv.invalidate();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return true;
	}
}
