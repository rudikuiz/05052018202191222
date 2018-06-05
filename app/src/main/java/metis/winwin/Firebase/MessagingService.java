package metis.winwin.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import metis.winwin.IncommingCall;
import metis.winwin.MainActivity;
import metis.winwin.Model.Notif;
import metis.winwin.R;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.DatabaseHandler;
import metis.winwin.Utils.SessionManager;

/**
 * Created by Tambora on 26/08/2016.
 */
public class MessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        AndLog.ShowLog("NOTIFF", remoteMessage.getData().toString());

        SessionManager sessionManager = new SessionManager(this);
        if (sessionManager.checkLogin() && remoteMessage.getData().get("tipe") != null && !sessionManager.getInCall()) {


            if (remoteMessage.getData().get("tipe").equals("call")) {
                Intent call = new Intent(getApplicationContext(), IncommingCall.class);

                String dariid = remoteMessage.getData().get("dariid");
                String nama = remoteMessage.getData().get("nama");
                String foto = remoteMessage.getData().get("foto");
                String waktu = remoteMessage.getData().get("waktu");
                String tipe = remoteMessage.getData().get("tipe");
                String kode = remoteMessage.getData().get("kode");


                call.putExtra("dariid", dariid);
                call.putExtra("nama", nama);
                call.putExtra("foto", foto);
                call.putExtra("waktu", waktu);
                call.putExtra("tipe", tipe);
                call.putExtra("kode", kode);
                call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(call);

            } else if (remoteMessage.getData().get("tipe").equals("chat")) {


                if(sessionManager.getInChat()){

                    Intent intent = new Intent(getString(R.string.chat));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


                }else {

                    String id_admin = remoteMessage.getData().get("id_admin");
                    int msgID = Integer.parseInt(id_admin);
                    String message = remoteMessage.getData().get("message");
                    String nama = remoteMessage.getData().get("nama");

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("message", message);

                    PendingIntent pendingIntent = PendingIntent.getActivity(this, msgID, intent, PendingIntent.FLAG_ONE_SHOT);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
                    notificationBuilder.setContentTitle(nama);
                    notificationBuilder.setContentText(message);
                    notificationBuilder.setSmallIcon(R.drawable.ic_chatting);
                    notificationBuilder.setAutoCancel(true);
                    notificationBuilder.setContentIntent(pendingIntent);
                    notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
                    //notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(msgID, notificationBuilder.build());

                }

            } else if (remoteMessage.getData().get("tipe").equals("notif")) {

                Notif notif = new Notif();
                notif.setId_pengajuan(remoteMessage.getData().get("pengajuan_id"));
                notif.setMessage(remoteMessage.getData().get("message"));

                DatabaseHandler databaseHandler = new DatabaseHandler(this);
                databaseHandler.addNotif(notif);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("message1", notif.getMessage());

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
                notificationBuilder.setContentTitle("Notifikasi");
                notificationBuilder.setContentText(notif.getMessage());
                notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
                notificationBuilder.setAutoCancel(true);
                notificationBuilder.setContentIntent(pendingIntent);
                notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
                //notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notificationBuilder.build());

            }

        }


    }
}
