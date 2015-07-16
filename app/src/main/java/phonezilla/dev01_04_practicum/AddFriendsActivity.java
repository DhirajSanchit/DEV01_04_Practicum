package phonezilla.dev01_04_practicum;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class AddFriendsActivity extends ListActivity {

    public static final String TAG = AddFriendsActivity.class.getSimpleName();

    protected List<ParseUser> mUsers;
    protected ParseRelation<ParseUser> mFriendsRelations;
    protected ParseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    protected void onResume(){

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelations = mCurrentUser.getRelation(ParseConstantValues.KEY_FRIEND_RELATIONS);

        setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending(ParseConstantValues.KEY_USERNAME);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    //callback was a succes, Display usernames,
                    mUsers = users;
                    String[] usernames = new String[mUsers.size()];
                    int i = 0;
                    for (ParseUser user : mUsers) {
                        usernames[i] = user.getUsername();
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddFriendsActivity.this, android.R.layout.simple_list_item_checked, usernames);
                    setListAdapter(adapter);

                    addFriendsVinkjes();
                } else {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(AddFriendsActivity.this, "Unfortunatly was an error!", Toast.LENGTH_LONG).show();
                }
            }
        });

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Adds a relationship between te users and saves it in the background.
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(getListView().isItemChecked(position)){
            //succes, add selected friendsz
            mFriendsRelations.add(mUsers.get(position));

        } else {
            //remove dat b**ch
            mFriendsRelations.remove(mUsers.get(position));
        }
        //Change is saved, no matter what :D
        mCurrentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if ( e != null){
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(AddFriendsActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addFriendsVinkjes(){
        mFriendsRelations.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseFriends, ParseException e) {
                if ( e == null){
                    // Returns a list, now look for a match

                    for (int i = 0; i < mUsers.size(); i++ ){
                        ParseUser user = mUsers.get(i);


                    for (ParseUser friend : parseFriends) {
                        if (friend.getObjectId().equals(user.getObjectId())) {
                            getListView().setItemChecked(i, true);
                            }
                        }
                    }
                } else {
                    Log.e(TAG, e.getMessage());
                }
            }

        });
    }


}
