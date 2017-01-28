package octacode.allblue.code.moviezz;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import octacode.allblue.code.moviezz.data.MovieContract;
import octacode.allblue.code.moviezz.fetchers.FetchMovieTask;

/**
 * Created by shasha on 23/12/16.
 */
public class SettingsActivit extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        bindPreferenceSummaryToValue(findPreference("pref_rate"));
        bindPreferenceSummaryToValue(findPreference("pref_tell"));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();
        if(preference.getKey().equals(getString(R.string.pref_sort_key))) {
            String pref = value.toString();
        }
        else
            getContentResolver().notifyChange(MovieContract.MainMovieTable.CONTENT_URI,null);

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });


        Preference rate_pref = findPreference("pref_rate");
        rate_pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(Utility.isNetworkAvailable(SettingsActivit.this))
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                else
                    Toast.makeText(SettingsActivit.this, "Network not available.",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference tell_pref = findPreference("pref_tell");
        tell_pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "The best app for movie maniacs. " +
                        "Download it from Play Store.\n"+"http://play.google.com/store/apps/details?id=" + getPackageName());
                        sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            }
        });
        Preference about_pref = findPreference("pref_about");
        about_pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(SettingsActivit.this,AboutActivity.class));
                return true;
            }
        });
        return false;
    }
}