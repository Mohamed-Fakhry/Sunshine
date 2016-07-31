package com.example.eastsound.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.ButterKnife;


public class ForecastFragment extends Fragment {

    ArrayAdapter<String> mForecastAdapter;
    //  @BindView(R.id.listview_forecast) ListView listView;

    public ForecastFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
   //    ButterKnife.bind(this, view);
        createForecastLV(view);
        return view;
    }

    private void createForecastLV(View view) {

        String[] forecastArray = getForecastArray();

        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                weekForecast);

        ListView listView = (ListView) view.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
    }

    @NonNull
    private String[] getForecastArray() {
        String[] forecastArray;
        forecastArray =  new String[]{
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/40",
                "Wed - Cloudy - 72/63",
                "Thurs - Asteroids - 75/65",
                "Fri - Heavy Rain - 65/56",
                "Sat - HELP TRAPPED IN WEATHERSTATION - 60/51",
                "Sun - Sunny - 80/68"
        };


        return forecastArray;
    }

    public class FetchWeatherTask extends AsyncTask<Void, Void, Void>{

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {
            BufferedReader reader = null;
            String forecastJsonStr = null;
            HttpsURLConnection urlConnection = null;

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7&appid=udicity58890");

                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                String line = reader.readLine();
                while(line != null) {
                    buffer.append(line + "\n");
                    line = reader.readLine();
                }

                forecastJsonStr = buffer.toString();

                reader = new BufferedReader(new InputStreamReader(inputStream));

            } catch (Exception e) {

                Log.d(LOG_TAG, "Error", e);

            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }
    }
}
