package metis.winwin.Model;

/**
 * Created by Ayo Maju on 21/05/2018.
 * Updated by Muhammad Iqbal on 21/05/2018.
 */

public class LogModel {
    private String nama,nomer,sec,date,type;

    public LogModel() {
    }

    public LogModel(String nama, String nomer, String sec, String date, String type) {
        this.nama = nama;
        this.nomer = nomer;
        this.sec = sec;
        this.date = date;
        this.type = type;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
