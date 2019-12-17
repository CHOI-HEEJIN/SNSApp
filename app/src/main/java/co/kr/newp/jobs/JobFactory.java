package co.kr.newp.jobs;

import android.os.AsyncTask;

public class JobFactory {

    public static AsyncTask getFtpWriteImage(String file_name) {

        return new AsyncFtpJobConnection(file_name);
    }

    public static AsyncTask getLoadImage() {

        return new AsyncImageLoadConnection();
    }
}
