package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import java.util.Calendar;

public class Timepick extends Activity {
DatePicker dp;
String content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timepick);
		dp=(DatePicker) findViewById(R.id.datePicker1);

		Calendar cal=Calendar.getInstance(); //현재 운영체제에 설정되어 있는 시간대를 기준으로 한 Calendar 하위 객체를 얻을 수 있음
		content=cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH);	// 연/월/일 로 content에 저장

		//timepick 현재 연월일로 초기화, 날짜를 선택하면 OnDateChangedListener() 실행
		dp.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				content=year+"/"+(1+monthOfYear)+"/"+dayOfMonth;	//선택한 날짜를 content에 저장
			}
		});
		
		findViewById(R.id.timepick_btn1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent();
				i.putExtra("content", content);		//content값을 전달
				setResult(1,i);
				finish();
				
			}
		});
	}
}