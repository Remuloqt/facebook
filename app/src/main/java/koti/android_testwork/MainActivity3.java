package koti.android_testwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import com.facebook.login.LoginManager;


public class MainActivity3 extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

         //hakee giffin URL:in ja syöttää sen webviewiin..
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadUrl("https://media2.giphy.com/media/26tnaxgK4tronxDUI/giphy.gif");

        //"bundlettaa" intent tiedot ja purkaa ne muuttujiin, jotka sitten tulostetaan activityyn..
        Bundle inBundle = getIntent().getExtras();
        String name = inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();

        TextView txtField = (TextView) findViewById(R.id.nameAndSurname);
        txtField.setText("" + name + " " + surname);

    }

    //yläpalkki
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            logOutListener();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //logout nappi, joka vie takaisin ensimmäiseen login activityyn..
    public void logOutListener() {
                        LoginManager.getInstance().logOut();
                        Intent intent4 = new Intent(MainActivity3.this, MainActivity.class);
                        startActivity(intent4);
                        finish();
                    }


    //disabloi taaksepäin napin puhelimesta
    @Override
    public void onBackPressed() {
    }

}
