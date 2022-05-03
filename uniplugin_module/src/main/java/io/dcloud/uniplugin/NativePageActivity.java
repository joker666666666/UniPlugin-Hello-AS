package io.dcloud.uniplugin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.CheckPermissionsActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

import io.dcloud.uniplugin.util.Address;
import uni.dcloud.io.uniplugin_module.R;


public class NativePageActivity extends CheckPermissionsActivity implements INaviInfoCallback {
    private Button btn1;
    private Button btn2;
    LatLng p1 = new LatLng(39.993266, 116.473193);//首开广场
    LatLng p2 = new LatLng(39.917337, 116.397056);//故宫博物院
    LatLng p3 = new LatLng(39.904556, 116.427231);//北京站
    LatLng p4 = new LatLng(39.773801, 116.368984);//新三余公园(南5环)
    LatLng p5 = new LatLng(40.041986, 116.414496);//立水桥(北5环)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        privacyCompliance();
        setContentView(R.layout.test);
        //FrameLayout rootView = new FrameLayout(this);
        //TextView textView = new TextView(this);
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(30);
        textView.setText("我是原生界面 点击我将返回 并携带参数返回");
        //rootView.addView(textView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("respond", "我是原生页面");
                setResult(TestModule.REQUEST_CODE, intent);
                finish();
            }
        });
        btn1 = findViewById(R.id.button);
        // 监听点击事件
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到另一个名为ButtonActivity的界面
                Intent intent=new Intent(NativePageActivity.this,ButtonActivity.class);
                startActivity(intent);
            }
        });
        //setContentView(rootView);
        // 点击跳转到导航页面
        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();

                String name=intent.getStringExtra("data");
                Address start = (Address)intent.getSerializableExtra("start");
                Address end = (Address)intent.getSerializableExtra("end");

                LatLng p1 = new LatLng(start.getLatitude(), start.getLongitude());
                LatLng p2 = new LatLng(end.getLatitude(), end.getLongitude());

                Poi poi1 = new Poi(start.getName(), p1, "");
                Poi poi2 = new Poi(end.getName(), p2, "");

                AmapNaviParams params = new AmapNaviParams(poi2, null, poi1, AmapNaviType.DRIVER);
                params.setUseInnerVoice(true);
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, NativePageActivity.this);
            }
        });
    }
    private void privacyCompliance() {
        MapsInitializer.updatePrivacyShow(NativePageActivity.this,true,true);
        SpannableStringBuilder spannable = new SpannableStringBuilder("\"亲，感谢您对XXX一直以来的信任！我们依据最新的监管要求更新了XXX《隐私权政策》，特向您说明如下\n1.为向您提供交易相关基本功能，我们会收集、使用必要的信息；\n2.基于您的明示授权，我们可能会获取您的位置（为您提供附近的商品、店铺及优惠资讯等）等信息，您有权拒绝或取消授权；\n3.我们会采取业界先进的安全措施保护您的信息安全；\n4.未经您同意，我们不会从第三方处获取、共享或向提供您的信息；\n");
        spannable.setSpan(new ForegroundColorSpan(Color.BLUE), 35, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        new AlertDialog.Builder(this)
                .setTitle("温馨提示(隐私合规示例)")
                .setMessage(spannable)
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MapsInitializer.updatePrivacyAgree(NativePageActivity.this,true);
                    }
                })
                .setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MapsInitializer.updatePrivacyAgree(NativePageActivity.this,false);
                    }
                })
                .show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);// 退出程序
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        //返回null则不显示自定义区域
        return getCustomView("底部自定义区域");
    }

    @Override
    public View getCustomNaviView() {
        //返回null则不显示自定义区域
        return getCustomView("中部自定义区域");
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public View getCustomMiddleView() {
        return null;
    }

    @Override
    public void onNaviDirectionChanged(int i) {

    }

    @Override
    public void onDayAndNightModeChanged(int i) {

    }

    @Override
    public void onBroadcastModeChanged(int i) {

    }

    @Override
    public void onScaleAutoChanged(boolean b) {

    }

    TextView text1;
    TextView text2;
    private View getCustomView(String title) {
        LinearLayout linearLayout = new LinearLayout(this);
        try {
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            text1 = new TextView(this);
            text1.setGravity(Gravity.CENTER);
            text1.setHeight(90);
            text1.setMinWidth(300);
            text1.setText(title);

            text2 = new TextView(this);
            text2.setGravity(Gravity.CENTER);
            text1.setHeight(90);
            text2.setMinWidth(300);
            text2.setText(title);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.addView(text1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(text2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = 100;
            linearLayout.setLayoutParams(params);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return linearLayout;
    }
}
