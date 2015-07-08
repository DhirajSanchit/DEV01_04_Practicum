package phonezilla.dev01_04_practicum;

import java.util.Locale;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Created by Dhiraj
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return MainActivity.PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        //one for Inbox and one for Friends.
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();

        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_inbox).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.tab_friends).toUpperCase(l);
        }
        return null;
    }

}

