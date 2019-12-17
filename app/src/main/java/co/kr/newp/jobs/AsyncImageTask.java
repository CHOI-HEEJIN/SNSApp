package co.kr.newp.jobs;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public abstract class AsyncImageTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    public String SaveBitmapToFileCache(Bitmap bitmap, String strFilePath,
                                        String filename) {

        File file = new File(strFilePath);

        if (!file.exists()) {
            file.mkdirs();
        }

        File fileCacheItem ;
        Log.d("3. ", strFilePath + filename);
        Log.d("4. ", bitmap.toString());
        try(OutputStream out = new FileOutputStream(fileCacheItem = new File(strFilePath + filename))) {
            Log.d("3. ", out.toString());
            Log.d("5. ", fileCacheItem.toString());
            fileCacheItem.createNewFile();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, out);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return strFilePath + filename;
    }
}
