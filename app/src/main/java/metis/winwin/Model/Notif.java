package metis.winwin.Model;

/**
 * Created by innan on 11/11/2017.
 */

public class Notif {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_pengajuan() {
        return id_pengajuan;
    }

    public void setId_pengajuan(String id_pengajuan) {
        this.id_pengajuan = id_pengajuan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String id_pengajuan;
    private String message;

    public Notif() {
    }
}
