package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_CalendarSaveList extends Activity {
    // 캘린더에서 저장된 부분 가져오는 화면
    ListView li1;
    ArrayList<Data_content> arr;
    SharedPreferencesUtil spu;

    //리스트뷰
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__calendar_save_list);

        spu=new SharedPreferencesUtil(getApplication());
        arr=new ArrayList<>();
        String date=getIntent().getStringExtra("date");
        String[] split_date=date.split(",");
        li1= (ListView) findViewById(R.id.li1);

        NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/get_calendarcontent.php"	,getApplicationContext());
        ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();

        ns.add(new BasicNameValuePair("_date", split_date[0]+"/"+split_date[1]+"/"+split_date[2]));
        ns.add(new BasicNameValuePair("_id", spu.getValue("id","")));


        try{
            JSONArray jsonarr=null;
            // 서버에 캘린더  가져오기

            jsonarr=nw.getJSONArrayByPOST(ns);
            for(int i=0;i<jsonarr.length();i++){
                JSONObject jsonob=jsonarr.getJSONObject(i);
                arr.add(new Data_content(jsonob.getString("_num").toString(),jsonob.getString("_writer").toString(),jsonob.getString("_date").toString(), jsonob.getString("_title").toString(),jsonob.getString("_place").toString(), jsonob.getString("_pic").toString(), jsonob.getString("_text").toString()));

            }
            // 나의글 검색후 가져오기

        }
        catch(Exception e){

        }


        CalendarAdapter adp=new CalendarAdapter();
        li1.setAdapter(adp);

        li1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                G.arr.add(arr.get(i));

                startActivity(new Intent(Activity_CalendarSaveList.this,Main_content.class));
            }
        });

    }




    public class CalendarAdapter extends BaseAdapter{
        //리스트뷰에 뿌려주는 어댑터 부분

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int i) {
            return arr.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View vi=((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_calendar,null);
            Data_content dc=arr.get(i);
            TextView tx1= (TextView) vi.findViewById(R.id.layout_list_tx1);
            TextView tx2= (TextView) vi.findViewById(R.id.layout_list_tx2);
            TextView tx3= (TextView) vi.findViewById(R.id.layout_list_tx3);
            TextView tx4= (TextView) vi.findViewById(R.id.layout_list_tx4);
            tx1.setText(dc.getNum());
            tx2.setText(dc.getDate());
            tx3.setText(dc.getTitle());
            tx4.setText(dc.getText());
            return vi;
        }
    }
}
