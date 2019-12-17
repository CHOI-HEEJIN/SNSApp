package co.kr.newp.jobs;

import android.graphics.BitmapFactory;
import android.os.Environment;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AsyncFtpJobConnection extends AsyncImageTask<Object, Void, Boolean> {

    private String host = "sogo4070.dothome.co.kr";
    private String user_name = "sogo4070";
    private String user_password = "sopwer12";

    private int ftp_port = FTP.BINARY_FILE_TYPE;
    private String image_name;
    private int bufferd_size = 1024 * 1024;

    public AsyncFtpJobConnection(String image_name) {
        this.image_name = image_name;
    }

    public AsyncFtpJobConnection(String image_name, String host, String user_name, String user_password) {
        this(image_name);
        this.host = host;
        this.user_name = user_name;
        this.user_password = user_password;
    }

    /**
     * Returns the cached address of the file based on the file name.
     * The returned caching path should be mapped to that address.
     * @see File
     * @param tempFileName
     * @return Cached File Path
     */
    private String cacheFile(String filePath, String tempFileName) {

        return SaveBitmapToFileCache( BitmapFactory.decodeFile(filePath), Environment.getExternalStorageDirectory().getAbsolutePath(), tempFileName);
    }

    /**
     * Returns an ftp connection.
     * The returned connection object is created with the basic information
     * of ftp in this JobObject
     * @return FTPClient FTP OBJECT
     */
    private FTPClient getFtpConnection() throws IOException {

        FTPClient mFTP = new FTPClient();

        mFTP.connect(host, FTPClient.DEFAULT_PORT);
        mFTP.login(user_name, user_password);
        mFTP.setFileType(FTP.BINARY_FILE_TYPE);
        mFTP.setBufferSize(bufferd_size);
        mFTP.enterLocalPassiveMode();


        final String path   = "html";
        final String folder = "content";

        mFTP.cwd(path);
        mFTP.mkd(folder);
        mFTP.cwd(folder);

        return mFTP;
    }

    /**
     * Independent method for reading and writing files.
     * @param image_path image_url
     * @return Uploaded Y/N
     */
    private boolean writeFile(String image_path) {

        try {

            FTPClient mFTP = getFtpConnection();

            File file = new File(image_path);

            if (file.isFile()) {

                FileInputStream file_stream;

                mFTP.storeFile(image_name, file_stream = new FileInputStream(new File(cacheFile(image_path, "/img_temp.jpg"))));

                mFTP.appendFile(image_name, file_stream); // ftp 해당 파일이 없다면 새로쓰기

            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected Boolean doInBackground(Object... params) {

        boolean returnValue = true;

        for(Object o : params) {

            if(returnValue) {

                returnValue = writeFile(((String)o).trim());
            }
        }

        return returnValue;
    }

    public void setBufferdsize(int bufferd_size) {

        this.bufferd_size = bufferd_size;
    }

    public void setFtpPort(int ftp_port) {

        this.ftp_port = ftp_port;
    }
}
