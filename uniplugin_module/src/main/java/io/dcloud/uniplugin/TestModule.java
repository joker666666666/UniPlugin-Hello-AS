package io.dcloud.uniplugin;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;
import io.dcloud.uniplugin.util.Address;


public class TestModule extends UniModule {

    String TAG = "TestModule";
    public static int REQUEST_CODE = 1000;

    //run ui thread
    @UniJSMethod(uiThread = true)
    public void testAsyncFunc(JSONObject options, UniJSCallback callback) {
        Log.e(TAG, "testAsyncFunc--"+options);
        if(callback != null) {
            JSONObject data = new JSONObject();
            data.put("code", "success");
            callback.invoke(data);
            //callback.invokeAndKeepAlive(data);
        }
    }

    //run JS thread
    @UniJSMethod (uiThread = false)
    public JSONObject testSyncFunc(JSONObject options){
        Log.e(TAG, "testAsyncFunc--"+options);
        JSONObject data = new JSONObject();
        data.put("code", "success");
        return data;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && data.hasExtra("respond")) {
            Log.e("TestModule", "原生页面返回----"+data.getStringExtra("respond"));
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @UniJSMethod (uiThread = true)
    public void gotoNativePage(JSONObject options){
        if(mUniSDKInstance != null && mUniSDKInstance.getContext() instanceof Activity) {
            Log.e(TAG, "gotoNativePage--"+options);
            JSONObject _start = (JSONObject) options.get("start");
            JSONObject _end = (JSONObject) options.get("end");

            Address start = new Address(_start.getString("name"), _start.getDoubleValue("longitude"), _start.getDoubleValue("latitude"));
            Address end = new Address(_end.getString("name"), _end.getDoubleValue("longitude"), _end.getDoubleValue("latitude"));

            Intent intent = new Intent(mUniSDKInstance.getContext(), NativePageActivity.class);
            intent.putExtra("start", start);
            intent.putExtra("end", end);
            ((Activity)mUniSDKInstance.getContext()).startActivityForResult(intent, REQUEST_CODE);

        }
    }
}
