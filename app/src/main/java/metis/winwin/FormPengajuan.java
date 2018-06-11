package metis.winwin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Adapter.KelurahanAdapter;
import metis.winwin.Adapter.OwnProgressDialog;
import metis.winwin.Model.FileModel;
import metis.winwin.Model.KelurahanModel;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.DataPinjamanManager;
import metis.winwin.Utils.DateTool;
import metis.winwin.Utils.DecimalsFormat;
import metis.winwin.Utils.FileUploader;
import metis.winwin.Utils.GlobalToast;
import metis.winwin.Utils.Hash2Json;
import metis.winwin.Utils.HttpsTrustManager;
import metis.winwin.Utils.MediaProcess;
import metis.winwin.Utils.SessionManager;
import metis.winwin.Utils.VolleyHttp;

import static metis.winwin.Utils.AppConf.URL_HQ;

public class FormPengajuan extends AppCompatActivity {


    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txNilaiPinjam)
    EditText txNilaiPinjam;
    @Bind(R.id.txJangkaPinjam)
    EditText txJangkaPinjam;
    @Bind(R.id.txJatuhTempo)
    EditText txJatuhTempo;
    @Bind(R.id.txTotal)
    EditText txTotal;
    @Bind(R.id.txKuponDiskon)
    EditText txKuponDiskon;
    @Bind(R.id.txNamaLengkap)
    EditText txNamaLengkap;
    @Bind(R.id.txEmail)
    EditText txEmail;
    @Bind(R.id.txPassword)
    EditText txPassword;
    @Bind(R.id.txCPassword)
    EditText txCPassword;
    @Bind(R.id.txNoKtp)
    EditText txNoKtp;
    @Bind(R.id.txTglLahir)
    EditText txTglLahir;
    @Bind(R.id.txAlamatPelanggan)
    EditText txAlamatPelanggan;
    @Bind(R.id.txKota)
    EditText txKota;

    @Bind(R.id.txKecamatan)
    EditText txKecamatan;
    @Bind(R.id.txKodePos)
    EditText txKodePos;
    @Bind(R.id.txStatusRumah)
    BetterSpinner txStatusRumah;
    @Bind(R.id.txLamaTahun)
    BetterSpinner txLamaTahun;
    @Bind(R.id.txLamaBulan)
    BetterSpinner txLamaBulan;
    @Bind(R.id.txTelpRumah)
    EditText txTelpRumah;
    @Bind(R.id.txHandPhone)
    EditText txHandPhone;
    @Bind(R.id.txNamaPerusahaan)
    EditText txNamaPerusahaan;
    @Bind(R.id.txPosisi)
    EditText txPosisi;
    @Bind(R.id.txLamaBekerja)
    EditText txLamaBekerja;
    @Bind(R.id.txNamaHrd)
    EditText txNamaHrd;
    @Bind(R.id.txAlamatPerusahaan)
    EditText txAlamatPerusahaan;
    @Bind(R.id.txStatusKerja)
    BetterSpinner txStatusKerja;
    @Bind(R.id.txTelpPerusahaan)
    EditText txTelpPerusahaan;
    @Bind(R.id.txGajiPerbulan)
    EditText txGajiPerbulan;
    @Bind(R.id.txTglGajian)
    BetterSpinner txTglGajian;
    @Bind(R.id.txNamaKlgSerumah)
    EditText txNamaKlgSerumah;
    @Bind(R.id.txNoTelpklgSerumah)
    EditText txNoTelpklgSerumah;
    @Bind(R.id.txNamaKlgRdkSerumah)
    EditText txNamaKlgRdkSerumah;
    @Bind(R.id.txNoTelpklgTdkSerumah)
    EditText txNoTelpklgTdkSerumah;
    @Bind(R.id.txHubunganKeluarga)
    EditText txHubunganKeluarga;
    @Bind(R.id.txAlamatKlgTdkSerumah)
    EditText txAlamatKlgTdkSerumah;
    @Bind(R.id.txNamaPemilikRek)
    EditText txNamaPemilikRek;
    @Bind(R.id.txCabang)
    EditText txCabang;
    @Bind(R.id.txNoRekening)
    EditText txNoRekening;
    @Bind(R.id.btFoto)
    Button btFoto;
    @Bind(R.id.txFoto)
    TextView txFoto;
    @Bind(R.id.btKtp)
    Button btKtp;
    @Bind(R.id.txKtp)
    TextView txKtp;
    @Bind(R.id.btSlip)
    Button btSlip;
    @Bind(R.id.txSlip)
    TextView txSlip;
    @Bind(R.id.btBukuRek)
    Button btBukuRek;
    @Bind(R.id.txBukuRek)
    TextView txBukuRek;
    @Bind(R.id.btSubmit)
    Button btSubmit;
    @Bind(R.id.ilNilaiPinjam)
    TextInputLayout ilNilaiPinjam;
    @Bind(R.id.ilJangkaPinjam)
    TextInputLayout ilJangkaPinjam;
    @Bind(R.id.ilJatuhTempo)
    TextInputLayout ilJatuhTempo;
    @Bind(R.id.ilTotal)
    TextInputLayout ilTotal;
    @Bind(R.id.ilKuponDiskon)
    TextInputLayout ilKuponDiskon;
    @Bind(R.id.ilNamaLengkap)
    TextInputLayout ilNamaLengkap;
    @Bind(R.id.ilEmail)
    TextInputLayout ilEmail;
    @Bind(R.id.ilPassword)
    TextInputLayout ilPassword;
    @Bind(R.id.ilCPassword)
    TextInputLayout ilCPassword;
    @Bind(R.id.ilNoKtp)
    TextInputLayout ilNoKtp;
    @Bind(R.id.ilTglLahir)
    TextInputLayout ilTglLahir;
    @Bind(R.id.ilAlamatPelanggan)
    TextInputLayout ilAlamatPelanggan;
    @Bind(R.id.ilKota)
    TextInputLayout ilKota;
    @Bind(R.id.ilKelurahan)
    TextInputLayout ilKelurahan;
    @Bind(R.id.ilKecamatan)
    TextInputLayout ilKecamatan;
    @Bind(R.id.ilKodePos)
    TextInputLayout ilKodePos;
    @Bind(R.id.ilStatusRumah)
    TextInputLayout ilStatusRumah;
    @Bind(R.id.ilLamaTahun)
    TextInputLayout ilLamaTahun;
    @Bind(R.id.ilLamaBulan)
    TextInputLayout ilLamaBulan;
    @Bind(R.id.ilTelpRumah)
    TextInputLayout ilTelpRumah;
    @Bind(R.id.ilHandPhone)
    TextInputLayout ilHandPhone;
    @Bind(R.id.ilJenisPekerjaan)
    TextInputLayout ilJenisPekerjaan;
    @Bind(R.id.ilNamaPerusahaan)
    TextInputLayout ilNamaPerusahaan;
    @Bind(R.id.ilPosisi)
    TextInputLayout ilPosisi;
    @Bind(R.id.ilLamaBekerja)
    TextInputLayout ilLamaBekerja;
    @Bind(R.id.ilNamaHrd)
    TextInputLayout ilNamaHrd;
    @Bind(R.id.ilAlamatPerusahaan)
    TextInputLayout ilAlamatPerusahaan;
    @Bind(R.id.ilStatusKerja)
    TextInputLayout ilStatusKerja;
    @Bind(R.id.ilTelpPerusahaan)
    TextInputLayout ilTelpPerusahaan;
    @Bind(R.id.ilGajiPerbulan)
    TextInputLayout ilGajiPerbulan;
    @Bind(R.id.ilTglGajian)
    TextInputLayout ilTglGajian;
    @Bind(R.id.ilNamaKlgSerumah)
    TextInputLayout ilNamaKlgSerumah;
    @Bind(R.id.ilNoTelpklgSerumah)
    TextInputLayout ilNoTelpklgSerumah;
    @Bind(R.id.ilNamaKlgRdkSerumah)
    TextInputLayout ilNamaKlgRdkSerumah;
    @Bind(R.id.ilNoTelpklgTdkSerumah)
    TextInputLayout ilNoTelpklgTdkSerumah;
    @Bind(R.id.ilHubunganKeluarga)
    TextInputLayout ilHubunganKeluarga;
    @Bind(R.id.ilAlamatKlgTdkSerumah)
    TextInputLayout ilAlamatKlgTdkSerumah;
    @Bind(R.id.ilNamaPemilikRek)
    TextInputLayout ilNamaPemilikRek;

    @Bind(R.id.ilCabang)
    TextInputLayout ilCabang;
    @Bind(R.id.ilNoRekening)
    TextInputLayout ilNoRekening;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.imgFoto)
    ImageView imgFoto;
    @Bind(R.id.imgKtp)
    ImageView imgKtp;
    @Bind(R.id.imgSlip)
    ImageView imgSlip;
    @Bind(R.id.imgBukuRek)
    ImageView imgBukuRek;
    @Bind(R.id.lyNoLogin)
    LinearLayout lyNoLogin;
    @Bind(R.id.txBatasKontrak)
    EditText txBatasKontrak;
    @Bind(R.id.ilBatassKontrak)
    TextInputLayout ilBatassKontrak;
    @Bind(R.id.spTujuan)
    Spinner spTujuan;
    @Bind(R.id.rbL)
    RadioButton rbL;
    @Bind(R.id.rbP)
    RadioButton rbP;
    @Bind(R.id.rG)
    RadioGroup rG;
    @Bind(R.id.spBank)
    Spinner spBank;
    @Bind(R.id.spinJenisPekerjaan)
    BetterSpinner spinJenisPekerjaan;
    @Bind(R.id.btKtpTake)
    Button btKtpTake;
    @Bind(R.id.btSlipTake)
    Button btSlipTake;
    @Bind(R.id.btBukuRekTake)
    Button btBukuRekTake;
    @Bind(R.id.txKelurahan)
    AutoCompleteTextView txKelurahan;
    @Bind(R.id.pbCust)
    ProgressBar pbCust;
    @Bind(R.id.lySlip)
    LinearLayout lySlip;
    @Bind(R.id.lyKtp)
    LinearLayout lyKtp;
    @Bind(R.id.lyRek)
    LinearLayout lyRek;
    @Bind(R.id.txRT)
    AutoCompleteTextView txRT;
    @Bind(R.id.ilRT)
    TextInputLayout ilRT;
    @Bind(R.id.txRW)
    AutoCompleteTextView txRW;
    @Bind(R.id.ilRW)
    TextInputLayout ilRW;
    @Bind(R.id.txKodeArea)
    EditText txKodeArea;
    @Bind(R.id.ilKodeArea)
    TextInputLayout ilKodeArea;
    @Bind(R.id.txKotaP)
    EditText txKotaP;
    @Bind(R.id.ilKotaP)
    TextInputLayout ilKotaP;
    @Bind(R.id.btSelfiTake)
    Button btSelfiTake;
    @Bind(R.id.txSelfi)
    TextView txSelfi;
    @Bind(R.id.imgSelfi)
    ImageView imgSelfi;
    @Bind(R.id.lySelfi)
    LinearLayout lySelfi;
    @Bind(R.id.txHandPhoneAlter)
    EditText txHandPhoneAlter;
    @Bind(R.id.ilHandPhoneAlter)
    TextInputLayout ilHandPhoneAlter;
    @Bind(R.id.etKodeTelp)
    EditText etKodeTelp;

    private ProgressDialog progressDialog;
    private ArrayList<View> viewList;
    private ArrayList<TextInputLayout> ilList;
    private String jumlah, periode, jatuh_tempo, total_byr, status_rumah;
    private SessionManager sessionManager;
    private final int PICK_KTP_REQUEST = 111;
    private final int PICK_SLIP_REQUEST = 222;
    private final int PICK_REK_REQUEST = 333;
    //    private Bitmap foto_ktp, foto_slip, foto_rek;
    private RequestQueue requestQueue;
    private byte[] foto_ktp, foto_slip, foto_rek;
    private StringRequest stringRequest;
    private final int MY_SOCKET_TIMEOUT_MS = 10 * 1000;
    private ArrayList<String> tujuan;
    private ArrayList<String> bank;
    private ArrayList<KelurahanModel> arrayList;
    private ArrayList<KelurahanModel> mExampleList;
    RecyclerView rvData;
    SwipeRefreshLayout Swipe;
    EditText etCari;
    KelurahanAdapter kelurahanAdapter;
    int bitmap_size = 50, TAKE_IMAGE = 444, TAKE_IMAGE2 = 555, TAKE_IMAGE3 = 666, TAKE_IMAGE4 = 777;
    Bitmap decoded, decoded2, decoded3;
    String jk;
    private boolean itemSearch, custSearch, isSo;
    private String divId, itemId, custId, urlapi, pathImage;
    private ArrayList<HashMap> listCustDetail;
    private ArrayList<HashMap> listAllItem;
    private ArrayList<HashMap> listSatuan;
    private ArrayList<String> listCust;
    private String[] items = {"Camera", "Gallery"};
    File sdCard, dir, outFileKTP, outFileSLIP, outFileREK, outFileSelfi;
    String fileName;
    private Bitmap bitmap;
    String pathKTP, pathSLIP, pathREK;
    String image1;
    String image2;
    String image3;
    String image4;
    Calendar calendar;
    private Uri mHighQualityImageUri;
    private final int PROS_ID = 8844;
    OwnProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengajuan);
        ButterKnife.bind(this);

        HttpsTrustManager.allowAllSSL();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        loading = new OwnProgressDialog(FormPengajuan.this);

        loading.show();
        requestQueue = Volley.newRequestQueue(FormPengajuan.this);
        sessionManager = new SessionManager(FormPengajuan.this);
        viewList = new ArrayList<>();
        ilList = new ArrayList<>();
        progressDialog = new ProgressDialog(FormPengajuan.this);
        progressDialog.setMessage("Loading...");
        status_rumah = "5";

        arrayList = new ArrayList<>();
        mExampleList = new ArrayList<>();

        tujuan = new ArrayList<>();
        bank = new ArrayList<>();

        listCustDetail = new ArrayList<>();
        listAllItem = new ArrayList<>();
        listSatuan = new ArrayList<>();
        listCust = new ArrayList<>();


        jk = "laki-laki";

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            jumlah = bundle.getString("jumlah");
            periode = bundle.getString("periode");
            jatuh_tempo = bundle.getString("jatuh_tempo");
            total_byr = bundle.getString("total_byr");

            txNilaiPinjam.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(jumlah));
            txJangkaPinjam.setText(periode + " Hari");
            txJatuhTempo.setText(jatuh_tempo);
            txTotal.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(total_byr));

        }

        txKelurahan.setThreshold(2);
        txKelurahan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (s.length() > 1 && !custSearch) {

                    pbCust.setVisibility(View.VISIBLE);
                    custSearch = true;
                    GetCust();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txKelurahan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String kel = listCustDetail.get(i).get("kelurahan").toString().split("-")[0].trim();
//                custId = listCustDetail.get(i).get("id").toString();
                txKodePos.setText(listCustDetail.get(i).get("kodepos").toString());
                txKecamatan.setText(listCustDetail.get(i).get("kecamatan").toString());
                txKelurahan.setText(kel);
                txKota.setText(listCustDetail.get(i).get("kabupaten").toString());
                etKodeTelp.setText(listCustDetail.get(i).get("kodeTelp").toString());
                listAllItem.clear();
                listSatuan.clear();
            }
        });

        InitSpin();
        spinJenisPekerjaan();


        calendar = Calendar.getInstance();
        AndLog.ShowLog("tgll", tanggal_sekarang);

        txHandPhone.setText("08");
        txHandPhone.setMaxEms(15);
        txHandPhoneAlter.setText("08");
        txHandPhoneAlter.setMaxEms(15);
        txNoTelpklgSerumah.setText("08");
        txNoTelpklgSerumah.setMaxEms(15);
        txNoTelpklgTdkSerumah.setText("08");
        txNoTelpklgTdkSerumah.setMaxEms(15);
    }

    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        return day + "/" + (month + 1) + "/" + year;
    }

    // cara pemanggilannya seperti ini
    String tanggal_sekarang = getCurrentDate();
    String tgl, usia;

    @OnClick({R.id.btBack, R.id.btKtp, R.id.txKtp, R.id.btSlip, R.id.btBukuRek, R.id.btSubmit,
            R.id.txTglLahir, R.id.txKelurahan, R.id.btKtpTake, R.id.btBukuRekTake, R.id.btSlipTake, R.id.btSelfiTake})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txTglLahir:
