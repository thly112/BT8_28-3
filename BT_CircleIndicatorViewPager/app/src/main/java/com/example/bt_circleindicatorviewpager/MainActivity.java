package com.example.bt_circleindicatorviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.bt_circleindicatorviewpager.adapter.ImagesViewPagerAdapter;
import com.example.bt_circleindicatorviewpager.api.APIService;
import com.example.bt_circleindicatorviewpager.api.RetrofitClient;
import com.example.bt_circleindicatorviewpager.model.ImagesSlider;
import com.example.bt_circleindicatorviewpager.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<ImagesSlider> imagesSliderList;
    private APIService apiService;
    private ViewPager viewPager;
    private ImagesViewPagerAdapter adapter;
    private CircleIndicator circleIndicator;
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager.getCurrentItem() == imagesSliderList.size() - 1) {
                viewPager.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
            handler.postDelayed(this, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);
        imagesSliderList = new ArrayList<>(); // Khởi tạo danh sách rỗng
        adapter = new ImagesViewPagerAdapter(imagesSliderList);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);

        getImages(); // Gọi API để lấy dữ liệu
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void getImages() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<MessageModel> call = apiService.LoadImageSlider(1);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.body() != null && response.body().getResult() != null) {
                    imagesSliderList.clear();
                    imagesSliderList.addAll(response.body().getResult());
                    adapter.notifyDataSetChanged();
                    circleIndicator.setViewPager(viewPager);
                    handler.postDelayed(runnable, 3000);
                    Toast.makeText(MainActivity.this, "Dữ liệu tải thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}