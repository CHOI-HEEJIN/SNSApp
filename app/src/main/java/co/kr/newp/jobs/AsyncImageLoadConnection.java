package co.kr.newp.jobs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class AsyncImageLoadConnection extends AsyncImageTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {

        try {

            URLConnection urlConnection;

            (urlConnection = new URL(params[0]).openConnection()).connect();    //url에 접속 설정(사진이 있는 주소로 접근)하고 연결
            InputStream is = urlConnection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(new BufferedInputStream(is, urlConnection.getContentLength()));
            is.close();
            return image;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }


}
