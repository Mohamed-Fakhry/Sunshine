package com.example.eastsound.sunshine;

import android.app.Activity;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
//        old model
//        addPreferencesFromResource(R.xml.pref_general);
//        bindPreferanceSumamryToValue(findPreference(getString(R.string.pref_location_key)));
    }

    public static class SettingsFragment extends PreferenceFragment
                        implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_general);

            bindPreferanceSumamryToValue();
        }


        private void bindPreferanceSumamryToValue() {
            findPreference(getString(R.string.pref_location_key))
                    .setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity())
                            .getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default)));

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex =listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntry());
                }
            } else {
                preference.setSummary(stringValue);
            }

            return false;
        }

    }

}
