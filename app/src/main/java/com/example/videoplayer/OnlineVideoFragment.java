package com.example.videoplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OnlineVideoFragment extends Fragment {

    private List<OnlineVideoBean> onlineVideoList = new ArrayList<>();
    private View view;
    private OnlineVideoAdapter onlineVideoAdapter;
    private int num=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.online_video_fragment, container, false);
        this.view = view;
        initOnlineVideo(num);
        initView();

        return view;
    }

    private void initView() {
        final RecyclerView videoListView =view.findViewById(R.id.online_video_view);
        final SwipeRefreshLayout videoSwipe = view.findViewById(R.id.online_video_swipe);

        onlineVideoAdapter = new OnlineVideoAdapter(this.getActivity(), onlineVideoList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        videoListView.setLayoutManager(layoutManager);
        videoListView.setAdapter(onlineVideoAdapter);

        videoListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //这个方法会执行3次
//                06-20 00:05:30.781 15938-15938/com.example.videoplayer D/TAG: onScrollStateChanged: 1
//                06-20 00:05:30.828 15938-15938/com.example.videoplayer D/TAG: onScrollStateChanged: 2
//                06-20 00:05:30.847 15938-15938/com.example.videoplayer D/TAG: onScrollStateChanged: 0

                if(newState==0&&isVisBottom(recyclerView)){
                    Log.d("TAG", "onScrollStateChanged: =============到达底部");
                    num=num+1;
                    initOnlineVideo(num);
                    onlineVideoAdapter.notifyDataSetChanged();
                }
            }

        });


        videoSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟网络请求需要3000毫秒，请求完成，设置setRefreshing 为false
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        videoSwipe.setRefreshing(false);
                    }
                }, 2000);
            }
    });


        onlineVideoAdapter.setOnItemClickListener(new OnlineVideoAdapter.OnItemClickListener() {
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

    public static boolean isVisBottom(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前 RecyclerView 的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView 的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }


    public void initOnlineVideo(final int num) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestBody formBody = new FormBody.Builder()
                            .add("page", String.valueOf(num))
                            .add("count", "13")
                            .add("type", "video")
                            .build();

                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(" https://api.apiopen.top/getJoke")
                            .removeHeader("User-Agent")//移除旧的
                            .post(formBody)
                            .addHeader("User-Agent", "Mozillo/5.0")
                            .build();
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        String data = response.body().string();
                        JSONObject jsonObject = new JSONObject(data);
                        JSONArray result = jsonObject.getJSONArray("result");

                        for (int i = 0; i < result.length(); i++) {
                            OnlineVideoBean videoBean = new OnlineVideoBean();
                            videoBean.setText(result.getJSONObject(i).getString("text"));
                            videoBean.setThumbnail(result.getJSONObject(i).getString("thumbnail"));
                            videoBean.setVideo(result.getJSONObject(i).getString("video"));
                            onlineVideoList.add(videoBean);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}