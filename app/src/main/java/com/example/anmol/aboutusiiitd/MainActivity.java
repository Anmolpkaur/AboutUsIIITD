package com.example.anmol.aboutusiiitd;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    private static final String DEBUG_TAG = "HttpExample";
    private EditText urlText;
    private TextView textView;
    private TextView title_textView;
    private String heading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.myText);
        title_textView = (TextView) findViewById(R.id.title_tv);
    }

    public void DownloadClick(View view) {

        String url = "https://www.iiitd.ac.in/about";
        ConnectivityManager conn = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(url);
        } else {
            textView.setText("Network Unavailable");
        }
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                Document doc = Jsoup.connect("https://www.iiitd.ac.in/about").get();
                heading=doc.title();
                String content = doc.text();
                return content;
               // title_textView.setText(heading);


            } catch (Exception e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
            title_textView.setText(heading);
        }
    }
}

