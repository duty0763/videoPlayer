package com.example.videoplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LocalVideoFragment extends Fragment {

    private List<LocalVideoBean> localVideoList = new ArrayList<>();
    private View view;
    private LocalVideoAdapter localVideoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.local_video_fragment, container, false);
        this.view=view;
        initView();

        return view;
    }

    private void initView() {
        RecyclerView videoListView = view.findViewById(R.id.local_video_view);
        localVideoList = Util.getLocalAllVideo(this.getActivity());
        localVideoAdapter = new LocalVideoAdapter(this.getActivity(), localVideoList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        videoListView.setLayoutManager(layoutManager);
        videoListView.setAdapter(localVideoAdapter);

        localVideoAdapter.setOnItemClickListener(new LocalVideoAdapter.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(View v) {
                Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
                intent.putExtra("uri",v.getTag().toString());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
    }

}
