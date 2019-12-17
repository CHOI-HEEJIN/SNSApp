package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Setting extends Activity {
	ImageView img2,img3,img4,img5;
	RelativeLayout rel1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		img2=(ImageView) findViewById(R.id.img2);
		img3=(ImageView) findViewById(R.id.img3);
		img4=(ImageView) findViewById(R.id.img4);
		img5=(ImageView) findViewById(R.id.img5);
		rel1=(RelativeLayout) findViewById(R.id.rel1);
		setbtnexe();
	}
	 private void setbtnexe() {
			// TODO Auto-generated method stub
		 rel1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Story2.class));
				
			}
		});
		 
			img3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getApplicationContext(),Story.class));
				}
			});
			
			img4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Write.class));
					
				}
			});
img2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Mystory.class));
					
				}
			});

img5.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	startActivity(new Intent(getApplicationContext(),Setting.class));
		
	}
});

	 }
	 
}
