package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.UUID;

import co.kr.newp.core.DefaultActivity;
import co.kr.newp.utils.FileUtils;
import co.kr.newp.jobs.JobFactory;

public class Write extends DefaultActivity {

    private EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7;
    private ImageView img1, bottom_img2, bottom_img3, bottom_img4, bottom_img5, img2;
    private Button btn1;
    private CheckBox checkBox;
    int privacy;
    private String $ID = UUID.randomUUID().toString();

    private String picname = "";
    private String path = "";

    private WriteImageAdapter imageAdapter;
    private ViewPager viewPager;

    final int REQ_CODE_SELECT_IMAGE = 200;
    final static String SUCCES_UPWRITE = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        //레이아웃의 뷰들을 변수로 반환한다.
        bottom_img2 = findViewById(R.id.main_profile_img2);
        bottom_img3 = findViewById(R.id.main_profile_img3);
        bottom_img4 = findViewById(R.id.main_profile_img4);
        bottom_img5 = findViewById(R.id.main_profile_img5);

        setbutton();
        setbuttonexe();
    }

    private void setbuttonexe() {

        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {    //게시물 저장 버튼 클릭시

                if(checkBox.isChecked()) {      //체크박스가 체크되어있으면
                    privacy = 1;                //게시물 비공개로 저장
                }
                else {
                    privacy = 0;                //체크되어있지 않으면 공개로 저장
                }

                String title = ed3.getText().toString().trim();     //뷰에 작성한 내용을 변수에 담는다.
                String place = ed4.getText().toString().trim();
                String date  = ed6.getText().toString().trim();
                String text  = ed7.getText().toString().trim();
                String pri  =  String.valueOf(privacy);

                if (title != null && !title.equals("")      //제목, 장소, 날짜를 모두 입력했다면
                        && place != null  && !place.equals("")
                        && date != null && !date.equals("")) {

                    ArrayList<NameValuePair> ns = new ArrayList<NameValuePair>();

                    //저장할 데이터를 ns에 담는다.
                    ns.add(new BasicNameValuePair("_writer", getValue("id", "")));
                    ns.add(new BasicNameValuePair("_title" , title)     );
                    ns.add(new BasicNameValuePair("_place" , place)     );
                    ns.add(new BasicNameValuePair("_date"  , date)      );
                    ns.add(new BasicNameValuePair("_text"  , text)      );
                    ns.add(new BasicNameValuePair("_pic"   , $ID)       );
                    ns.add(new BasicNameValuePair("_privacy" , pri)     );


                    try {

                        if (new NetWork("http://sogo4070.dothome.co.kr/write_content.php",
                                getApplicationContext()).getStringByPOST(ns).equals(SUCCES_UPWRITE)) {
                            //저장할 데이터를 서버에 전달하고, 성공적으로 작업이 이루어졌는지 확인한다.

                            /** regist Image Name Update */
                            for(int i = 0 ; i < imageAdapter.getCount()-1 ; i++) {
                                new NetWork("http://sogo4070.dothome.co.kr/write_content_images.php",
                                        getApplicationContext()).getStringByPOST(imageAdapter.getSaveImage(i));
                                //저장할 이미지들을 서버에 전달한다.

                                JobFactory.getFtpWriteImage(imageAdapter.getImageNames(i)).execute(imageAdapter.getPath(i));
                                //ftp에 이미지를 저장한다.
                            }

                            Toast.makeText(getApplicationContext(), "글이 정상적으로 등록 되었습니다.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Mystory.class));
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "잘못 입력된 부분이있습니다", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        img1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {       //달력 버튼을 클릭했을 때
                startActivityForResult(new Intent(getApplicationContext(), Timepick.class), 1);
                //TimPick 액티비티를 띄워 값을 받는다.
                }

        });

        img2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {       //사진편집 클릭할 때
                Intent intent = new Intent(Intent.ACTION_PICK);         //데이터로부터 아이템을 선택하고 선택한 아이템을 리턴시키라는 액션
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);           //외부에 저장되어있는 데이터를 가져올 것임을 선언
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);                  //이미지를 넘긴다.
            }
        });

        bottom_img2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Mystory.class));
                finish();
            }
        });

        bottom_img3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Story.class));
                finish();
            }
        });

        bottom_img5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Setting.class));
                finish();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                //뷰페이져 내부에 존재하는 페이지에 변화가 생겼을 때 호출

                img2.setImageBitmap(imageAdapter.getImage(position));      //position에 맞는 이미지를 가져와 뷰에 세팅한다.
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {         //이미지가 정상적으로 넘어왔다면

                try {

                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    Matrix mat = new Matrix();

                    image_bitmap = Bitmap.createBitmap(image_bitmap, 0, 0, image_bitmap.getWidth(), image_bitmap.getHeight(), mat, true);
                    //원본이미지 그대로 비트맵 생성
                    img2.setImageBitmap(image_bitmap);      //이미지를 뷰에 세팅한다.

                    String img_name;
                    picname = FileUtils.getImageNameFormat( img_name = ( path = (getPath(data.getData()) + "") ).substring(path.length() - 9), this);
                    //img_name에 이미지의 경로를 담고, 이미지이름 포맷하여 picname에 담는다.

                    JobFactory.getFtpWriteImage(picname).execute(path);     //이미지를 파일로 저장한다.

                    if(imageAdapter.addImage(image_bitmap, viewPager.getCurrentItem(), picname, path, img_name))  //이미지어댑터에 이어붙이기
                        viewPager.setCurrentItem(imageAdapter.getCount()-1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (resultCode == 1) {
            String x = data.getStringExtra("content");
            ed6.setText(x);
        }
    }

    //레이아웃의 뷰를 변수로 반환
    private void setbutton() {

        ed1 = (EditText) findViewById(R.id.member_join_ed1);
        ed2 = (EditText) findViewById(R.id.member_join_ed2);
        ed3 = (EditText) findViewById(R.id.member_join_ed3);
        ed4 = (EditText) findViewById(R.id.member_join_ed4);
        ed5 = (EditText) findViewById(R.id.member_join_ed5);
        ed6 = (EditText) findViewById(R.id.member_join_ed6);
        ed7 = (EditText) findViewById(R.id.member_join_ed7);

        img1 = (ImageView) findViewById(R.id.member_join_date);
        img2 = (ImageView) findViewById(R.id.writeImageView);
        img2.setImageResource(R.drawable.pic);
        btn1 = findViewById(R.id.member_join_btn_confirm);

        viewPager = (ViewPager) findViewById(R.id.writeViewPager);
        viewPager.setAdapter(imageAdapter = new WriteImageAdapter(getApplicationContext(), $ID));

        checkBox = (CheckBox)findViewById(R.id.checkBox);

    }

    public String getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = managedQuery(uri, projection, null, null, null);
        // 이미지 경로로 해당 이미지에 대한 정보를 가지고 있는 cursor 호출

        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // 해당 필드의 인덱스를 반환하고, 존재하지 않을 경우 예외를 발생시킨다.
        cursor.moveToFirst();            //가장 처음에 위치한 레코드를 가리킨다.

        return cursor.getString(columnIndex);
    }
}


