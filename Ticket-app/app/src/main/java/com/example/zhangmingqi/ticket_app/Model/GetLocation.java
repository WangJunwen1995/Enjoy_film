package com.example.zhangmingqi.ticket_app.Model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.zhangmingqi.ticket_app.Controller.CurrentUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




//通过根据传感器获得的经纬度信息，发送给webservice接口，获取当前所在城市
public class GetLocation {
    public LocationManager manager;
    public LocationListener listener;
    private Context context;
    public GetLocation(Context _context) {
        context = _context;
    }
    public void initLocation() {

        // 权限检查
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "无法定位，将使用上次的位置", Toast.LENGTH_SHORT).show();
            return;
        }

        manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);     // 高精度
        criteria.setAltitudeRequired(false);              // 不要求海拔信息
        criteria.setBearingRequired(false);               // 不要求方位信息
        criteria.setCostAllowed(true);                    // 是否允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 对电量的要求

        String bestProvider = manager.getBestProvider(criteria, true);
        try {
            Location location = manager.getLastKnownLocation(bestProvider);

        if (location != null) {

            (new getCityByLocationAsyncTask()).execute(location);

        } else {

            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    (new getCityByLocationAsyncTask()).execute(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                    try {
                        Location location = manager.getLastKnownLocation(provider);
                        if (location != null)
                            (new getCityByLocationAsyncTask()).execute(location);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            try {
                manager.requestLocationUpdates(bestProvider, 1000, 0, listener);
            } catch (SecurityException e) {
            e.printStackTrace();
        }
        }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // 获取当前的城市
    class getCityByLocationAsyncTask extends AsyncTask<Location, Void, String> {

        // 使用API: http://apistore.baidu.com/apiworks/servicedetail/957.html
        @Override
        protected String doInBackground(Location... params) {
            Location location = params[0];
           // Log.i(TAG, location.toString());

            String httpUrl = "http://apis.baidu.com/wxlink/here/here";
            String httpArg = "lat=" + location.getLatitude() + "&lng=" + location.getLongitude() + "&cst=1";

            HttpURLConnection connection = null;
            try {
                URL url = new URL(httpUrl + "?" + httpArg);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(4000);
                connection.setReadTimeout(4000);
                // 填入apikey到HTTP header
                connection.setRequestProperty("apikey", "9e7d05f4f32904de94d0a0f785b8f455");
                connection.connect();

                StringBuffer buffer = new StringBuffer();
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                String strRead;
                while ((strRead = reader.readLine()) != null) {
                    buffer.append(strRead);
                    buffer.append("\r\n");
                }
                reader.close();
                String result = buffer.toString();

                JSONArray jsonArray = new JSONArray("[" + result + "]");
                JSONObject obj = jsonArray.getJSONObject(0);
                if (!obj.getString("code").equals("10000")) {
                    Toast.makeText(context, "定位查询失败，将使用上一次的定位", Toast.LENGTH_SHORT).show();
                    return null;
                }

                JSONArray resultArray = new JSONArray(obj.getString("result"));
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject job = resultArray.getJSONObject(i);
                    if (job.getString("TypeName").equals("市"))
                        return job.getString("DistrictName");
                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            if (str != null)
                CurrentUser.getInstance().location = str;

            if (listener != null)
                manager.removeUpdates(listener);
        }
    }
}
