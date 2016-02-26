package com.hasbrain.chooseyourcar;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hasbrain.chooseyourcar.model.Car;
import com.hasbrain.chooseyourcar.model.CarDetailFragment;
import com.hasbrain.chooseyourcar.model.CarManager;
import com.hasbrain.chooseyourcar.model.LoopViewPager;

import java.util.List;


public class CarDetailActivity extends AppCompatActivity {
    private List<Car> mCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager pager = new LoopViewPager(this);
        pager.setId(R.id.viewPaper);
        setContentView(pager);

        mCars = CarManager.get(this).getCars();

        //dùng cho loop viewpager
        Car extraFirstCar = CarManager.get(this).getCar(mCars.size() - 2);
        Car extraSecondCar = CarManager.get(this).getCar(mCars.size() - 1);

        mCars.add(extraFirstCar);
        mCars.add(extraSecondCar);

        int pos = getIntent().getIntExtra(CarDetailFragment.EXTRA_CAR_POSITION, 0);
        FragmentManager manager = getSupportFragmentManager();

        pager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                position = LoopViewPager.toRealPosition(position, getCount());
                return CarDetailFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return mCars.size() - 2;
            }
        });
        pager.setCurrentItem(pos);

        //dùng cho show 1 phần part
        pager.setClipToPadding(false);
        pager.setPadding(100, 0, 100, 0);
        pager.setPageMargin(20);
    }
}
