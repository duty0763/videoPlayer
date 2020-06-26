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

import com.bumptech.glide.Glide;

import java.util.List;

public class OnlineVideoAdapter extends RecyclerView.Adapter<OnlineVideoAdapter.ViewHolder> implements View.OnClickListener {

    private List<OnlineVideoBean> mOnlineVideoList;
    private Context mContext;


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView videoTitle;
        ImageView videoPic;

        public ViewHolder(View view) {
            super(view);
            videoTitle = view.findViewById(R.id.videoTitle);
            videoPic = view.findViewById(R.id.videoPic);

            view.setOnClickListener(OnlineVideoAdapter.this);//设置监听器
        }

    }

    public OnlineVideoAdapter(Context context, List<OnlineVideoBean> onlineVideoList) {
        this.mContext = context;
        mOnlineVideoList = onlineVideoList;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        OnlineVideoBean video = mOnlineVideoList.get(position);
        holder.videoTitle.setText(video.getText());
        Glide.with(mContext).load(video.getThumbnail()).into(holder.videoPic);
        holder.itemView.setTag(video.getVideo());
    }

    @Override
    public int getItemCount() {
        return mOnlineVideoList.size();
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
}
