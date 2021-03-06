package phonezilla.dev01_04_practicum;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity {

    //Declaring the View variables as member variables and assigning them to the Views in the
    // layout
    @InjectView(R.id.userNameField) EditText mUserNameField;
    @InjectView(R.id.passwordField) EditText mPasswordField;
    @InjectView(R.id.emailField) EditText mEmailField;
    @InjectView(R.id.signUpButton) Button mSignUpButton;
    @InjectView(R.id.progressBar2) ProgressBar mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //"injecting" the views into the oncreate method to use them
        ButterKnife.inject(this);


        //Button adds a user on Parse
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVisibillity();

                String username = mUserNameField.getText().toString();
                String password = mPasswordField.getText().toString();
                String email = mEmailField.getText().toString();

                username = username.trim();
                password = password.trim();
                email = email.trim();

                //If empty, show dialog that additional information must be entered to validate
                if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.signup_login_errormessage)
                         .setTitle(R.string.error_title)
                         .setPositiveButton(android.R.string.ok, null);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {

                    //Create the user in Parse
                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(username);
                    parseUser.setPassword(password);
                    parseUser.setEmail(email);

                    //Runs in the background thread, so that the user won't be annoyed
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException pe) {
                            if (pe == null){
                                //Succes !!
                                changeVisibillity();
                                Intent goBackIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                goBackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                goBackIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(goBackIntent);
                            } else {
                                //not successful, show an backend error
                                changeVisibillity();
                                AlertDialog.Builder builder= new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage(pe.getMessage())
                                        .setTitle(R.string.error_title)
                                        .setPositiveButton(android.R.string.ok, null);

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                    });
                }
            }
        });

    }


    public void changeVisibillity(){
        if(mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mSignUpButton.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mSignUpButton.setVisibility(View.VISIBLE);
        }
    }
}
