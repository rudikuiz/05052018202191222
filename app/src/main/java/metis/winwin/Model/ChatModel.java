package metis.winwin.Model;

/**
 * Created by Tambora on 14/10/2017.
 */

public class ChatModel {

    String id, iduser, nama, text, waktu, sender, status;
    int imgstatus;

    public ChatModel() {
    }

    public ChatModel(String id, String iduser, String nama, String text, String waktu, String sender, String status, int imgstatus) {
        this.id = id;
        this.iduser = iduser;
        this.nama = nama;
        this.text = text;
        this.waktu = waktu;
        this.sender = sender;
        this.status = status;
        this.imgstatus = imgstatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImgstatus() {
        return imgstatus;
    }

    public void setImgstatus(int imgstatus) {
        this.imgstatus = imgstatus;
    }
}
