package phonezilla.dev01_04_practicum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.parse.ParseUser;


// ***The TabListener has been deprecated, will look for update later***
// ***The MainActivity class is implementing TabListener, so the     ***
// *** activity itself is listening for tab changes. We'll have to   ***
// *** replace this later. Remember that TabListener is an interface,***
// *** so we'll be looking for a replacement interface later.        ***
public class MainActivity extends AppCompatActivity  {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final int SHOOT_PHOTO_REQUEST = 0;
    public static final int SHOOT_VIDEO_REQUEST = 1;
    public static final int CHOOSE_PHOTO_REQUEST = 2;
    public static final int CHOOSE_VIDEO_REQUEST = 3;


    //Lets the user take action from the build dialog from action_camera case
    protected DialogInterface.OnClickListener mDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case 0: //Take pictah
                    Intent shootPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(shootPhotoIntent, SHOOT_PHOTO_REQUEST);
                    break;
                case 1: //neem video
                    break;
                case 2: // kies foto
                    break;
                case 3: // kies video
                    break;
            }

        }
    };

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the user cached on disk if present
        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            // If no one is logged in, go to the login screen
            navigateToLogin();
        } else {
            Log.i(TAG, currentUser.getUsername());
        }


        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();

        // ***This is where setNavigationMode has been deprecated***


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.action_log_out:
                ParseUser.logOut();
                navigateToLogin();
                break;
            case R.id.action_add_friends:
                Intent addFriendIntent = new Intent(this, AddFriendsActivity.class);
                startActivity(addFriendIntent);
                break;
            case R.id.action_camera:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(R.array.camera_keuzes, mDialogClickListener);
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }




}