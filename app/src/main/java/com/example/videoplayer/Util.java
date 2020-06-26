package com.example.videoplayer;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.format.DateFormat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Util {

    public static List<LocalVideoBean> getLocalAllVideo(Context context) {

        List<LocalVideoBean> LocalVideoList = new ArrayList<>();

        // 视频其他信息的查询条件
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.TITLE};

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, MediaStore.Video.VideoColumns.DATE_ADDED + " DESC");

        if (cursor == null) {
            return LocalVideoList;
        }
        if (cursor.moveToFirst()) {
            do {
                LocalVideoBean video = new LocalVideoBean();

                video.setData(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                String duration =formatMillis(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))).toString();
                video.setDuration(duration);
                String size =formSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));
                video.setSize(size);
                video.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));

                LocalVideoList.add(video);
            } while (cursor.moveToNext());

        }
        cursor.close();

        return LocalVideoList;

    }
    public static CharSequence formatMillis(long duration) {
//		把duration转换为一个日期
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.add(Calendar.MILLISECOND, (int) duration);
        CharSequence inFormat = duration / 60*60*1000 >1 ? "kk:mm:ss":"mm:ss";
        return DateFormat.format(inFormat, calendar.getTime());
    }



    public static String formSize(long size) {
        //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }


}
