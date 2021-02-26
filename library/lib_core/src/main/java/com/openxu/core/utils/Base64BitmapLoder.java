package com.openxu.core.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * autour : openXu
 * date : 2017/8/1 16:23
 * className : Base64BitmapLoder
 * version : 1.0
 * description : base64编码图片加载器
 */
public class Base64BitmapLoder {

    private String TAG = "Base64BitmapLoder";

    private ExecutorService bitmapLoadThreadPool;
    private Activity activity;

    private static Base64BitmapLoder instance;
    private Base64BitmapLoder(Activity activity){
        this.activity = activity;
        bitmapLoadThreadPool = Executors.newFixedThreadPool(3);
    }

    public static Base64BitmapLoder getInstance(Activity activity){

        if(instance==null)
            instance = new Base64BitmapLoder(activity);
        return instance;
    }

    public void loadBitmap(String tagID, String base64Str, ImageView imageView){
        BitmapDecodeRunnable runnable = new BitmapDecodeRunnable(tagID, base64Str, imageView);
        bitmapLoadThreadPool.execute(runnable);
    }

    public Bitmap getBitmap(String base64Str){
        if(TextUtils.isEmpty(base64Str))
            return null;
        byte[] logobytes = Base64.decode(base64Str.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(logobytes, 0, logobytes.length);
        return bitmap;
    }
    class BitmapDecodeRunnable implements Runnable {

        public BitmapDecodeRunnable(String tagID, String base64Str, ImageView imageView) {
            this.tagID = tagID;
            this.base64Str = base64Str;
            this.imageView = imageView;
        }
        private String tagID;
        private ImageView imageView;
        private String base64Str;
        @Override
        public void run() {
            try {
                FLog.i(TAG, "logo icon = "+base64Str);
                byte[] logobytes = Base64.decode(base64Str.getBytes(), Base64.DEFAULT);
                FLog.i(TAG, "Base64编码:"+logobytes.length);
                Bitmap bitmap = BitmapFactory.decodeByteArray(logobytes, 0, logobytes.length);
                FLog.i(TAG, "bitmap:"+bitmap);
                if(bitmap!=null && (imageView.getTag()==null || imageView.getTag().equals(tagID))){
                    activity.runOnUiThread(()-> imageView.setImageBitmap(bitmap));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
