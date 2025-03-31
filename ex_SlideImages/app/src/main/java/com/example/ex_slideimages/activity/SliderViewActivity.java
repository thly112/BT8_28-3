package com.example.ex_slideimages.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ex_slideimages.adapter.SliderAdapter;
import com.example.ex_slideimages.R;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import java.util.ArrayList;

public class SliderViewActivity extends AppCompatActivity {
    private SliderView sliderView;
    private ArrayList<Integer> arrayList;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sliderview);

        sliderView = findViewById(R.id.imageSlider);
        arrayList = new ArrayList<>();

        // Thêm ảnh vào danh sách
        arrayList.add(R.drawable.coffee);
        arrayList.add(R.drawable.pizza);
        arrayList.add(R.drawable.quangcao);

        sliderAdapter = new SliderAdapter(getApplicationContext(), arrayList);
        sliderView.setSliderAdapter(sliderAdapter);

        // Cấu hình sliderView
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(getResources().getColor(R.color.red));
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(5);
    }
}
