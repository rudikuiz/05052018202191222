package metis.winwin.Model;

import android.net.Uri;

import java.io.File;

/**
 * Created by Lenovo on 5/30/2018.
 */

public class FileModel {

    private Uri uriPath;
    private File filePath;

    public FileModel() {

    }

    public FileModel(Uri uriPath, File filePath) {
        this.uriPath = uriPath;
        this.filePath = filePath;
    }

    public Uri getUriPath() {
        return uriPath;
    }

    public void setUriPath(Uri uriPath) {
        this.uriPath = uriPath;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }
}
