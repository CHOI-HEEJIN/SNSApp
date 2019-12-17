package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Reply extends Activity {
ListView li1;
Button btn1;
SharedPreferencesUtil spu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);
		
		spu=new SharedPreferencesUtil(getApplicationContext());
		li1=(ListView) findViewById(R.id.reply_li1);
		btn1=(Button) findViewById(R.id.reply_btn1);
		Listadapter adp=new Listadapter(Reply.this, G.arr_reply);
		li1.setAdapter(adp);
		
		
		
findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});		//뒤로가기 버튼
btn1.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {		//'답글쓰기' 버튼 클릭했을 때 동작
	
		final EditText ed1=new EditText(getApplicationContext());				//답글을 입력할 텍스트칸 만들기
		ed1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ed1.setTextSize(16);
		
		new AlertDialog.Builder(Reply.this)				//Dialog창을 띄운다.
		.setTitle("답글을 입력하세요")
		.setView(ed1)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {	//확인을 누르면
				 NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/insert_reply.php"	,getApplicationContext());
				 ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();

				//유저id, 게시물id, 답글내용을 ns에 담는다.
				 ns.add(new BasicNameValuePair("user_id", spu.getValue("id", "")));
				 ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
				 ns.add(new BasicNameValuePair("text", ed1.getText().toString().trim()));
				 Toast.makeText(getApplicationContext(), "답글이 등록되었습니다", Toast.LENGTH_SHORT).show();
				 finish();

				 try {
					nw.getStringByPOST(ns);	//ns를 서버에 전달하고, 결과값을 받아온다.
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}
	});
	}
}