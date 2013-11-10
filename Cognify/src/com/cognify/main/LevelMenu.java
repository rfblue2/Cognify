package com.cognify.main;

import java.util.ArrayList;

import com.cognify.loader.LevelLoader;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
	
	ArrayList<Button>levelButtons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_menu);
		levelButtons = new ArrayList<Button>();
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
		mainLayout.setPadding(50, 50, 50, 50);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		params.weight = 1;
		params.setMargins(10, 10, 10, 10);
		
		
		int lNum = 1;//level number
		for(int i=1; i<11; i++)
		{
			LinearLayout innerLayout = new LinearLayout(this);
			innerLayout.setOrientation(LinearLayout.HORIZONTAL);
			
			
			
			for(int j=1; j<5; j++)
			{
				Button temp = new Button(this); 
				
				temp.setText("Lvl"+lNum);
				temp.setTag(lNum);
				temp.setSingleLine(true);
				temp.setPadding(30, 30, 30, 30);
				temp.setBackgroundResource(R.drawable.level_gear);
				temp.setLayoutParams(params);
				
				temp.setOnClickListener(this);
				levelButtons.add(temp);
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
		Intent i = new Intent(this, GameActivity.class);
		i.putExtra("level",  (Integer) temp.getTag());
		this.startActivityForResult(i, 0);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)	{
			Bundle info = data.getExtras();
			boolean arr[] = info.getBooleanArray("finished");
			for(int i = 0; i < 40; i++)	{//HARDCODED # OF LEVELS
				if(arr[i])	{
					levelButtons.get(i).setBackgroundResource(R.drawable.level_gear_passed);
				}
			}
		}
	}

}
