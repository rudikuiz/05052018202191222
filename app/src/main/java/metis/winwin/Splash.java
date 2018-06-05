package metis.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private Handler handler;
    private final int DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();

        handler.postDelayed(runnable, DELAY);


    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Intent main = new Intent(Splash.this, MainActivity.class);
            startActivity(main);
            finish();

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
