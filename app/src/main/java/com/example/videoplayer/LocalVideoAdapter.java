package com.example.videoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.ViewHolder> implements View.OnClickListener {

    private List<LocalVideoBean> mLocalVideoList;
    private Context mContext;


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView videoSize;
        TextView videoTitle;
        TextView videoDuration;
        ImageView videoPic;

        public ViewHolder(View view) {
            super(view);
            videoSize = view.findViewById(R.id.videoSize);
            videoTitle = view.findViewById(R.id.videoTitle);
            videoDuration = view.findViewById(R.id.videoDuration);
            videoPic = view.findViewById(R.id.videoPic);

            view.setOnClickListener(LocalVideoAdapter.this);//设置监听器
        }

    }

    public LocalVideoAdapter(Context context, List<LocalVideoBean> localVideoList) {
        this.mContext = context;
        mLocalVideoList = localVideoList;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LocalVideoBean video = mLocalVideoList.get(position);
        holder.videoSize.setText(""+video.getSize());
        holder.videoTitle.setText(video.getTitle());
        holder.videoDuration.setText(""+video.getDuration());
        holder.videoPic.setImageBitmap(getVideoThumbnail(video.getData()));
        holder.itemView.setTag(video.getData());
    }

    @Override
    public int getItemCount() {
        return mLocalVideoList.size();
    }


    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v);

        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {

        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v);
        }
    }

    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media =new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }
}
