package koti.android_testwork;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class MainActivity extends AppCompatActivity {

    private static Button login_Button;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;


    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            nextActivity(profile);
        }
        @Override
        public void onCancel() {        }
        @Override
        public void onError(FacebookException e) {      }
    };

    //login napin toiminnallisuus...
    public void loginListener(){
        login_Button = (Button) findViewById(R.id.loginButton);
        final EditText name_id = (EditText) findViewById(R.id.nameField);
        final EditText pass_id = (EditText) findViewById(R.id.passwordField);
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        login_Button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //voisi implementoida database haun jos olisi database...
                        if (name_id.getText().toString().equals("Roy.Forest") && pass_id.getText().toString().equals("testikoodi")) {
                            Intent intent2 = new Intent(MainActivity.this, MainActivity3.class);
                            String firstName = "Roy";
                            String lastName = "Forest";
                            intent2.putExtra("name", firstName);
                            intent2.putExtra("surname", lastName);
                            Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
                            startActivity(intent2);
                            finish();
                        } else {
                            dlgAlert.setMessage("Wrong username or password.");
                            dlgAlert.setTitle("Error Message...");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                        }
                    }
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginListener();
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                this.stopTracking();
                nextActivity(newProfile);
            }
        };


        accessTokenTracker.startTracking();

        LoginButton loginButton = (LoginButton)findViewById(R.id.faceLogButton);
        // hakee ap ID, etunimen, sukunimen ja kuvan..
        callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if (Profile.getCurrentProfile() == null) {
                    profileTracker.startTracking();
                    Toast.makeText(getApplicationContext(), "Fetching Data...", Toast.LENGTH_SHORT).show();
                    nextActivity(profile);

                    //tällä saisi enemmän tietoa profiilista irti mm. emailin jos ne on ovat public info fb acossa..
                    /*GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(
                                        JSONObject object,
                                        GraphResponse response) {
                                    Log.v("LoginActivity Response ", response.toString());

                                    try {
                                        Name = object.getString("name");

                                        FEmail = object.getString("email");
                                        Log.v("Email = ", " " + FEmail);
                                        //Toast.makeText(getApplicationContext(), "Name " + Name, Toast.LENGTH_LONG).show();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();*/
                }

            }


            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        };
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, callback);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        callbackManager.onActivityResult(requestCode, responseCode, intent);

    }

    //pistää intentin mukana facebookin tiedot eteenpäin seuraavalle activitylle...
    private void nextActivity(Profile profile){
        if(profile != null){
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("name", profile.getFirstName());
            intent.putExtra("surname", profile.getLastName());
            intent.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
            //intent.putExtra("email", FEmail);
            startActivity(intent);
            finish();
        }
    }
}
