package com.guuidea.towersdk.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParser;
import com.guuidea.towersdk.TowerLogin;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 *
 */

class RealRequest {
    private static final String TAG = "RealRequest";
    private static final String BOUNDARY = java.util.UUID.randomUUID().toString();
    private static final String TWO_HYPHENS = "--";
    private static final String LINE_END = "\r\n";

    /**
     * get请求
     */
    RealResponse getData(String requestURL, Map<String, String> headerMap) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpURLConnection(requestURL, "GET");
            conn.setDoInput(true);
            if (headerMap != null) {
                setHeader(conn, headerMap);
            }
            conn.setRequestProperty("appkey", TowerLogin.getInstance().getAppKey());
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String response = getString(conn.getInputStream());
                Log.i(TAG, "postData: "+response);
                return new RealResponse(200, JsonParser.parseString(response).getAsJsonObject());
            } else {
                return new RealResponse(conn.getResponseCode(), new Throwable(conn.getResponseMessage()));
            }
        } catch (Exception e) {
            return new RealResponse(-1, new Throwable(e.getMessage()));
        }
    }

    /**
     * post请求
     */
    RealResponse postData(String requestURL, String bodyType, String body, Map<String, String> headerMap) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpURLConnection(requestURL, "POST");
            conn.setDoOutput(true);//可写出
            conn.setDoInput(true);//可读入
            conn.setUseCaches(false);
            conn.setRequestProperty("appkey", TowerLogin.getInstance().getAppKey());
            conn.setRequestProperty("authToken", "123");
            if (headerMap != null) {
                setHeader(conn, headerMap);
            }
            if (!TextUtils.isEmpty(bodyType)) {
                conn.setRequestProperty("Content-Type", bodyType);
            }
            conn.connect();// 连接，以上所有的请求配置必须在这个API调用之前
            if (!TextUtils.isEmpty(body)) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String response = getString(conn.getInputStream());
                Log.i(TAG, "postData: "+response);
                return new RealResponse(200, JsonParser.parseString(response).getAsJsonObject());
            } else {
                Log.i(TAG, "postData: "+conn.getResponseCode());
                return new RealResponse(conn.getResponseCode(), new Throwable(getString(conn.getErrorStream())));
            }
        } catch (Exception e) {
            return new RealResponse(-1, new Throwable(e.getMessage()));
        }
    }

    private String getString(InputStream in) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int length = 0;
        while (true) {
            try {
                if ((length = in.read(buffer)) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.write(buffer, 0, length);//写入输出流
        }
        try {
            in.close();//读取完毕，关闭输入流
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 根据输出流创建字符串对象
        try {
            return new String(bos.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 得到Connection对象，并进行一些设置
     */
    private HttpURLConnection getHttpURLConnection(String requestURL, String requestMethod) throws IOException {
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setReadTimeout(15 * 1000);
        conn.setRequestMethod(requestMethod);
        return conn;
    }

    /**
     * 设置请求头
     */
    private void setHeader(HttpURLConnection conn, Map<String, String> headerMap) {
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                conn.setRequestProperty(key, headerMap.get(key));
            }
        }
    }


}
