package com.example.drum.hulixia.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.data.main_data.entity.Mp3Info;
import com.example.drum.hulixia.util.MediaUtil;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hulixia on 2016/7/14.
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private List<Mp3Info> mp3Infos;
    private LayoutInflater inflater;
    private Context context;
    private PlayClickListener clickListener;

    public MusicAdapter(List<Mp3Info> mp3Infos, Context context) {
        this.mp3Infos = mp3Infos;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public void setPlayClickListener(PlayClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.music_item, parent, false);
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicHolder holder, final int position) {
        final Mp3Info info = mp3Infos.get(position);
        holder.tvName.setText(info.getTitle());
        holder.tvArt.setText(info.getArtist());
        MediaUtil.setArtwork(context, info.getUrl(), holder.ivCover);
        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.playClick(position);
            }
        });
    }

  public   interface PlayClickListener {
        void playClick(int  position);
    }

    @Override
    public int getItemCount() {
        return mp3Infos.size();
    }

    class MusicHolder extends RecyclerView.ViewHolder {

        private ImageView ivCover;
        private TextView tvName;
        private TextView tvArt;
        private TextView tvTime;
        private ImageView ivPlay;

        public MusicHolder(View itemView) {
            super(itemView);
            ivCover = (ImageView) itemView.findViewById(R.id.iv_music_item_pic);
            tvName = (TextView) itemView.findViewById(R.id.tv_music_item_name);
            tvArt = (TextView) itemView.findViewById(R.id.tv_music_item_art);
            tvTime = (TextView) itemView.findViewById(R.id.tv_music_item_time);
            ivPlay = (ImageView) itemView.findViewById(R.id.iv_music_item_play);
        }
    }
}
