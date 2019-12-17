package co.kr.newp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.kr.newp.Data_content;
import co.kr.newp.jobs.AsyncImageLoadConnection;

@SuppressLint({ "ViewHolder", "InflateParams" }) public class Gridadapter extends BaseAdapter{ 
	private ArrayList<Data_content> arr;
	private Context con;

	private ArrayList<Bitmap> cached_Image;
	
	public Gridadapter(Context con, ArrayList<Data_content> arr) {
		this.arr=arr;
		this.con=con;
	}
	@Override
	public int getCount() {
		return arr.size();
	}

	
	@Override
	public Object getItem(int position) {

		return arr.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater lif=(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//inflate를 사용하기 위한 선언 -  xml파일에 쓰여 있는 view에 대한 정의를 바탕으로 코드상에서 view 객체를 생성
		View vi=lif.inflate(R.layout.layout_grid, null);				//layout_grid를 inflate한다.
		ImageView img1=(ImageView) vi.findViewById(R.id.layout_grid_img1);	//레이아웃의 뷰들을 변수로 반환
		TextView tx1=(TextView) vi.findViewById(R.id.layout_grid_tx1);
		TextView tx2=(TextView) vi.findViewById(R.id.layout_grid_tx2);
		TextView tx3=(TextView) vi.findViewById(R.id.layout_grid_tx3);
		
		TextView tx4=(TextView) vi.findViewById(R.id.layout_grid_tx4);
		TextView tx5=(TextView) vi.findViewById(R.id.layout_grid_tx5);
		TextView tx6=(TextView) vi.findViewById(R.id.layout_grid_tx6);
		
		Data_content dt=arr.get(position);		//선택한 뷰의 위치를 받아온다.

		this.cached_Image = this.cached_Image == null ? new ArrayList<Bitmap>() : this.cached_Image;

		if(position >= this.cached_Image.size()){		// ???????????무슨뜻이지 뷰의 위치가 이미지 갯수보다 많거나 같으면?
			try {

				NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_content_image.php", con);
				List<NameValuePair> nm = new ArrayList<>();
				nm.add(new BasicNameValuePair("_IMG_ID",dt.getPic()));	//이미지의 id를 nm에 저장한다.

				JSONArray jsonarr = nw.getJSONArrayByPOST(nm);	//이미지id를 서버에 전달하고 그 이미지의 데이터를 jsonarr에 받아온다.

				Bitmap image = null;

				for(int i = 0 ; i < jsonarr.length() ;i++) {
					JSONObject obj = jsonarr.getJSONObject(i);
					String img_name = (String)obj.get("_img_name");	//이미지 이름을 저장

					if(cached_Image.add(image = new AsyncImageLoadConnection().execute("http://sogo4070.dothome.co.kr/content/"+img_name).get())) {
						//이미지의 주소에 연결, 주소를 image에 받아오고, cached_Image에 저장한다. .
						break;
					}

				}

				if(image!=null)	//이미지가 존재할 때
				{
					Matrix mt = new Matrix();
					image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), mt, true); //원본 사진의 크기 그대로 bitmap 생성
					img1.setImageBitmap(image);	//img1에 사진을 세팅한다.
					tx1.setText(dt.getText());	//게시물의 데이터를 뷰에 세팅한다.
					tx2.setText(dt.getDate());
					tx3.setText(dt.getTitle());
					tx4.setText(dt.getChoice());
					tx5.setText(dt.getReply());
					tx6.setText(dt.getWriter());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}


		}
		else {
			img1.setImageBitmap(cached_Image.get(position));
			tx1.setText(dt.getText());
			tx2.setText(dt.getDate());
			tx3.setText(dt.getTitle());
			tx4.setText(dt.getChoice());
			tx5.setText(dt.getReply());
			tx6.setText(dt.getWriter());
		}

		return vi;
	}

}
