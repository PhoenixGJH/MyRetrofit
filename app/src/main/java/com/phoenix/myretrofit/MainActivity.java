package com.phoenix.myretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String BASE_URL = "http://123.57.248.61:8089/";

    @BindView(R.id.tv_activity_main_hello)
    TextView mTV_hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_main, null);
        ButterKnife.bind(this, contentView);
        setContentView(contentView);
        mTV_hello.setText("Click");
    }

    @OnClick({R.id.tv_activity_main_hello})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_main_hello:
                init();
                break;
        }
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<City> call = service.getData();
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                Log.d(TAG, "OnResponse");
                Toast.makeText(MainActivity.this, "OnResponse", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Log.d(TAG, "OnFailure");
                Toast.makeText(MainActivity.this, "OnFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface APIService {
        @GET("app/evaluation/getcity")
        Call<City> getData();
    }
}
