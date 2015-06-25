package phonezilla.dev01_04_practicum;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Dhiraj on 24-6-2015.
 */
public class PhonezillaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Y2POF6SqgY49TKpNqCjUufDjFtFHdstoxpU7fxp2", "ywSkE4jG2yeaZ6orgqjd0KmtDiELCHYeMipERgML");


      }
}
