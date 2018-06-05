package metis.winwin.Model;

/**
 * Created by Laptop on 10/9/2017.
 */
public class HistoryModel {

    private String id, tanggal, pengajuan, status, keterangan;

    public HistoryModel() {
    }

    public HistoryModel(String id, String tanggal, String pengajuan, String status, String keterangan) {
        this.id = id;
        this.tanggal = tanggal;
        this.pengajuan = pengajuan;
        this.status = status;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPengajuan() {
        return pengajuan;
    }

    public void setPengajuan(String pengajuan) {
        this.pengajuan = pengajuan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
