package metis.winwin.Model;


public class KelurahanModel {
    String Kel,Kec,KodePos,Kota;

    public KelurahanModel() {
    }

    public KelurahanModel(String kel, String kec, String kodePos, String kota) {
        Kel = kel;
        Kec = kec;
        KodePos = kodePos;
        Kota = kota;
    }

    public String getKel() {
        return Kel;
    }

    public void setKel(String kel) {
        Kel = kel;
    }

    public String getKec() {
        return Kec;
    }

    public void setKec(String kec) {
        Kec = kec;
    }

    public String getKodePos() {
        return KodePos;
    }

    public void setKodePos(String kodePos) {
        KodePos = kodePos;
    }

    public String getKota() {
        return Kota;
    }

    public void setKota(String kota) {
        Kota = kota;
    }
}
