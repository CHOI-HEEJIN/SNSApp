package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Intro extends Activity{
	
	Handler handle;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //해당 액티비티의 액션바를 없애고 풀스크린으로 보이기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_intro);
		handle = new Handler();	//핸들러 객체 생성
		handle.postDelayed(rIntent, 3000); //3초 후에 다음 액티비티(Login)로 이동한다.
		
	}
	Runnable rIntent = new Runnable() {	//Runnable 인터페이스를 통해 동작을 구성
		
		@Override
		public void run() {
			Intent Main = new Intent(Intro.this,Login.class);
			startActivity(Main);
			finish();
			
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			//페이지 이동을 하는 데 fade in, fade out 애니메이션 효과를 넣음
		}
	};
	@Override
	public void onBackPressed() {		//뒤로가기를 누르면 종료시킨다.
		// TODO Auto-generated method stub
		super.onBackPressed();
		handle.removeCallbacks(rIntent);
	}
	
}