//                new DatePickerDialog(FormPengajuan.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        calendar = Calendar.getInstance();
//                        calendar.set(Calendar.DAY_OF_MONTH, year);
//                        calendar.set(Calendar.MONTH, month + 1);
//                        calendar.set(Calendar.YEAR, dayOfMonth);
//
//                        String dateString = (year - 21) + "-" + month + "-" + dayOfMonth;
////                        String now = year + "-" + month + "-" + dayOfMonth;
//                        tgl = DateTool.changeFormat(dateString, "yyyy-M-dd", "dd MMM yyyy");
//                        txTglLahir.setText(tgl);
//
//                    }
//                },
//                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
//                ).show();
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment().setThemeLight()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                String dateString = year + "-" + month + "-" + dayOfMonth;
                                String tgl = DateTool.changeFormat(dateString, "yyyy-M-dd", "dd MMM yyyy");

                                final Calendar c = Calendar.getInstance();
                                int yearNow = c.get(Calendar.YEAR);
                                int umur = yearNow - year;
                                if (umur < 21) {
                                    Tooat("Maaf, Usia tidak boleh dibawah 21 Tahun");
                                    txTglLahir.getText().clear();
                                } else {
                                    txTglLahir.setText(tgl);
                                }
                                AndLog.ShowLog("asd", String.valueOf(umur));

                            }
                        });
                cdp.show(getSupportFragmentManager(), null);
                break;
            case R.id.btBack:
                finish();
                break;
            case R.id.btKtp:
                if (checkPermission()) {
                    showFileChooser(PICK_KTP_REQUEST);
                }
                break;
            case R.id.txKtp:
                break;
            case R.id.btSlip:
                if (checkPermission()) {
                    showFileChooser(PICK_SLIP_REQUEST);
                }
                break;
            case R.id.btBukuRek:
                if (checkPermission()) {
                    showFileChooser(PICK_REK_REQUEST);
                }
                break;
            case R.id.btSubmit:
                onRadioButtonClicked();
                int err = 0;
                for (int i = 0; i < viewList.size(); i++) {

                    if (viewList.get(i) instanceof EditText) {
                        EditText editText = (EditText) viewList.get(i);
                        if (editText.getText().toString().isEmpty()) {
                            if (err == 0) {
                                viewList.get(i - 1).getParent().requestChildFocus(viewList.get(i - 1), viewList.get(i - 1));
                            }
                            err++;
                            ilList.get(i).setError("Belum diisi");
                        } else {
                            ilList.get(i).setError(null);
                            ilList.get(i).setErrorEnabled(false);

                        }
                    }

                }


                if (!sessionManager.checkLogin() && err == 0) {

                    if (!txPassword.getText().toString().equals(txCPassword.getText().toString())) {
                        err++;
                        ilCPassword.setError("Password tidak sama");
                        txPassword.getParent().requestChildFocus(txPassword, txPassword);
                    }

                    if (txKodePos.getText().toString().length() < 5) {
                        err++;
                        ilKodePos.setError("Minimal 5 karakter");
                        txKodePos.getParent().requestChildFocus(txKodePos, txKodePos);
                    }

                    if (txHandPhone.getText().toString().length() < 8) {
                        err++;
                        ilHandPhone.setError("Minimal 8 karakter");
                        txHandPhone.getParent().requestChildFocus(txHandPhone, txHandPhone);
                    }

                    if (txTelpPerusahaan.getText().toString().length() < 8) {
                        err++;
                        ilTelpPerusahaan.setError("Minimal 8 karakter");
                        txTelpPerusahaan.getParent().requestChildFocus(txTelpPerusahaan, txTelpPerusahaan);
                    }

                    if (txTelpRumah.getText().toString().length() < 6) {
                        err++;
                        ilTelpRumah.setError("Minimal 6 karakter");
                        txTelpRumah.getParent().requestChildFocus(txTelpRumah, txTelpRumah);
                    }

                    if (txNoTelpklgSerumah.getText().toString().length() < 8) {
                        err++;
                        ilNoTelpklgSerumah.setError("Minimal 8 karakter");
                        txNoTelpklgSerumah.getParent().requestChildFocus(txNoTelpklgSerumah, txNoTelpklgSerumah);
                    }

                    if (txNoTelpklgTdkSerumah.getText().toString().length() < 8) {
                        err++;
                        ilNoTelpklgTdkSerumah.setError("Minimal 8 karakter");
                        txNoTelpklgTdkSerumah.getParent().requestChildFocus(txNoTelpklgTdkSerumah, txNoTelpklgTdkSerumah);
                    }

                    if (err == 0) {
                        if (image1 == null) {
                            GlobalToast.ShowToast(FormPengajuan.this, "Silahkan pilih foto ktp");
                            err++;
                        } else if (image2 == null) {
                            GlobalToast.ShowToast(FormPengajuan.this, "Silahkan pilih foto slip gaji");
                            err++;
                        } else if (image3 == null) {
                            GlobalToast.ShowToast(FormPengajuan.this, "Silahkan pilih foto buku rekening");
                            err++;
                        }
                    }
                } else {


                    if (err == 0) {
                        if (image2 == null) {
                            GlobalToast.ShowToast(FormPengajuan.this, "Silahkan pilih foto slip gaji");
                            err++;
                        }
                    }


                }


                //////////////// Submit

                String sts_rmh = txStatusRumah.getText().toString();
                switch (sts_rmh) {
                    case "Milik Sendiri":
                        status_rumah = "1";
                        break;
                    case "Milik Orang tua":
                        status_rumah = "5";
                        break;
                    case "Sewa Kos":
                        status_rumah = "10";
                        break;
                    case "Rumah Dinas / Mes Perusahaan / Lain-lain":
                        status_rumah = "20";
                        break;
                }

                if (!sessionManager.checkLogin()) {

                    String tgl_lhr = DateTool.changeFormat(txTglLahir.getText().toString(), "dd MMM yyyy", "yyyy-MM-dd");

                    String gj = txGajiPerbulan.getText().toString();
                    gj = gj.replace(".", "");
                    gj = gj.replace(",", "");

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("amount", jumlah);
                    params.put("duration", periode);
                    params.put("jatuh_tempo", jatuh_tempo);
                    params.put("total", total_byr);

                    params.put("tujuan", spTujuan.getSelectedItem().toString());
//                params.put("", txKuponDiskon.getText().toString());
                    params.put("nama_lengkap", txNamaLengkap.getText().toString());
                    params.put("jk", jk);
                    params.put("rt", txRT.getText().toString());
                    params.put("rw", txRW.getText().toString());
                    params.put("email", txEmail.getText().toString());
                    params.put("email_alternatif", txEmail.getText().toString());
                    params.put("password", txPassword.getText().toString());
//                params.put("", txCPassword.getText().toString());
                    params.put("no_ktp", txNoKtp.getText().toString());
                    params.put("tgl_lahir", tgl_lhr);
                    params.put("tgl_lahir_label", txTglLahir.getText().toString());
                    params.put("alamat", txAlamatPelanggan.getText().toString());
                    params.put("kota", txKota.getText().toString());
                    params.put("kelurahan", txKelurahan.getText().toString());
                    params.put("kecamatan", txKecamatan.getText().toString());
                    params.put("kodepos", txKodePos.getText().toString());
                    params.put("status_rumah", status_rumah);
                    params.put("status_rumah_label", sts_rmh);
                    params.put("lama_tinggal_tahun", txLamaTahun.getText().toString());
                    params.put("lama_tinggal_bulan", txLamaBulan.getText().toString());
                    params.put("kode_area", txKodeArea.getText().toString());
                    params.put("telepon", txTelpRumah.getText().toString());
                    params.put("handphone", txHandPhone.getText().toString());
                    params.put("handphone_alternatif", txHandPhoneAlter.getText().toString());
                    params.put("jenis_pekerjaan", spinJenisPekerjaan.getText().toString());
                    params.put("perusahaan", txNamaPerusahaan.getText().toString());
                    params.put("posisi", txPosisi.getText().toString());
                    params.put("lama_bekerja", txLamaBekerja.getText().toString());
                    params.put("nama_hrd", txNamaHrd.getText().toString());
                    params.put("alamat_perusahaan", txAlamatPerusahaan.getText().toString());
                    params.put("kota_perusahaan", txKotaP.getText().toString());
                    params.put("status_kontrak", txStatusKerja.getText().toString());
                    params.put("batas_kontrak", txBatasKontrak.getText().toString());
                    params.put("telepon_perusahaan", txTelpPerusahaan.getText().toString());
                    params.put("besar_gaji", gj);
                    params.put("tanggal_gajian", txTglGajian.getText().toString());
                    params.put("nama_keluarga_serumah", txNamaKlgSerumah.getText().toString());
                    params.put("telepon_keluarga_serumah", txNoTelpklgSerumah.getText().toString());
                    params.put("nama_keluarga_tidak_serumah", txNamaKlgRdkSerumah.getText().toString());
                    params.put("telepon_keluarga_tidak_serumah", txNoTelpklgTdkSerumah.getText().toString());
                    params.put("hubungan_keluarga_tidak_serumah", txHubunganKeluarga.getText().toString());
                    params.put("alamat_keluarga_tidak_serumah", txAlamatKlgTdkSerumah.getText().toString());
                    params.put("nama_pemilik_rekening", txNamaPemilikRek.getText().toString());
                    params.put("nama_bank", spBank.getSelectedItem().toString());
                    params.put("lokasi_cabang_bank", txCabang.getText().toString());
                    params.put("nomor_rekening", txNoRekening.getText().toString());

                    JSONObject json = new JSONObject(params);
                    DataPinjamanManager dataPinjamanManager = new DataPinjamanManager(FormPengajuan.this);
                    dataPinjamanManager.clearData();
                    dataPinjamanManager.createData(json.toString());

                }


                if (err == 0) {

                    if (sessionManager.checkLogin()) {
//                        PengajuanProsesLogin();
//                        AsyncTaskRunner task = new AsyncTaskRunner();
//                        task.execute();
                        UpdateSetujui();
                    } else {
                        progressDialog.show();
                        SaveLog();
                        AsyncTaskRunner task = new AsyncTaskRunner();
                        task.execute();
                    }
                }

                break;
            case R.id.btKtpTake:
                if (checkPermission()) {
                    takeImageKTP(view);
                }
                break;
            case R.id.btBukuRekTake:
                if (checkPermission()) {
                    takeImageRek(view);
                }
                break;
            case R.id.btSlipTake:
                if (checkPermission()) {
                    takeImageSlip(view);
                }

                break;
            case R.id.btSelfiTake:
                if (checkPermission()) {
                    takeImageSelfi(view);
                }
                break;
        }
    }

    private void GetCust() {

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_HQ + "ref_kelurahan.php?kodepos_kelurahan=" + txKelurahan.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    listCust.clear();
                    listCustDetail.clear();

                    JSONArray ja = new JSONArray(response);

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jos = ja.getJSONObject(i);

                        String kelurahan = jos.getString("kodepos_kelurahan");
                        String kodepos = jos.getString("kodepos_kode");
                        String kecamatan = jos.getString("kodepos_kecamatan");
                        String kab = jos.getString("kodepos_kabupaten");
                        String kode = jos.getString("kodepos_kode_tlp");
                        HashMap<String, String> cust = new HashMap<>();

                        cust.put("kelurahan", kelurahan);
                        cust.put("kodepos", kodepos);
                        cust.put("kecamatan", kecamatan);
                        cust.put("kabupaten", kab);
                        cust.put("kodeTelp", kode);
                        listCust.add(kelurahan + " - " + kecamatan);
                        listCustDetail.add(cust);

                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (FormPengajuan.this, android.R.layout.simple_dropdown_item_1line, listCust);


                    txKelurahan.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    //ShowToast("Request Timeout");
                }

                custSearch = false;
                pbCust.setVisibility(View.INVISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                custSearch = false;
                pbCust.setVisibility(View.INVISIBLE);
            }

        });

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(FormPengajuan.this).addToRequestQueue(stringRequest);

    }

    public void onRadioButtonClicked() {
        // Is the button now checked?
        int id = rG.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rbL:
                jk = "laki-laki";
                break;
            case R.id.rbP:
                jk = "perempuan";
                break;
        }
    }

    private void InitSpin() {

        DataPinjamanManager dataPinjamanManager = new DataPinjamanManager(FormPengajuan.this);
        if (dataPinjamanManager.isExisting()) {

            try {
                JSONObject jo = new JSONObject(dataPinjamanManager.getJson());

                String amount = jo.getString("amount");
                String duration = jo.getString("duration");
                String jatuh_tempos = jo.getString("jatuh_tempo");
                String total = jo.getString("total");
                String tujuan = jo.getString("tujuan");
                String nama_lengkap = jo.getString("nama_lengkap");
                String email = jo.getString("email");
                String email_alternatif = jo.getString("email_alternatif");
                String password = jo.getString("password");
                String no_ktp = jo.getString("no_ktp");
                String tgl_lahir = jo.getString("tgl_lahir");
                String tgl_lahir_label = jo.getString("tgl_lahir_label");
                String rt = jo.getString("rt");
                String rw = jo.getString("rw");
                String alamat = jo.getString("alamat");
                String kota = jo.getString("kota");
                String kelurahan = jo.getString("kelurahan");
                String kecamatan = jo.getString("kecamatan");
                String kodepos = jo.getString("kodepos");
                String status_rumah = jo.getString("status_rumah");
                String status_rumah_label = jo.getString("status_rumah_label");
                String lama_tinggal_tahun = jo.getString("lama_tinggal_tahun");
                String lama_tinggal_bulan = jo.getString("lama_tinggal_bulan");
                String kodearea = jo.getString("kode_area");
                String telepon = jo.getString("telepon");
                String handphone = jo.getString("handphone");
                String handphone_alternatif = jo.getString("handphone_alternatif");
                String jenis_pekerjaan = jo.getString("jenis_pekerjaan");
                String perusahaan = jo.getString("perusahaan");
                String kota_perusahaan = jo.getString("kota_perusahaan");
                String posisi = jo.getString("posisi");
                String lama_bekerja = jo.getString("lama_bekerja");
                String nama_hrd = jo.getString("nama_hrd");
                String alamat_perusahaan = jo.getString("alamat_perusahaan");
                String status_kontrak = jo.getString("status_kontrak");
                String batas_kontrak = "";
                if (jo.has("batas_kontrak")) {
                    batas_kontrak = jo.getString("batas_kontrak");
                }
                String telepon_perusahaan = jo.getString("telepon_perusahaan");
                String besar_gaji = jo.getString("besar_gaji");
                String tanggal_gajian = jo.getString("tanggal_gajian");
                String nama_keluarga_serumah = jo.getString("nama_keluarga_serumah");
                String telepon_keluarga_serumah = jo.getString("telepon_keluarga_serumah");
                String nama_keluarga_tidak_serumah = jo.getString("nama_keluarga_tidak_serumah");
                String telepon_keluarga_tidak_serumah = jo.getString("telepon_keluarga_tidak_serumah");
                String hubungan_keluarga_tidak_serumah = jo.getString("hubungan_keluarga_tidak_serumah");
                String alamat_keluarga_tidak_serumah = jo.getString("alamat_keluarga_tidak_serumah");
                String nama_pemilik_rekening = jo.getString("nama_pemilik_rekening");
                String nama_bank = jo.getString("nama_bank");
                String lokasi_cabang_bank = jo.getString("lokasi_cabang_bank");
                String nomor_rekening = jo.getString("nomor_rekening");

                txNamaLengkap.setText(nama_lengkap);
                txEmail.setText(email);
                txPassword.setText(password);
                txCPassword.setText(password);
                txNoKtp.setText(no_ktp);

                txTglLahir.setText(tgl_lahir_label);
                txAlamatPelanggan.setText(alamat);
                txKota.setText(kota);
                txRT.setText(rt);
                txRW.setText(rw);
                txKelurahan.setText(kelurahan);
                txKecamatan.setText(kecamatan);
                txKodePos.setText(kodepos);

                txStatusRumah.setText(status_rumah_label);
                txLamaTahun.setText(lama_tinggal_tahun);
                txLamaBulan.setText(lama_tinggal_bulan);
                txKodeArea.setText(kodearea);
                txTelpRumah.setText(telepon);
                txHandPhone.setText(handphone);
                txNamaPerusahaan.setText(perusahaan);
                txPosisi.setText(posisi);
                txLamaBekerja.setText(lama_bekerja);
                txNamaHrd.setText(nama_hrd);
                txAlamatPerusahaan.setText(alamat_perusahaan);
                txKotaP.setText(kota_perusahaan);
                txStatusKerja.setText(status_kontrak);
                txBatasKontrak.setText(batas_kontrak);
                txTelpPerusahaan.setText(telepon_perusahaan);
                txGajiPerbulan.setText(DecimalsFormat.priceWithoutDecimal(besar_gaji));
                txTglGajian.setText(tanggal_gajian);
                txNamaKlgSerumah.setText(nama_keluarga_serumah);
                txNoTelpklgSerumah.setText(telepon_keluarga_serumah);
                txNamaKlgRdkSerumah.setText(nama_keluarga_tidak_serumah);
                txNoTelpklgTdkSerumah.setText(telepon_keluarga_tidak_serumah);
                txHubunganKeluarga.setText(hubungan_keluarga_tidak_serumah);
                txAlamatKlgTdkSerumah.setText(alamat_keluarga_tidak_serumah);
                txNamaPemilikRek.setText(nama_pemilik_rekening);
                txCabang.setText(lokasi_cabang_bank);
                txNoRekening.setText(nomor_rekening);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        viewList.add(txNilaiPinjam);
        viewList.add(txJangkaPinjam);
        viewList.add(txJatuhTempo);
        viewList.add(txTotal);


//        viewList.add(txKuponDiskon);
        if (!sessionManager.checkLogin()) {
            lyNoLogin.setVisibility(View.VISIBLE);
            viewList.add(txNamaLengkap);
            viewList.add(txEmail);
            viewList.add(txPassword);
            viewList.add(txCPassword);
            viewList.add(txNoKtp);
            viewList.add(txTglLahir);
            viewList.add(txAlamatPelanggan);
            viewList.add(txRT);
            viewList.add(txRW);
            viewList.add(txKelurahan);
            viewList.add(txKota);
            viewList.add(txKecamatan);
            viewList.add(txKodePos);
            viewList.add(txStatusRumah);
            viewList.add(txLamaTahun);
            viewList.add(txLamaBulan);
            viewList.add(txKodeArea);
            viewList.add(txTelpRumah);
            viewList.add(txHandPhone);
            viewList.add(spinJenisPekerjaan);
            viewList.add(txNamaPerusahaan);
            viewList.add(txPosisi);
            viewList.add(txLamaBekerja);
            viewList.add(txNamaHrd);
            viewList.add(txAlamatPerusahaan);
            viewList.add(txKotaP);
            viewList.add(txStatusKerja);
            viewList.add(txBatasKontrak);
            viewList.add(txTelpPerusahaan);
            viewList.add(txGajiPerbulan);
            viewList.add(txTglGajian);
            viewList.add(txNamaKlgSerumah);
            viewList.add(txNoTelpklgSerumah);
            viewList.add(txNamaKlgRdkSerumah);
            viewList.add(txNoTelpklgTdkSerumah);
            viewList.add(txHubunganKeluarga);
            viewList.add(txAlamatKlgTdkSerumah);
            viewList.add(txNamaPemilikRek);
//            viewList.add(spBank);
            viewList.add(txCabang);
            viewList.add(txNoRekening);
//            viewList.add(spTujuan);
//            viewList.add(rG);
        } else {
            lyNoLogin.setVisibility(View.GONE);
            lySlip.setVisibility(View.VISIBLE);
            lyKtp.setVisibility(View.GONE);
            lyRek.setVisibility(View.GONE);
        }


        ilList.add(ilNilaiPinjam);
        ilList.add(ilJangkaPinjam);
        ilList.add(ilJatuhTempo);
        ilList.add(ilTotal);
//        ilList.add(ilKuponDiskon);
        if (!sessionManager.checkLogin()) {
            ilList.add(ilNamaLengkap);
            ilList.add(ilEmail);
            ilList.add(ilPassword);
            ilList.add(ilCPassword);
            ilList.add(ilNoKtp);
            ilList.add(ilTglLahir);
            ilList.add(ilAlamatPelanggan);
            ilList.add(ilRT);
            ilList.add(ilRW);
            ilList.add(ilKelurahan);
            ilList.add(ilKota);
            ilList.add(ilKecamatan);
            ilList.add(ilKodePos);
            ilList.add(ilStatusRumah);
            ilList.add(ilLamaTahun);
            ilList.add(ilLamaBulan);
            ilList.add(ilKodeArea);
            ilList.add(ilTelpRumah);
            ilList.add(ilHandPhone);
            ilList.add(ilJenisPekerjaan);
            ilList.add(ilNamaPerusahaan);
            ilList.add(ilPosisi);
            ilList.add(ilLamaBekerja);
            ilList.add(ilNamaHrd);
            ilList.add(ilAlamatPerusahaan);
            ilList.add(ilKotaP);
            ilList.add(ilStatusKerja);
            ilList.add(ilBatassKontrak);
            ilList.add(ilTelpPerusahaan);
            ilList.add(ilGajiPerbulan);
            ilList.add(ilTglGajian);
            ilList.add(ilNamaKlgSerumah);
            ilList.add(ilNoTelpklgSerumah);
            ilList.add(ilNamaKlgRdkSerumah);
            ilList.add(ilNoTelpklgTdkSerumah);
            ilList.add(ilHubunganKeluarga);
            ilList.add(ilAlamatKlgTdkSerumah);
            ilList.add(ilNamaPemilikRek);
            ilList.add(ilCabang);
            ilList.add(ilNoRekening);

        }

        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i) instanceof EditText) {
                EditText editText = (EditText) viewList.get(i);
                if (editText.getTag() == null && editText.getId() != R.id.txKelurahan) {
                    editText.addTextChangedListener(new MyTextWatcher(i));
                }
            }
        }

        String[] STATUS_RMH = getResources().getStringArray(R.array.status_rumah);
        String[] JENIS_PKJ = getResources().getStringArray(R.array.jenis_pekerjaan);
        String[] STATUS_KJR = getResources().getStringArray(R.array.status_kerja);
        String[] LAMA_THN = getResources().getStringArray(R.array.lama_tahun);
        String[] LAMA_BLN = getResources().getStringArray(R.array.lama_bulan);
        String[] TGL_GAJIAN = getResources().getStringArray(R.array.tgl_gajian);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STATUS_RMH);
        txStatusRumah.setAdapter(adapter1);

