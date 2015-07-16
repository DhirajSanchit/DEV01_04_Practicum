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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.signUpTextButton) TextView mSignUpView;
    @InjectView(R.id.userNameTextField) EditText mUserNameField;
    @InjectView(R.id.passwordTextField) EditText mPasswordField;
    @InjectView(R.id.loginButton) Button mLoginButton;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);



        mSignUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signUpIntent);
            }
        });



        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVisibillity();
                String username = mUserNameField.getText().toString();
                String password = mPasswordField.getText().toString();


                username = username.trim();
                password = password.trim();


                //If empty, show dialog that additional information must be entered to validate
                if(username.isEmpty() || password.isEmpty()) {
                    //Error when logging in
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.signup_login_errormessage)
                            .setTitle(R.string.error_title)
                            .setPositiveButton(android.R.string.ok, null);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    changeVisibillity();

                } else {
                    //Logs in
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if ( e == null){
                                //Succes !
                                Intent goBackIntent = new Intent(LoginActivity.this, MainActivity.class);
                                goBackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                goBackIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(goBackIntent);
                                 Toast.makeText(LoginActivity.this, "You are logged in!", Toast.LENGTH_LONG).show();
                            } else {
                                //Login Error
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(R.string.signup_login_errormessage)
                                        .setTitle(R.string.error_title)
                                        .setPositiveButton(android.R.string.ok, null);

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                changeVisibillity();
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
            mLoginButton.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mLoginButton.setVisibility(View.VISIBLE);
        }
    }
}
