package koti.android_testwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.login.LoginManager;


public class MainActivity2 extends AppCompatActivity {

    private Button logout_button;
    private Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //"bundlettaa" intent tiedot ja purkaa ne muuttujiin, jotka sitten tulostetaan activityyn..
        Bundle inBundle = getIntent().getExtras();
        String name = inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();

        TextView nameView = (TextView)findViewById(R.id.nameAndSurname);
        nameView.setText("" + name + " " + surname);
        //ladataan profiili kuva imageviewiin..
        new DownloadImage((ImageView) findViewById(R.id.profileImage)).execute(imageUrl);
        Continue();
        logOutListener();
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
    public void logOutListener(){
        logout_button = (Button) findViewById(R.id.logOutButton);
        logout_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginManager.getInstance().logOut();
                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

    }


    //disabloi taaksepäin napin puhelimesta
    @Override
    public void onBackPressed(){
    }
    //nappi joka jatkaa appin pääikkunaan facebook katsauksen jälkeen..
    public void Continue() {
        continue_button = (Button) findViewById(R.id.continueButton);
        continue_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*tässä passataan FB login tieto tämän activityn yli kolmanteen activityyn jos logataan
                        facebookilla*/
                        Intent intent3 = new Intent(MainActivity2.this, MainActivity3.class);
                        final String firstActivityValues = getIntent().getStringExtra("name");
                        final String firstActivityValues2 = getIntent().getStringExtra("surname");
                        final String firstActivityValues3 = getIntent().getStringExtra("imageUrl");
                        intent3.putExtra("name", firstActivityValues);
                        intent3.putExtra("surname", firstActivityValues2);
                        intent3.putExtra("imageUrl", firstActivityValues3);
                        Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
                        startActivity(intent3);
                        finish();
                    }
                }
        );
    }
}
