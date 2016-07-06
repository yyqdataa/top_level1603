package com.phone1000.app.okhttplibrary;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/28.
 */
public class OkHttpTool {

    private static OkHttpClient okHttpClient ;
    public static OkHttpAsynTask newInstance(){
        if (okHttpClient==null){
            okHttpClient = new OkHttpClient();
        }
        return new OkHttpAsynTask();
    }

    public static class OkHttpAsynTask extends AsyncTask<String,Void,String>{
        public static final String URL_REGEX = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
        private IOKCallBack okCallBack;
        private FormBody formBody;

        public void callback(IOKCallBack okCallBack){
            this.okCallBack = okCallBack;
        }
        public OkHttpAsynTask post(Map<String,String> map){
            Set<String> keySet = map.keySet();
            FormBody.Builder builder = new FormBody.Builder();
            for (String key:keySet){
                String value = map.get(key);
                builder.add(key,value);
            }
            formBody= builder.build();
            return this;
        }

        public OkHttpAsynTask start(String url){
            //判断URL是否正确，使用正则表达式

            if (url == null) {
                throw new NullPointerException("url is null");
            }

            if (!url.matches(URL_REGEX)) {
                throw new IllegalArgumentException("url is error");
            }

            this.execute(url);
            return this;
        }
        @Override
        protected String doInBackground(String... params) {
           // Request request = new Request.Builder().url(params[0]).get().build();

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            if (formBody!=null){
                builder.post(formBody);
            }
            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (okCallBack!=null){
                okCallBack.success(s);
            }
        }
    }
}
