package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

public class Activity_Calendar extends Activity {
    // 캘린더 화면
 MaterialCalendarView mview;
 SharedPreferencesUtil spu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__calendar);
        mview= (MaterialCalendarView) findViewById(R.id.calendarView);
        // 라이브러리 쓴 캘린더 뷰 가져오기
        spu=new SharedPreferencesUtil(getApplicationContext());
        //앱에 저장된 요소 가져오기
        initView();
        //처음 불러올때 부를부분


        mview.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );
        //토요일, 일요일 표시넣기

        mview.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                startActivity(new Intent(getApplicationContext(),Activity_CalendarSaveList.class).putExtra("date",date.getYear()+","+(date.getMonth()+1)+","+date.getDay()));
            }
        });
        //캘린더에서 클릭 눌었을때 화면 이동하기


        mview.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_savedday.php",getApplicationContext());
                ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
                //저장된 부분 요청 넣기

                int Mon=(date.getMonth());
                    ns.add(new BasicNameValuePair("YearMonth",date.getYear()+"/"+(Mon+1)));
                ns.add(new BasicNameValuePair("_id",spu.getValue("id","")));
                //해당 날짜와 아이디를 가지고 서버에 있는 데이터 가져오기


                JSONArray jso;
                try {
                    jso = nw.getJSONArrayByPOST(ns);
                    //서버에 넣은 부분 json 구주로 받기
                    ArrayList<CalendarDay> arr_cal=new ArrayList<>();
                    for(int i=0;i<jso.length();i++){
                        JSONObject jo=jso.getJSONObject(i);
                        String dts= (String) jo.get("_date");
                        String[] parserString=dts.split("/");
                        CalendarDay dt=new CalendarDay(Integer.parseInt(parserString[0]),Integer.parseInt(parserString[1])-1,Integer.parseInt(parserString[2]));
                        arr_cal.add(dt);

                    }
                    mview.addDecorator(new EventDecorator(Color.RED, arr_cal,Activity_Calendar.this));
                    // 데이터 가져온 부분 캘린더에 뿌려주기
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(),Timepick.class),1);
            }
        });
        // 날짜 선택 하는 화면으로 이동하기

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            String strdate=data.getStringExtra("content");
            String[] strs=strdate.split("/");
            CalendarDay cr=new CalendarDay(Integer.parseInt(strs[0]),Integer.parseInt(strs[1])-1,Integer.parseInt(strs[2]));
            mview.setCurrentDate(cr);
        }
        // 날짜 선택하기 화면에서 다시 돌아올때 화면 받는 부분

    }

    private void initView() {
        NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_savedday.php",getApplicationContext());
        ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
                /*if((date.getMonth()+"").length()==2){
                    date= new CalendarDay(date.getYear(),date.getMonth(),date)
                }*/
        Calendar cal=Calendar.getInstance();

        ns.add(new BasicNameValuePair("YearMonth",cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)));
        ns.add(new BasicNameValuePair("_id",spu.getValue("id","")));

        JSONArray jso;
        try {
            jso = nw.getJSONArrayByPOST(ns);
            ArrayList<CalendarDay> arr_cal=new ArrayList<>();
            for(int i=0;i<jso.length();i++){
                JSONObject jo=jso.getJSONObject(i);
                String dts= (String) jo.get("_date");
                String[] parserString=dts.split("/");
                CalendarDay dt=new CalendarDay(Integer.parseInt(parserString[0]),Integer.parseInt(parserString[1])-1,Integer.parseInt(parserString[2]));
                arr_cal.add(dt);

            }
            mview.addDecorator(new EventDecorator(Color.RED, arr_cal,Activity_Calendar.this));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }
    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }





    public class EventDecorator implements DayViewDecorator {

        private final Drawable drawable;
        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates, Activity context) {
            drawable = context.getResources().getDrawable(R.drawable.savedday);
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
            view.addSpan(new DotSpan(5, color)); // 날자밑에 점
        }
    }


}
