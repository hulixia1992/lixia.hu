package com.example.drum.hulixia.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.data.main_data.entity.SanCanItem;
import com.example.drum.hulixia.util.image_cache.ImageDownloadUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by hulixia on 2016/7/6.
 * 美食数据适配器
 */
public class FoodsDataAdapter extends RecyclerView.Adapter<FoodsDataAdapter.FoodsViewHolder> {
    private ArrayList<SanCanItem> sanCanItems;
    private LayoutInflater inflater;
    private PictureClickEvent pictureClickEvent;

    public FoodsDataAdapter(ArrayList<SanCanItem> sanCanItems, Context context, PictureClickEvent pictureClickEvent) {
        this.sanCanItems = sanCanItems;
        inflater = LayoutInflater.from(context);
        this.pictureClickEvent = pictureClickEvent;
    }

    @Override
    public FoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.foods_item, null);
        return new FoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodsViewHolder holder, final int position) {
        SanCanItem sanCanItem = sanCanItems.get(position);
        holder.tvTitle.setText(sanCanItem.title);
        holder.tvHeart.setText(sanCanItem.fav_num);
        holder.tvDesc.setText(sanCanItem.descr);
     //   holder.sdvImage.setImageURI(Uri.parse(sanCanItem.tj_img));
        ImageDownloadUtils.getInstance().getBitmapToIV(sanCanItem.tj_img,holder.sdvImage);
        holder.sdvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureClickEvent.onClick(position, view);
            }
        });
    }


    @Override
    public int getItemCount() {
        return sanCanItems.size();
    }

    class FoodsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDesc;
        public TextView tvHeart;
        public SimpleDraweeView sdvImage;

        public FoodsViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_foods_item_title);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_foods_item_desc);
            tvHeart = (TextView) itemView.findViewById(R.id.tv_foods_item_heart);
            sdvImage = (SimpleDraweeView) itemView.findViewById(R.id.sdv_foods_item);
        }
    }

    public interface PictureClickEvent {
        void onClick(int position, View view);
    }
}