//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, JENIS_PKJ);
//        txJenisPekerjaan.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STATUS_KJR);
        txStatusKerja.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LAMA_THN);
        txLamaTahun.setAdapter(adapter4);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LAMA_BLN);
        txLamaBulan.setAdapter(adapter5);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TGL_GAJIAN);
        txTglGajian.setAdapter(adapter6);


        txGajiPerbulan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txGajiPerbulan.removeTextChangedListener(this);
                if (charSequence.length() == 0) {
                    ilGajiPerbulan.setError("Belum diisi");
                } else {
                    ilGajiPerbulan.setError(null);
                    ilGajiPerbulan.setErrorEnabled(false);

                    String gj = txGajiPerbulan.getText().toString();
                    gj = gj.replace(".", "");
                    gj = gj.replace(",", "");

                    txGajiPerbulan.setText(DecimalsFormat.priceWithoutDecimal(gj));
                    txGajiPerbulan.setSelection(txGajiPerbulan.getText().toString().length());
                }
                txGajiPerbulan.addTextChangedListener(this);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void UpdateSetujui() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.PINJAMWINWIN + "updatesetujui", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    if (jo.has("error")) {

                        String error = jo.getString("error");

                        if (error.equals("false")) {

                            AsyncTaskRunner task = new AsyncTaskRunner();
                            task.execute();

                        } else {
                            progressDialog.dismiss();
                            GlobalToast.ShowToast(FormPengajuan.this, getString(R.string.gagal_menyambungkan));
                        }

                    } else {
                        progressDialog.dismiss();
                        GlobalToast.ShowToast(FormPengajuan.this, getString(R.string.gagal_menyambungkan));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    GlobalToast.ShowToast(FormPengajuan.this, getString(R.string.gagal_menyambungkan));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                GlobalToast.ShowToast(FormPengajuan.this, getString(R.string.disconnected));
                progressDialog.dismiss();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();
                params.put("id", sessionManager.getIduser());
                params.put("total_setujui", sessionManager.getTotalSetujui());
                params.put("max_pinjam", sessionManager.getMaxpinjam());

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(FormPengajuan.this).addToRequestQueue(stringRequest);

    }


    private void SaveLog() {

        final HashMap<String, String> tmp = new HashMap<>();
        String gj = txGajiPerbulan.getText().toString();
        gj = gj.replace(".", "");
        gj = gj.replace(",", "");
        tmp.put("amount", jumlah);
        tmp.put("duration", periode);
        tmp.put("jatuh_tempo", jatuh_tempo);
        tmp.put("total", total_byr);
        tmp.put("tujuan_pinjam", spTujuan.getSelectedItem().toString());
        tmp.put("nama_lengkap", txNamaLengkap.getText().toString());
        tmp.put("jk", jk);
        tmp.put("email", txEmail.getText().toString());
        tmp.put("email_alternatif", txEmail.getText().toString());
        tmp.put("password", txPassword.getText().toString());
        tmp.put("no_ktp", txNoKtp.getText().toString());
        tmp.put("tgl_lahir", DateTool.changeFormat(txTglLahir.getText().toString(), "dd MMM yyyy", "MM/dd/yyyy"));
        tmp.put("kota", txKota.getText().toString());
        tmp.put("alamat", txAlamatPelanggan.getText().toString());
        tmp.put("rt", txRT.getText().toString());
        tmp.put("rw", txRW.getText().toString());
        tmp.put("kelurahan", txKelurahan.getText().toString());
        tmp.put("kecamatan", txKecamatan.getText().toString());
        tmp.put("kodepos", txKodePos.getText().toString());
        tmp.put("status_rumah", status_rumah);
        tmp.put("lama_tinggal_tahun", txLamaTahun.getText().toString());
        tmp.put("lama_tinggal_bulan", txLamaBulan.getText().toString());
        tmp.put("kode_area", txKodeArea.getText().toString());
        tmp.put("telepon", txTelpRumah.getText().toString());
        tmp.put("handphone", txHandPhone.getText().toString());
        tmp.put("jenis_pekerjaan", spinJenisPekerjaan.getText().toString());
        tmp.put("nama_perusahaan", txNamaPerusahaan.getText().toString());
        tmp.put("posisi", txPosisi.getText().toString());
        tmp.put("lama_bekerja", txLamaBekerja.getText().toString());
        tmp.put("nama_hrd", txNamaHrd.getText().toString());
        tmp.put("alamat_perusahaan", txAlamatPerusahaan.getText().toString());
        tmp.put("kota_perusahaan", txKotaP.getText().toString());
        tmp.put("status_kontrak", txStatusKerja.getText().toString());
        tmp.put("batas_kontrak", txBatasKontrak.getText().toString());
        tmp.put("telepon_perusahaan", txTelpPerusahaan.getText().toString());
        tmp.put("besar_gaji", gj);
        tmp.put("tanggal_gajian", txTglGajian.getText().toString());
        tmp.put("nama_keluarga_serumah", txNamaKlgSerumah.getText().toString());
        tmp.put("telepon_keluarga_serumah", txNoTelpklgSerumah.getText().toString());
        tmp.put("nama_keluarga_tidak_serumah", txNamaKlgRdkSerumah.getText().toString());
        tmp.put("telepon_keluarga_tidak_serumah", txNoTelpklgTdkSerumah.getText().toString());
        tmp.put("hubungan_keluarga_tidak_serumah", txHubunganKeluarga.getText().toString());
        tmp.put("alamat_keluarga_tidak_serumah", txAlamatKlgTdkSerumah.getText().toString());
        tmp.put("nama_pemilik_rekening", txNamaPemilikRek.getText().toString());
        tmp.put("nama_bank", spBank.getSelectedItem().toString());
        tmp.put("lokasi_cabang_bank", txCabang.getText().toString());
        tmp.put("nomor_rekening", txNoRekening.getText().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.PINJAMWINWINLOG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();
                params.put("log", Hash2Json.convertSingle(tmp));

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(FormPengajuan.this).addToRequestQueue(stringRequest);

    }

    private void spinJenisPekerjaan() {
        AndLog.ShowLog("upek", URL_HQ + "ref_pekerjaan.php");
        stringRequest = new StringRequest(Request.Method.GET, URL_HQ + "ref_pekerjaan.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        tujuan.add(json.optString("ref_pekerjaan_nama"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spinJenisPekerjaan.setAdapter(new ArrayAdapter<String>(FormPengajuan.this,
                        R.layout.custom_spinner,
                        tujuan));

                spinBank();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                loading.dismiss();
                if (error instanceof TimeoutError) {
                    Toast.makeText(FormPengajuan.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(FormPengajuan.this, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }

    private void spinBank() {
        AndLog.ShowLog("ubank", URL_HQ + "ref_bank_client.php");
        stringRequest = new StringRequest(Request.Method.GET, URL_HQ + "ref_bank_client.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        bank.add(json.optString("bank_label"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                spBank.setAdapter(new ArrayAdapter<String>(FormPengajuan.this,
                        R.layout.custom_spinner,
                        bank));

                progressDialog.dismiss();
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                loading.dismiss();
                if (error instanceof TimeoutError) {
                    Toast.makeText(FormPengajuan.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(FormPengajuan.this, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    public String uploadFilesWithData() {
        String return_value = "";

        try {
            String charset = "UTF-8";
            String requestURL = null;

            if (sessionManager.checkLogin()) {
                requestURL = AppConf.URL_PENGAJUAN;
            } else {
                requestURL = AppConf.URL_KLIEN;
            }


            FileUploader multipart = new FileUploader(requestURL, charset);


            if (sessionManager.checkLogin()) {
                multipart.addFormField("_session", sessionManager.getSession());
                multipart.addFormField("amount", jumlah);
                multipart.addFormField("duration", periode);
                multipart.addFormField("tujuan_pinjam", spTujuan.getSelectedItem().toString());
            } else {

                String gj = txGajiPerbulan.getText().toString();
                gj = gj.replace(".", "");
                gj = gj.replace(",", "");

                multipart.addFormField("amount", jumlah);
                multipart.addFormField("duration", periode);
                multipart.addFormField("jatuh_tempo", jatuh_tempo);
                multipart.addFormField("total", total_byr);
                multipart.addFormField("tujuan_pinjam", spTujuan.getSelectedItem().toString());
                multipart.addFormField("nama_lengkap", txNamaLengkap.getText().toString());
                multipart.addFormField("jk", jk);
                multipart.addFormField("email", txEmail.getText().toString());
                multipart.addFormField("email_alternatif", txEmail.getText().toString());
                multipart.addFormField("password", txPassword.getText().toString());
                multipart.addFormField("no_ktp", txNoKtp.getText().toString());
                multipart.addFormField("tgl_lahir", DateTool.changeFormat(txTglLahir.getText().toString(), "dd MMM yyyy", "MM/dd/yyyy"));
                multipart.addFormField("alamat", txAlamatPelanggan.getText().toString());
                multipart.addFormField("rt", txAlamatPelanggan.getText().toString());
                multipart.addFormField("rw", txAlamatPelanggan.getText().toString());
                multipart.addFormField("kelurahan", txKelurahan.getText().toString());
                multipart.addFormField("kota", txKota.getText().toString());
                multipart.addFormField("kecamatan", txKecamatan.getText().toString());
                multipart.addFormField("kodepos", txKodePos.getText().toString());
                multipart.addFormField("status_rumah", status_rumah);
                multipart.addFormField("lama_tinggal_tahun", txLamaTahun.getText().toString());
                multipart.addFormField("lama_tinggal_bulan", txLamaBulan.getText().toString());
                multipart.addFormField("kode_area", txKodeArea.getText().toString());
                multipart.addFormField("telepon", etKodeTelp.getText().toString() + txTelpRumah.getText().toString());
                multipart.addFormField("handphone", txHandPhone.getText().toString());
                multipart.addFormField("jenis_pekerjaan", spinJenisPekerjaan.getText().toString());
                multipart.addFormField("nama_perusahaan", txNamaPerusahaan.getText().toString());
                multipart.addFormField("posisi", txPosisi.getText().toString());
                multipart.addFormField("lama_bekerja", txLamaBekerja.getText().toString());
                multipart.addFormField("nama_hrd", txNamaHrd.getText().toString());
                multipart.addFormField("alamat_perusahaan", txAlamatPerusahaan.getText().toString());
                multipart.addFormField("kota_perusahaan", txKotaP.getText().toString());
                multipart.addFormField("status_kontrak", txStatusKerja.getText().toString());
                multipart.addFormField("batas_kontrak", txBatasKontrak.getText().toString());
                multipart.addFormField("telepon_perusahaan", txTelpPerusahaan.getText().toString());
                multipart.addFormField("besar_gaji", gj);
                multipart.addFormField("tanggal_gajian", txTglGajian.getText().toString());
                multipart.addFormField("nama_keluarga_serumah", txNamaKlgSerumah.getText().toString());
                multipart.addFormField("telepon_keluarga_serumah", txNoTelpklgSerumah.getText().toString());
                multipart.addFormField("nama_keluarga_tidak_serumah", txNamaKlgRdkSerumah.getText().toString());
                multipart.addFormField("telepon_keluarga_tidak_serumah", txNoTelpklgTdkSerumah.getText().toString());
                multipart.addFormField("hubungan_keluarga_tidak_serumah", txHubunganKeluarga.getText().toString());
                multipart.addFormField("alamat_keluarga_tidak_serumah", txAlamatKlgTdkSerumah.getText().toString());
                multipart.addFormField("nama_pemilik_rekening", txNamaPemilikRek.getText().toString());
                multipart.addFormField("nama_bank", spBank.getSelectedItem().toString());
                multipart.addFormField("lokasi_cabang_bank", txCabang.getText().toString());
                multipart.addFormField("nomor_rekening", txNoRekening.getText().toString());

            }

            //Tooat(image1+" ===== "+image2+" ===== "+image3+" ====== "+image4);
            if (image1 != null) {
                File imageFile = new File(image1);
                multipart.addFilePart("file_ktp", imageFile);

            }

            if (image2 != null) {
                File imageFile = new File(image2);
                multipart.addFilePart("file_slipgaji", imageFile);

            }

            if (image3 != null) {
                File imageFile = new File(image3);
                multipart.addFilePart("file_bukurek", imageFile);

            }

            if (image4 != null) {
                File imageFile = new File(image4);
                multipart.addFilePart("file_selfi", imageFile);

            }

            List<String> response = multipart.finish();

            //System.out.println("SERVER REPLIED:");

            String combine = "";
            for (String line : response) {
                combine = combine + line;
            }
            return_value = combine;
            //Toast.makeText(this,combine,Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            return_value = ex.getMessage();
        }

        return return_value;
    }

    public String Data() {
        String return_value = "";

        try {
            String charset = "UTF-8";
            String requestURL = AppConf.URL_PENGAJUAN;

            FileUploader multipart = new FileUploader(requestURL, charset);

            multipart.addFormField("amount", jumlah);
            multipart.addFormField("duration", periode);
            multipart.addFormField("jatuh_tempo", jatuh_tempo);
            multipart.addFormField("total", total_byr);
            multipart.addFormField("tujuan_pinjam", spTujuan.getSelectedItem().toString());
            multipart.addFormField("_session", sessionManager.getSession());


            if (image2 != "") {
                File imageFile = new File(image2);
                multipart.addFilePart("file_slipgaji", imageFile);

            }


            List<String> response = multipart.finish();

            //System.out.println("SERVER REPLIED:");

            String combine = "";
            for (String line : response) {
                combine = combine + line;
            }
            return_value = combine;
            //Toast.makeText(this,combine,Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            return_value = ex.getMessage();
        }

        return return_value;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        //        ProgressDialog progressDialog;
        private String result1;
        private String result2;

        @Override
        protected String doInBackground(String... params) {
//            publishProgress("Process..."); // Calls onProgressUpdate()
            //Tooat("Welcome "+params[0]);
            try {

                result1 = "";
                result2 = "";

                //This function is responsible for sending data to our webservice
                resp = uploadFilesWithData();

                AndLog.ShowLog("Values", resp);


            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return result1;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            //  list_adapter= new ListAdapter_UserPanel(User_SelectPark.this, pendingList);
            // listView.setAdapter(list_adapter);
            //Tooat(decision);
            progressDialog.dismiss();
            String status = resp.trim();

            AndLog.ShowLog("rsstss", status);

            try {
                JSONObject jo = new JSONObject(status);
                if (jo.has("error")) {

                    String error = jo.getString("error");

                    if (error.equals("false")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(FormPengajuan.this);

                        builder.setTitle("Berhasil");
                        builder.setCancelable(false);
                        if (sessionManager.checkLogin()) {
                            builder.setMessage(getString(R.string.pengajuan_success));
                        } else {
                            builder.setMessage(getString(R.string.pengajuan_success_baru));
                        }

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                                finish();
                            }
                        });


                        AlertDialog alert = builder.create();
                        alert.show();


                    } else {
                        String message = jo.getString("message");
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormPengajuan.this);

                        builder.setTitle("Gagal");
                        builder.setCancelable(false);
                        if (message.toLowerCase().contains("similar user")) {
                            builder.setMessage("Data yang sama telah terdaftar");
                        } else {
                            builder.setMessage(message);
                        }

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        });


                        AlertDialog alert = builder.create();
                        alert.show();

                    }

                } else {

                    GlobalToast.ShowToast(FormPengajuan.this, getString(R.string.pengajuan_failed));
                }

            } catch (JSONException e) {
                e.printStackTrace();

                GlobalToast.ShowToast(FormPengajuan.this, getString(R.string.disconnected));

            }

            //finalResult.setText(result);
        }

        @Override
        protected void onPreExecute() {


//            progressDialog = ProgressDialog.show(FormPengajuan.this,
//                    "Process",
//                    "Please wait...");
        }

        @Override
        protected void onProgressUpdate(String... text) {
            //finalResult.setText(text[0]);

        }
    }

    private class ATRPinjaman extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;
        private String result1;
        private String result2;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Process...");

            try {

                result1 = "";
                result2 = "";

                resp = Data();

                AndLog.ShowLog("dodods", resp);


            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return result1;
        }

        @Override
        protected void onPostExecute(String result) {
            String status = resp.trim();

            try {
                JSONObject jo = new JSONObject(status);
                if (jo.has("error")) {

                    String error = jo.getString("error");

                    if (error.equals("false")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(FormPengajuan.this);

                        builder.setTitle("Berhasil");
                        builder.setCancelable(false);
                        builder.setMessage(getString(R.string.pengajuan_success));

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                                finish();
                            }
                        });


                        AlertDialog alert = builder.create();
                        alert.show();


                    } else {
                        String message = jo.getString("message");
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormPengajuan.this);

                        builder.setTitle("Gagal");
                        builder.setCancelable(false);
                        builder.setMessage("Data yang sama telah terdaftar");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        });


                        AlertDialog alert = builder.create();
                        alert.show();

                    }

                } else {

                    GlobalToast.ShowToast(FormPengajuan.this, getString(R.string.pengajuan_failed));
                }

            } catch (JSONException e) {
                e.printStackTrace();

                GlobalToast.ShowToast(FormPengajuan.this, getString(R.string.disconnected));

            }
            progressDialog.dismiss();
            //finalResult.setText(result);
        }

        @Override
        protected void onPreExecute() {


            progressDialog = ProgressDialog.show(FormPengajuan.this,
                    "Process",
                    "Please wait...");
        }

        @Override
        protected void onProgressUpdate(String... text) {
            //finalResult.setText(text[0]);

        }
    }

    public void Tooat(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public class MyTextWatcher implements TextWatcher {

        private int pos;

        public MyTextWatcher(int position) {
            pos = position;
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (charSequence.length() == 0) {
                ilList.get(pos).setError("Belum diisi");
            } else {
                ilList.get(pos).setError(null);
                ilList.get(pos).setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        sdCard = Environment.getExternalStorageDirectory();
        dir = new File(sdCard.getAbsolutePath() + "/WinwinClient");
        dir.mkdirs();
        fileName = String.format("%d.jpg", System.currentTimeMillis());
        Bitmap scale = null;

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            String filesx = filePath.getPath();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                filesx = getPath(FormPengajuan.this, filePath);
            }


            try {


                ImageView img = null;
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = false;
//                options.inPreferredConfig = Bitmap.Config.RGB_565;
//                options.inDither = true;
//                Bitmap bmp = BitmapFactory.decodeFile(filesx, options);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                Bitmap bmp = MediaProcess.scaledBitmap(filesx);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);


                switch (requestCode) {
                    case PICK_KTP_REQUEST:
                        img = imgKtp;

                        outFileKTP = new File(dir, fileName);
                        MediaProcess.bitmapToFile(bmp, outFileKTP.getAbsolutePath(), 30);
                        image1 = outFileKTP.getAbsolutePath();
                        txKtp.setVisibility(View.GONE);

                        break;
                    case PICK_SLIP_REQUEST:
                        img = imgSlip;

                        outFileSLIP = new File(dir, fileName);
                        MediaProcess.bitmapToFile(bmp, outFileSLIP.getAbsolutePath(), 30);
                        image2 = outFileSLIP.getAbsolutePath();
                        txSlip.setVisibility(View.GONE);

                        break;
                    case PICK_REK_REQUEST:
                        img = imgBukuRek;

                        outFileREK = new File(dir, fileName);
                        MediaProcess.bitmapToFile(bmp, outFileREK.getAbsolutePath(), 30);
                        image3 = outFileREK.getAbsolutePath();
                        txBukuRek.setVisibility(View.GONE);

                        break;
                }

                //Getting the Bitmap from Gallery
                if (img != null && bmp != null) {

                    Glide.with(FormPengajuan.this).load(stream.toByteArray()).into(img);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            scrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollView.setSmoothScrollingEnabled(false);
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    scrollView.setSmoothScrollingEnabled(true);
                }
            }, 500);

        }

        if (requestCode == TAKE_IMAGE && resultCode == RESULT_OK) {
            if (btKtpTake.isClickable()) {

                try {

                    if (mHighQualityImageUri == null) {
                        GlobalToast.ShowToast(FormPengajuan.this, "Gagal memuat gambar, silahkan coba kembali.");
                    } else {
                        Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), mHighQualityImageUri);
                        MediaProcess.bitmapToFile(bmp, outFileKTP.getAbsolutePath(), 30);
                        scale = MediaProcess.scaledBitmap(outFileKTP.getAbsolutePath());
                        MediaProcess.bitmapToFile(scale, outFileKTP.getAbsolutePath(), 30);
                        Glide.with(FormPengajuan.this).load(outFileKTP.getAbsolutePath()).into(imgKtp);
                        image1 = outFileKTP.getAbsolutePath();
                        txKtp.setVisibility(View.GONE);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        if (requestCode == TAKE_IMAGE2 && resultCode == RESULT_OK) {
            if (btSlipTake.isClickable()) {
                try {
                    if (mHighQualityImageUri == null) {
                        GlobalToast.ShowToast(FormPengajuan.this, "Gagal memuat gambar, silahkan coba kembali.");
                    } else {
                        Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), mHighQualityImageUri);
                        MediaProcess.bitmapToFile(bmp, outFileSLIP.getAbsolutePath(), 30);
                        scale = MediaProcess.scaledBitmap(outFileSLIP.getAbsolutePath());
                        MediaProcess.bitmapToFile(scale, outFileSLIP.getAbsolutePath(), 30);
                        Glide.with(FormPengajuan.this).load(outFileSLIP.getAbsolutePath()).into(imgSlip);
                        image2 = outFileSLIP.getAbsolutePath();
                        txSlip.setVisibility(View.GONE);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        if (requestCode == TAKE_IMAGE3 && resultCode == RESULT_OK) {
            if (btBukuRekTake.isClickable()) {
                try {
                    if (mHighQualityImageUri == null) {
                        GlobalToast.ShowToast(FormPengajuan.this, "Gagal memuat gambar, silahkan coba kembali.");
                    } else {
                        Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), mHighQualityImageUri);
                        MediaProcess.bitmapToFile(bmp, outFileREK.getAbsolutePath(), 30);
                        scale = MediaProcess.scaledBitmap(outFileREK.getAbsolutePath());
                        MediaProcess.bitmapToFile(scale, outFileREK.getAbsolutePath(), 30);
                        Glide.with(FormPengajuan.this).load(outFileREK.getAbsolutePath()).into(imgBukuRek);
                        image3 = outFileREK.getAbsolutePath();
                        txBukuRek.setVisibility(View.GONE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == TAKE_IMAGE4 && resultCode == RESULT_OK) {
            if (btSelfiTake.isClickable()) {
                try {
                    if (mHighQualityImageUri == null) {
                        GlobalToast.ShowToast(FormPengajuan.this, "Gagal memuat gambar, silahkan coba kembali.");
                    } else {
                        Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), mHighQualityImageUri);
                        MediaProcess.bitmapToFile(bmp, outFileSelfi.getAbsolutePath(), 30);
                        scale = MediaProcess.scaledBitmap(outFileSelfi.getAbsolutePath());
                        MediaProcess.bitmapToFile(scale, outFileSelfi.getAbsolutePath(), 30);
                        Glide.with(FormPengajuan.this).load(outFileSelfi.getAbsolutePath()).into(imgSelfi);
                        image4 = outFileSelfi.getAbsolutePath();
                        txSelfi.setVisibility(View.GONE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void takeImageKTP(View view) {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, TAKE_IMAGE);

        FileModel fileModel = generateTimeStampPhotoFileUri();
        mHighQualityImageUri = fileModel.getUriPath();
        outFileKTP = fileModel.getFilePath();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
        startActivityForResult(intent, TAKE_IMAGE);
    }

    public void takeImageSlip(View view) {

        FileModel fileModel = generateTimeStampPhotoFileUri();
        mHighQualityImageUri = fileModel.getUriPath();
        outFileSLIP = fileModel.getFilePath();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
        startActivityForResult(intent, TAKE_IMAGE2);

    }

    public void takeImageRek(View view) {

        FileModel fileModel = generateTimeStampPhotoFileUri();
        mHighQualityImageUri = fileModel.getUriPath();
        outFileREK = fileModel.getFilePath();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
        startActivityForResult(intent, TAKE_IMAGE3);
    }

    public void takeImageSelfi(View view) {

        FileModel fileModel = generateTimeStampPhotoFileUri();
        mHighQualityImageUri = fileModel.getUriPath();
        outFileSelfi = fileModel.getFilePath();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
        startActivityForResult(intent, TAKE_IMAGE4);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public String getStringImage(byte[] bmp) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
//        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(bmp, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser(int code) {
        Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in, "Select Image"), code);
    }


    private FileModel generateTimeStampPhotoFileUri() {

        FileModel fileModel = null;
        Uri photoFileUri = null;
        File photoFile = null;
        File outputDir = getPhotoDirectory();
        if (outputDir != null) {
            photoFile = new File(outputDir, System.currentTimeMillis()
                    + ".jpg");
            photoFileUri = Uri.fromFile(photoFile);

            fileModel = new FileModel(photoFileUri, photoFile);
        }

        return fileModel;
    }


    private File getPhotoDirectory() {
        File outputDir = null;
        String externalStorageStagte = Environment.getExternalStorageState();
        if (externalStorageStagte.equals(Environment.MEDIA_MOUNTED)) {
            File photoDir = Environment.getExternalStorageDirectory();
            outputDir = new File(photoDir, "/WinwinClient");
            if (!outputDir.exists())
                if (!outputDir.mkdirs()) {
                    Toast.makeText(
                            this,
                            "Failed to create directory "
                                    + outputDir.getAbsolutePath(),
                            Toast.LENGTH_SHORT).show();
                    outputDir = null;
                }
        }
        return outputDir;
    }

    private boolean checkPermission() {

        boolean allowed = false;
        if (ActivityCompat.checkSelfPermission(FormPengajuan.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(FormPengajuan.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(FormPengajuan.this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    },
                    PROS_ID);
        } else {
            allowed = true;
        }

        return allowed;
    }
}
