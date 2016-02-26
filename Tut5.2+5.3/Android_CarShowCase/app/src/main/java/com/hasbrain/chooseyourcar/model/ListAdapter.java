package com.hasbrain.chooseyourcar.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasbrain.chooseyourcar.CarDetailActivity;
import com.hasbrain.chooseyourcar.CarListActivity;
import com.hasbrain.chooseyourcar.R;

import java.util.List;

/**
 * Created by hao on 2/22/2016.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Car> mCars;
    private Context mContext;

    public ListAdapter(Context context, List<Car> cars) {
        mContext = context;
        mCars = cars;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_list_car, parent, false);
        return new CarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CarViewHolder viewHolder = (CarViewHolder) holder;
        viewHolder.bind(mCars.get(position));
    }

    @Override
    public int getItemCount() {
        return mCars.size();
    }

    private class CarViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public CarViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view_list_car);
            mTextView = (TextView) itemView.findViewById(R.id.text_view_list_car);
        }

        public void bind(Car car) {

            CarListActivity.sImageLoader.queueThumbnail(mImageView, car.getImageUrl(), new ImageInfo(car.getId(), false));
            mTextView.setText(car.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CarDetailActivity.class);
                    intent.putExtra(CarDetailFragment.EXTRA_CAR_POSITION, getAdapterPosition());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
