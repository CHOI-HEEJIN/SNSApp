package co.kr.newp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.kr.newp.ViewImageAdapter;
import co.kr.newp.core.DefaultActivity;
import co.kr.newp.jobs.AsyncImageLoadConnection;

public class Main_content extends DefaultActivity {

	private TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7;
	private ImageView img1,img2,img3;
	private ViewImageAdapter imageAdapter;
	private ViewPager viewPager;
	private String $ID = null;

	private int ischoice;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_content);
		tx1=(TextView) findViewById(R.id.main_content_tx1);
		tx2=(TextView) findViewById(R.id.main_content_tx2);
		tx3=(TextView) findViewById(R.id.main_content_tx3);
		tx4=(TextView) findViewById(R.id.main_content_tx4);
		tx5=(TextView) findViewById(R.id.main_content_tx5);
		tx6=(TextView) findViewById(R.id.main_content_tx6);
		tx7=(TextView) findViewById(R.id.main_content_tx7);

		img2=(ImageView) findViewById(R.id.main_content_img2);
		img3=(ImageView) findViewById(R.id.main_content_img3);
		viewPager = (ViewPager) findViewById(R.id.mainViewPager);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		firstset();
		
	}

	private void firstset() {
		
		Log.e("pic", G.arr.get(G.arr.size()-1).getPic());
		
		if(!getValue("id", "").equals(G.arr.get(G.arr.size()-1).getWriter())){
			tx6.setVisibility(View.GONE);
			tx7.setVisibility(View.GONE);
		}
	//수정 삭제 안보이게
		((TextView)findViewById(R.id.top_text)).setText(G.arr.get(G.arr.size()-1).getWriter()+"의 여행기");
		
		tx1.setText(G.arr.get(G.arr.size()-1).getTitle());
		tx2.setText(G.arr.get(G.arr.size()-1).getDate());
		tx3.setText(G.arr.get(G.arr.size()-1).getWriter());
		tx4.setText(G.arr.get(G.arr.size()-1).getPlace());
		tx5.setText(G.arr.get(G.arr.size()-1).getText());
		
		tx7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Main_content.this)
				.setTitle("정말로 삭제 하시겠습니까")
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						 NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/content_del.php"	,getApplicationContext());
						 ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
						 
						 ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
						 Log.e("num",G.arr.get(G.arr.size()-1).getNum());
						 
						 Toast.makeText(getApplicationContext(), "글이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
						 startActivity(new Intent(getApplicationContext(),Mystory.class));
							finish();
						 try {
							nw.getStringByPOST(ns);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
								
					}
				})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
				
			}
		});
		tx6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 startActivity(new Intent(getApplicationContext(),Write_modify.class));
			}
		});
		
		img2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(G.ischoice==1){
				NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/ischoice_del.php",getApplicationContext());
				ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
				ns.add(new BasicNameValuePair("user_id", getValue("id", "")));
				try {
					String s=nw.getStringByPOST(ns);
					if(s.equals("1")){
						onResume();
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				}
				else if(G.ischoice==0){
					NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/ischoice_ins.php",getApplicationContext());
					ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
					ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
//					ns.add(new BasicNameValuePair("user_id", G.arr.get(G.arr.size()-1).getWriter()));
					ns.add(new BasicNameValuePair("user_id",getValue("id", "")));
					try {
						String s=nw.getStringByPOST(ns);
						if(s.equals("0")){
							onResume();
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}
		});
		img3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {


				NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_replylist.php",getApplicationContext());
				ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
					
				
				 try{
					 JSONArray jsonarr=null;
					 G.arr_reply=new ArrayList<Data_reply>();
					 jsonarr=nw.getJSONArrayByPOST(ns);
					 
					 for(int i=0;i<jsonarr.length();i++){
						
						 JSONObject jsonob=jsonarr.getJSONObject(i);
						  G.arr_reply.add(new Data_reply(jsonob.getString("user_id").toString(), jsonob.getString("text").toString()));
								 
					 } 
					// 나의글 검색후 가져오기
					 
					 
					 // 붙이기
					 
					 
					 
				 }
				 catch(Exception e){
					 
				 }
				 
				 startActivity(new Intent(getApplicationContext(),Reply.class));
				
			}
		});

		try {


			NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_content_image.php", getApplicationContext());
			List<NameValuePair> nm = new ArrayList<>();
			nm.add(new BasicNameValuePair("_IMG_ID", G.arr.get(G.arr.size()-1).getPic()));

			JSONArray jsonarr = nw.getJSONArrayByPOST(nm);

			for(int i = 0 ; i < jsonarr.length() ;i++) {
				JSONObject obj = jsonarr.getJSONObject(i);

				if(this.$ID == null)  {

					imageAdapter = new ViewImageAdapter(getApplicationContext(), $ID = (String)obj.get("_img_id"));
				}

				String seq = (String)obj.get("_seq");
				String img_name = (String)obj.get("_img_name");
				String file_name = (String)obj.get("_file_name");

				Bitmap image = new AsyncImageLoadConnection().execute("http://sogo4070.dothome.co.kr/content/"+img_name).get();

				Log.e("url + "+i, "http://sogo4070.dothome.co.kr/content/"+img_name);

				imageAdapter.addImage(Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), new Matrix(), true),
						Integer.parseInt(seq), img_name, "html/content", file_name);
				Log.e("test : ",$ID+"\t"+seq+"\t"+img_name);
			}

			viewPager.setAdapter(imageAdapter);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	  @Override
	  protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();



		NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/ischoice.php",getApplicationContext());
		ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
		ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
		ns.add(new BasicNameValuePair("user_id", getValue("id", "")));
		try {
			String s=nw.getStringByPOST(ns);
			if(s.equals("1")){
				img2.setImageResource(R.drawable.choice2);
				G.ischoice=1;
			}
			else{
				img2.setImageResource(R.drawable.choice1);
				G.ischoice=0;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("G.choice",G.ischoice+"");
	}
}
