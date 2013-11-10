package com.cognify.main;

import java.util.ArrayList;

import com.cognify.geometry.Shape;
import com.cognify.geometry.TouchManager;
import com.cognify.loader.LevelLoader;
import com.cognify.geometry.Vector2D;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameActivity extends Activity implements OnTouchListener {

	MySurfaceView sv;
	ArrayList<Shape> shapes;

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

	public boolean isInitialized = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		shapes = new ArrayList<Shape>();
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

		LevelLoader levelLoader = new LevelLoader(this);
		Bundle extras = getIntent().getExtras();
		levelLoader.loadLevel(extras.getInt("level"));

		sv = new MySurfaceView(this);
		sv.setOnTouchListener(this);
		setContentView(sv);
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
				// draw
				if (!holder.getSurface().isValid() || !isInitialized) {
					continue;
				}

				Canvas c = holder.lockCanvas();
				c.drawARGB(255, 150, 150, 10);

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

					c.drawBitmap(shapes.get(n).getBmp(), transform.get(n), paint);
					System.out.println(transform.get(n).toString());
					
					float[] temp = new float[9];
					transform.get(n).getValues(temp);
					
					shapes.get(n).setPosX(temp[2]);
					shapes.get(n).setPosY(temp[5]);
					bitmapBounds.get(n).set((int) shapes.get(n).getPosX(), (int) shapes.get(n).getPosY(),
							(int) shapes.get(n).getPosX() + shapes.get(n).getBmp().getWidth(),
							(int) shapes.get(n).getPosY() + shapes.get(n).getBmp().getHeight());
					// c.drawBitmap(shapes.get(n).getBmp(),
					// shapes.get(n).getPosX(), shapes.get(n).getPosY(), null);
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
		for (int x = 0; x < shapes.size(); x++ ) {
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
						(int) touchManager.getPoint(0).getY())) {
					activeShapeIndex = x;
					break;
				}
			}

			if (touchManager.getPressCount() == 1 && !shapes.get(activeShapeIndex).isHole()) {
				vca.set(activeShapeIndex, touchManager.getPoint(0));
				vpa.set(activeShapeIndex, touchManager.getPreviousPoint(0));
				position.get(activeShapeIndex).add(touchManager.moveDelta(0));
			} else {
				if (touchManager.getPressCount() == 2 && shapes.get(activeShapeIndex).isHole()) {
					vca.set(activeShapeIndex, touchManager.getPoint(0));
					vpa.set(activeShapeIndex, touchManager.getPreviousPoint(0));
					vcb.set(activeShapeIndex, touchManager.getPoint(1));
					vpb.set(activeShapeIndex, touchManager.getPreviousPoint(1));

					Vector2D current = touchManager.getVector(0, 1);
					Vector2D previous = touchManager.getPreviousVector(0, 1);

					angle.set(activeShapeIndex, angle.get(activeShapeIndex)
							- Vector2D.getSignedAngleBetween(current, previous));
				}
			}
			sv.invalidate();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return true;
	}
}
