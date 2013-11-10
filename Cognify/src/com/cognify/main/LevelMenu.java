package com.cognify.main;

import com.cognify.loader.LevelLoader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LevelMenu extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_menu);
		addLevelButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addLevelButtons(){
		
		 
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.main_level_layout);		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		params.weight = 1;
		params.setMargins(20, 20, 20, 20);
		
		
		int lNum = 1;//level number
		for(int i=1; i<11; i++)
		{
			LinearLayout innerLayout = new LinearLayout(this);
			innerLayout.setOrientation(LinearLayout.HORIZONTAL);
			
			
			
			for(int j=1; j<6; j++)
			{
				Button temp = new Button(this); 
				
				temp.setText("Lvl"+lNum);
				temp.setTag(lNum);
				temp.setBackgroundResource(R.drawable.level_button_back);
				temp.setLayoutParams(params);
				
				temp.setOnClickListener(this);
				 
				innerLayout.addView(temp);
				lNum++;
			}
			mainLayout.addView(innerLayout);
		}
	}

	@Override
	public void onClick(View v) {
		Button temp = (Button) v;
		//String level = temp.getText().toString();		
		Log.v("lvl click", "lvl " + temp.getTag());

		LevelLoader levelLoader = new LevelLoader(this);
		levelLoader.loadLevel((Integer) temp.getTag());
	}

}
