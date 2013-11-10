package com.cognify.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Walkthrough extends Activity	{

	TextView txt;
	int level;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.walkthrough);
		txt = (TextView) findViewById(R.id.tvWalkTxt);
		Bundle extras =  getIntent().getExtras();
		level = extras.getInt("lvl");
		
		switch(level)	{
		case 1:
			txt.setText("Welcome to Cognify, the game beyond cognition." +
					"  Your objective is to move the shapes into their respective holes." +
					"  Drag the blue square into the corresponding gray hole.");
			break;
		}
	}

}
