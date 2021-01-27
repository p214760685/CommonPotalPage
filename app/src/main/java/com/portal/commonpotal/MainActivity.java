package com.portal.commonpotal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;

import com.portal.common.PtrClassicRefreshLayout;
import com.portal.common.PtrDefaultHandler;
import com.portal.common.PtrFrameLayout;
import com.portal.common.activity.CaptureActivity;
import com.portal.common.activity.WebViewActivity;
import com.portal.common.contact.view.PinyinUtils;
import com.portal.common.util.ImageUtil;
import com.portal.common.util.UtilLog;
import com.portal.common.util.UtilToast;
import com.portal.common.view.SwitchButton;
import com.portal.common.view.WImageView;


public class MainActivity extends Activity {

    private PtrClassicRefreshLayout mPtrFrame ;
    Vibrator vibrator ;
    private SwitchButton switch_button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
        switch_button =findViewById(R.id.switch_button);

        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                UtilToast.show(MainActivity.this,PinyinUtils.getPingYin("毛泽东"));

            }
        });
        findViewById(R.id.scan_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url","https://www.baidu.com");
                startActivity(intent);

//                Intent intent = new Intent(MainActivity.this,CaptureActivity.class);
//                startActivityForResult(intent, 10005);
//                SlbLoginAuth.LoginAuthToSLB(MainActivity.this,"123456","15526453981","com.portal.commonpotal","com.portal.commonpotal.MainActivity",1);
//                ComponentName comp = new ComponentName("com.yucheng.mobile.wportal","com.yucheng.mobile.wportal.third.T01_LoginActivity");
//                Intent it=new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("key", "SLB123487654");
//                it.putExtras(bundle);
//                it.setComponent(comp);
//                startActivityForResult(it,1);
//                startActivityForResult(new Intent(MainActivity.this,
//                        CaptureActivity.class), 2000);
//                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 10005);
            }
        });

//        WImageView img  =   findViewById(R.id.mk_goods_img);
//        ImageUtil.drawImageFromUri("https://test.shelongwang.com:8879/upfiles/titlepic/583407c989c57_270X200.jpg",img);

        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url","https://www.baidu.com");
                intent.putExtra("title","百度搜索");
                startActivity(intent);
//                startActivityForResult(new Intent(MainActivity.this,
//                        CaptureActivity.class), 2000);
            }
        });

        mPtrFrame = findViewById(R.id.test_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {//单纯下拉刷新
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                        vibrator.vibrate(100);
                    }
                },3000);
            }

        });
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        for (int i = 0; i < 2; i++) {
            UtilToast.show(MainActivity.this,PinyinUtils.getPingYin("毛泽东"));
        }
    }

//    private void setWritePerimission(){
//        AndPermission.with(this)
//                .permission(
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.CAMERA
//                )
//                .start();
//    }


//    //此方法中可以接收另一个app回传回来的数据
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if( resultCode ==  RESULT_OK && requestCode == SlbLoginAuth.RESULT_SLBCODE){
//            String return_data=data.getStringExtra("return_data");
//            UtilToast.show(MainActivity.this,return_data);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }


    // 回调扫描
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//
//            if (requestCode == 2000) {
//                String barCodeStr = data.getStringExtra("result");
//                UtilToast.show(MainActivity.this,barCodeStr);
//            }else if(requestCode == 10005){
//                UtilLog.shownNet("111111111111",true);
//                if (null != data) {
//                    UtilLog.shownNet("222222222",true);
//                                 Uri selectedImage = data.getData();
//                              String[] filePathColumns = {MediaStore.Images.Media.DATA};
//                                   String imagePath;
//                                   Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
//                                   if (c != null) {
//                                           c.moveToFirst();
//                                           int columnIndex = c.getColumnIndex(filePathColumns[0]);
//                                           imagePath = c.getString(columnIndex);
//                                            c.close();
//                                       UtilLog.shownNet("imagePath=="+ imagePath,true);
//                                   final Bitmap bm = BitmapFactory.decodeFile(imagePath);
//                                       UtilLog.shownNet("bm=="+ bm,true);
//                                       new AsyncTask<Void, Void, String>() {
//                                           @Override
//                                           protected String doInBackground(Void... params) {
//                                               return QRCodeDecoder.syncDecodeQRCode(bm);
//                                           }
//
//                                           @Override
//                                           protected void onPostExecute(String result) {
//                                               if (TextUtils.isEmpty(result)) {
//                                                   UtilToast.show(MainActivity.this, "二维码解析失败");
//                                               } else {
//                                                   UtilToast.show(MainActivity.this,"result==" +  result);
//                                               }
//                                           }
//                                       }.execute();
//                                       } else {
//                                       UtilToast.show(this, "图片路径为空");
//                                      }
//                            }
//            }
//
//        }
//
//    }

}
