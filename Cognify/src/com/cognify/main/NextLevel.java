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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.next_level);
		next = (Button) findViewById(R.id.bProceed);
		next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())	{
		case R.id.bProceed:
			Intent p = new Intent();
			setResult(RESULT_OK, p);
			finish();
			break;
		}
		
	}

}
