package metis.winwin.Model;

/**
 * Created by Laptop on 10/9/2017.
 */
public class NotifikasiModel {

    String id, nama, bagian, tanggal, alamat;


    public NotifikasiModel() {
    }

    public NotifikasiModel(String id, String nama, String bagian, String tanggal, String alamat) {
        this.id = id;
        this.nama = nama;
        this.bagian = bagian;
        this.tanggal = tanggal;
        this.alamat = alamat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBagian() {
        return bagian;
    }

    public void setBagian(String bagian) {
        this.bagian = bagian;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
