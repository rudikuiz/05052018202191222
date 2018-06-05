package metis.winwin.Model;

/**
 */

public class StatusPinjamanMode {
    String no_aplikasi, pinjaman, bunga, perpanjangan, denda, totaltagihan, jangkapinjaman, jatuhtempo, status;

    public StatusPinjamanMode() {
    }

    public StatusPinjamanMode(String no_aplikasi, String pinjaman, String bunga, String perpanjangan, String denda, String totaltagihan, String jangkapinjaman, String jatuhtempo) {
        this.no_aplikasi = no_aplikasi;
        this.pinjaman = pinjaman;
        this.bunga = bunga;
        this.perpanjangan = perpanjangan;
        this.denda = denda;
        this.totaltagihan = totaltagihan;
        this.jangkapinjaman = jangkapinjaman;
        this.jatuhtempo = jatuhtempo;
    }

    public String getNo_aplikasi() {
        return no_aplikasi;
    }

    public void setNo_aplikasi(String no_aplikasi) {
        this.no_aplikasi = no_aplikasi;
    }

    public String getPinjaman() {
        return pinjaman;
    }

    public void setPinjaman(String pinjaman) {
        this.pinjaman = pinjaman;
    }

    public String getBunga() {
        return bunga;
    }

    public void setBunga(String bunga) {
        this.bunga = bunga;
    }

    public String getPerpanjangan() {
        return perpanjangan;
    }

    public void setPerpanjangan(String perpanjangan) {
        this.perpanjangan = perpanjangan;
    }

    public String getDenda() {
        return denda;
    }

    public void setDenda(String denda) {
        this.denda = denda;
    }

    public String getTotaltagihan() {
        return totaltagihan;
    }

    public void setTotaltagihan(String totaltagihan) {
        this.totaltagihan = totaltagihan;
    }

    public String getJangkapinjaman() {
        return jangkapinjaman;
    }

    public void setJangkapinjaman(String jangkapinjaman) {
        this.jangkapinjaman = jangkapinjaman;
    }

    public String getJatuhtempo() {
        return jatuhtempo;
    }

    public void setJatuhtempo(String jatuhtempo) {
        this.jatuhtempo = jatuhtempo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
