package co.kr.newp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class Listadapter extends BaseAdapter {
Context con;
ArrayList<Data_reply> arr;

public Listadapter(Context con, ArrayList<Data_reply> arr) {
		// TODO Auto-generated constructor stub
		this.con=con;
		this.arr=arr;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arr.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater lif=(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi=lif.inflate(R.layout.layout_list, null);
		TextView tx1=(TextView) vi.findViewById(R.id.layout_list_tx1);
		TextView tx2=(TextView) vi.findViewById(R.id.layout_list_tx2);
		Data_reply dr=arr.get(arg0);
		tx1.setText(dr.getId());
		tx2.setText(dr.getText());
		
		
		return vi;
	}

}
