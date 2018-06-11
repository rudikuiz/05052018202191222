package metis.winwin.Utils;

/**
 * Created by Web on 15/04/2016.
 */

public class AppConf {

    public static final String URL_HQ = "https://hq.ppgwinwin.com/winwin/api/";
    public static final String URL_HQ2 = "https://hq.ppgwinwin.com:445/userapps/";

    public static final String PINJAMWINWINLOG = "http://118.98.64.43/winwinlog/index.php";
    public static final String PINJAMWINWIN = "https://pinjamwinwin.com/api/";
//    public static final String PINJAMWINWIN = "http://winwinujicoba.tamboraagungmakmur.com/api/";
    //public static final String BASE_URL = "http://messenger.flyvoi.com";
    public static final String SIGNALLING_URL = "http://rtcwinwin.tamboraagungmakmur.com:9950";
    public static final String pref_room_server_url_default = "http://rtcwinwin.tamboraagungmakmur.com:8080";
    public static final String BASE_URL = "http://118.98.64.44";
    public static final String URL_SERV = URL_HQ + "userapps/";
    public static final String URL_SERV_IMG = URL_HQ + "/files/";
    public static final String URL_LOGIN = PINJAMWINWIN + "loginmobile";
    public static final String URL_LOANSTAT = PINJAMWINWIN + "loanstat";
    public static final String URL_UPDATE_SESSION = PINJAMWINWIN + "updatesession";
    public static final String URL_TOKEN = URL_HQ2 + "token";
    public static final String URL_CALL = URL_HQ2 + "call";
    public static final String URL_KLIEN = PINJAMWINWIN + "register";
    public static final String URL_PENGAJUAN = PINJAMWINWIN + "createloan";
    public static final String URL_CUST_INFO = PINJAMWINWIN + "customerinfo";
    public static final String URL_SENDCHAT = URL_HQ2 + "sendchat";
    public static final String URL_GETCHAT = URL_HQ2 + "getchat";
    public static final String URL_CHECKDISETUJUI = URL_HQ2 + "checkdisetujui";
    public static final String URL_HISTORY = URL_HQ2 + "historymobile?id_user=";
    public static final String URL_GET_DATA = URL_HQ + "client_api.php?cli_id=";
    public static final String URL_POST_PINJAM = URL_HQ + "api_sebagian.php";
    public static final String httpTag = "KillHttp";
    public static final String URL_GET_ID_CLIENT = URL_HQ + "get_id_client.php?id_client_web=";

    public static final String URL_GET_COUNT = URL_HQ + "count_client.php?id_client=";
    public static final String GET_BUNGA = URL_HQ + "bunga.php?id_client=";
    public static final String GET_MAX_PINJAMAN = URL_HQ + "maksimal_pinjaman.php?id_client=";
    public static final String GET_STATUS_PINJAMAN = URL_HQ + "detail_pengajuan.php?detail_pengajuan.php=";
    public static final int NOTIFICATION_ID = 1945;
    public static final String URL_GET_STATUS_PINJAMAN = URL_HQ + "status_pinjaman.php?cli_id=";
    public static final String URL_GET_JANJI_BAYAR = URL_HQ + "cek_req_client.php?pengajuan_id_client=";
    public static final String URL_GET_JANJI_TRIAL = URL_HQ + "cek_req_client.php?pengajuan_id_client=";
    public static final String URL_PING = URL_SERV + "notification/ping";
    public static final String UPDATELOKIASI = URL_HQ + "update_lokasi.php";
    public static final String SAVELOG = URL_HQ + "save_log_contact.php";
    public static final String SAVEKONTAK = URL_HQ + "save_kontak.php";
    public static final String JUMLAH_PINJAMAN = URL_HQ + "jumlah_lunas.php?pengajuan_id_client=";


    public static final String PING_PREF = "ping_notification";

    public static final int PING_INTERVAL = 120000;
}
