package co.kr.newp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import co.kr.newp.R;

public class ViewImageAdapter extends PagerAdapter {

    /** Array storage to store images */
    private List<Bitmap> images = new ArrayList<>();

    /** Array storage space to store the image name */
    private List<String> names  = new ArrayList<>();

    /** Array storage space to store the image path */
    private List<String> image_paths  = new ArrayList<>();

    /** Array storage space to store the image file name */
    private List<String> image_names  = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;

    /** About unique indexes in images */
    private String unique_UUID;

    public ViewImageAdapter(Context context, String unique_UUID) {

        this.context = context;
        this.unique_UUID = unique_UUID;
    }

    @Override
    public int getCount() {

        return images.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {

        return this.images.indexOf((View) object) == -1 ? POSITION_NONE : this.images.indexOf((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View v;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.activity_viewpage_assistant, container, false);
        ((ImageView)v.findViewById(R.id.imageView)).setImageBitmap(images.get(position));
        ((TextView)(v.findViewById(R.id.view_file_name))).setText(image_names.get(position));

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {

        container.invalidate();
    }

    public Bitmap getImage (int position) {

        return this.images.get(position);
    }

    public String getPath(int index) {

        return this.image_paths.get(index);
    }

    public String getImageNames (int index){

        return this.names.get(index);
    }

    public boolean addImage (Bitmap bitmap, int position, String image_name, String path, String img_name) {

        this.images.add(bitmap);
        this.names.add(image_name);
        this.image_paths.add(path);
        this.image_names.add(img_name);
        this.notifyDataSetChanged();

        return true;
    }

    public List<NameValuePair> getSaveImage(int index) {

        List<NameValuePair> saveList = new ArrayList<>();

        saveList.add(new BasicNameValuePair("_img_cd",unique_UUID));
        saveList.add(new BasicNameValuePair("_img_seq",Integer.toString(index)));
        saveList.add(new BasicNameValuePair("_img_nm",this.names.get(index)));
        saveList.add(new BasicNameValuePair("_file_name",this.image_names.get(index)));

        return saveList;
    }

}
