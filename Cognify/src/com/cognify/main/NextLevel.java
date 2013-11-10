package com.cognify.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class NextLevel extends Activity	implements View.OnClickListener	{

	Button next;
	int currentLevel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.next_level);
		next = (Button) findViewById(R.id.bProceed);
		next.setOnClickListener(this);
		Bundle extras = getIntent().getExtras();
		currentLevel = extras.getInt("lvl");
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())	{
		case R.id.bProceed:
			Intent p = new Intent();
			Bundle backpack = new Bundle();
			backpack.putInt("next", currentLevel + 1);
			p.putExtras(backpack);
			setResult(RESULT_OK, p);
			Log.v("mgs", "RESULTOK1");
			finish();
			break;
		}
		
	}

}
