package co.kr.newp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Activity_map extends FragmentActivity {
    // 맵관련 화면

    public WebView mWebView;
    SharedPreferencesUtil spu;
    Spinner mapSpinner;
    Spinner map_country_Spinner;
    Spinner map_color_Spinner;
    Context context;
    Button confirmbtn;
    List<String> country_korea_list;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        context = this;
        country_korea_list = Arrays.asList(getResources().getStringArray(R.array.country_korea_code));

        mapSpinner = (Spinner)findViewById(R.id.map_spinner);
        ArrayAdapter mapAdapter = ArrayAdapter.createFromResource(this,
                R.array.country, android.R.layout.simple_spinner_item);
        mapAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mapSpinner.setAdapter(mapAdapter);
        mapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mapSpinner.getSelectedItem().toString().equals("World")) {
                    handler.sendEmptyMessage(0);
                }else if(mapSpinner.getSelectedItem().toString().equals("Korea")){
                    handler.sendEmptyMessage(1);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        map_country_Spinner = (Spinner)findViewById(R.id.map_country_spinner);
        ArrayAdapter map_country_Adapter = ArrayAdapter.createFromResource(this,
                R.array.country_world, android.R.layout.simple_spinner_item);
        map_country_Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        map_country_Spinner.setAdapter(map_country_Adapter);

        map_color_Spinner = (Spinner)findViewById(R.id.map_color_spinner);
        ArrayAdapter map_color_Adapter = ArrayAdapter.createFromResource(this,
                R.array.country_color, android.R.layout.simple_spinner_item);
        map_color_Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        map_color_Spinner.setAdapter(map_color_Adapter);

        confirmbtn = (Button)findViewById(R.id.map_confirm);
        confirmbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(2);
            }
        });

        spu=new SharedPreferencesUtil(getApplicationContext());
        mWebView = (WebView)findViewById(R.id.map_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setHorizontalScrollBarEnabled(false);

        mWebView.setVerticalScrollBarEnabled(false);
        //To disabled the horizontal and vertical scrolling:
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        mWebView.loadUrl("http://sogo4070.dothome.co.kr/geochart_korea.php?id="+spu.getValue("id", ""));

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
    }

    final Handler handler = new Handler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ArrayAdapter map_country_Adapter1 = ArrayAdapter.createFromResource(context,
                            R.array.country_world, android.R.layout.simple_spinner_item);
                    map_country_Adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    map_country_Spinner.setAdapter(map_country_Adapter1);
                    mWebView.loadUrl("http://sogo4070.dothome.co.kr/geochart_world.php?id="+spu.getValue("id", ""));
                    break;
                case 1:
                    ArrayAdapter map_country_Adapter2 = ArrayAdapter.createFromResource(context,
                            R.array.country_korea, android.R.layout.simple_spinner_item);
                    map_country_Adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    map_country_Spinner.setAdapter(map_country_Adapter2);
                    mWebView.loadUrl("http://sogo4070.dothome.co.kr/geochart_korea.php?id="+spu.getValue("id", ""));
                    break;
                case 2:
                    String kind = "";
                    String country_name = "";
                    String country_code = "";
                    String country_value = "";
                    if(mapSpinner.getSelectedItem().toString().equals("World")) {
                        kind = "1";
                        country_name = map_country_Spinner.getSelectedItem().toString();
                        country_code = country_name;
                    }else {
                        kind = "2";
                        country_name = map_country_Spinner.getSelectedItem().toString();
                        country_code = country_korea_list.get(map_country_Spinner.getSelectedItemPosition());
                    }
                    switch (map_color_Spinner.getSelectedItemPosition()) {
                        case 0:
                            country_value = "0";
                            break;
                        case 1:
                            country_value = "10";
                            break;
                        case 2:
                            country_value = "20";
                            break;
                        case 3:
                            country_value = "30";
                            break;
                        case 4:
                            country_value = "40";
                            break;
                        case 5:
                            country_value = "50";
                            break;
                        case 6:
                            country_value = "60";
                            break;
                        case 7:
                            country_value = "1000";
                            break;
                        default:
                            break;
                    }
                    mWebView.loadUrl("http://sogo4070.dothome.co.kr/add_map.php?id="+spu.getValue("id", "")+"&kind="+kind+"&country_name="+country_name+"&country_code="+country_code+"&country_value="+country_value);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

    }
}