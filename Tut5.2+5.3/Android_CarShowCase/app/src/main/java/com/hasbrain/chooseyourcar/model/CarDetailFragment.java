package com.hasbrain.chooseyourcar.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasbrain.chooseyourcar.CarListActivity;
import com.hasbrain.chooseyourcar.R;

/**
 * Created by hao on 2/23/2016.
 */
public class CarDetailFragment extends Fragment {
    public static final String EXTRA_CAR_POSITION = "car position";

    private Car mCar;

    public static CarDetailFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_CAR_POSITION, position);

        CarDetailFragment fragment = new CarDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position = getArguments().getInt(EXTRA_CAR_POSITION);
        mCar = CarManager.get(getActivity()).getCar(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceSate) {
        View v = inflater.inflate(R.layout.fragment_car_detail, parent, false);

        ImageView imageView = (ImageView) v.findViewById(R.id.image_view_car);
        CarListActivity.sImageLoader.queueThumbnail(imageView, mCar.getImageUrl(), new ImageInfo(mCar.getId(), true));

        TextView textView = (TextView) v.findViewById(R.id.text_view_car);
        textView.setText(mCar.getName());

        return v;
    }
}
