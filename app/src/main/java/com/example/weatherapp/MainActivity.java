package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weatherapp.Model.MyWeather;
import com.example.weatherapp.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Api api;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        progressDialog=new ProgressDialog(this);
//        progressDialog.setTitle("Qachongacha kutamiz");
//        progressDialog.setMessage("Kutasan kerak bo`lsa");
//        progressDialog.show();
//        progressDialog.hide();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://community-open-weather-map.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(Api.class);
        binding.imageviewsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar.setVisibility(View.VISIBLE);
                request(binding.edittext1.getText().toString());
            }
        });



    }


    public void request(String cityname){
        Call<MyWeather> call=api.getMyCityWeather(cityname);
        call.enqueue(new Callback<MyWeather>() {
            @Override
            public void onResponse(Call<MyWeather> call, Response<MyWeather> response) {
                if (response.isSuccessful()){
                    binding.progressBar.setVisibility(View.GONE);
                    MyWeather myWeather=response.body();
                    float temp= (float) (myWeather.getMain().getTemp()-273f);
                    binding.textview1.setText(temp+" C");
                    binding.textview2.setText(myWeather.getWeather().get(0).getDescription()+"");
                    binding.textview3.setText(myWeather.getWind().getSpeed()+" km/h");
                    binding.textview4.setText(myWeather.getSys().getCountry());

                    switch (myWeather.getWeather().get(0).getMain()+""){
                        case "Clear":
                            binding.imageview2.setImageResource(R.drawable.sun);
                            break;
                        case "Snow":
                            binding.imageview2.setImageResource(R.drawable.snow);
                            break;
                        case "Rain":
                            binding.imageview2.setImageResource(R.drawable.rain);
                            break;
                    }
                }else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.edittext1.setError("Not Found");

                }
            }

            @Override
            public void onFailure(Call<MyWeather> call, Throwable t) {

            }
        });
    }
}