package com.gg.umengsharedemo;

import android.Manifest;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.UMQQSsoHandler;
import com.umeng.socialize.media.UMImage;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn, btn2, btn3,btn4;
//    // 首先在您的Activity中添加如下成员变量
//    final UMSocialService mController = UMServiceFactory.getUMSocialService("com" +
//            ".umeng.share");
//// 设置分享内容
//    mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng" +
//            ".com/social");
//// 设置分享图片, 参数2为图片的url地址
//    mController.setShareMedia(new
//
//    UMImage(getActivity(),
//
//    "http://www.umeng.com/images/pic/banner_module_social.png"));
// 设置分享图片，参数2为本地图片的资源引用
//mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
// 设置分享图片，参数2为本地图片的路径(绝对路径)
//mController.setShareMedia(new UMImage(getActivity(),
//                                BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

// 设置分享音乐
//UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
//uMusic.setAuthor("GuGu");
//uMusic.setTitle("天籁之音");
// 设置音乐缩略图
//uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
//mController.setShareMedia(uMusic);

    // 设置分享视频
//UMVideo umVideo = new UMVideo(
//          "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
// 设置视频缩略图
//umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
//umVideo.setTitle("友盟社会化分享!");
//mController.setShareMedia(umVideo);
    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN
            };
    UMImage image = new UMImage(MainActivity.this, "http://www.umeng" +
            ".com/images/pic/social/integrated_3.png");
    UMShareAPI mShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        //可以将一下代码加到你的MainActivity中，或者在任意一个需要调用分享功能的activity当中
        String[] mPermissionList = new String[]{Manifest.permission
                .ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest
                .permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest
                .permission.WRITE_EXTERNAL_STORAGE, Manifest.permission
                .SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest
                .permission.GET_ACCOUNTS};
        ActivityCompat.requestPermissions(MainActivity.this, mPermissionList, 100);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        mShareAPI = UMShareAPI.get(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i,
                               Map<String, String>
                                       map) {
            Toast.makeText(getApplicationContext(), "Authorize succeed",
                    Toast
                            .LENGTH_SHORT).show();

            Set<Map.Entry<String, String>> set = map.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();

                Log.i("tag", entry.getKey() + "-----" + entry.getValue
                        ().toString());

            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast
                    .LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel",
                    Toast
                            .LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShareAPI.deleteOauth(MainActivity.this, SHARE_MEDIA.RENREN, umAuthListener);
        mShareAPI.deleteOauth(MainActivity.this, SHARE_MEDIA.SINA, umAuthListener);
        mShareAPI.deleteOauth(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                new ShareAction(this).setDisplayList(displaylist)
                        .withText("呵呵")
                        .withTitle("title")
                        .withTargetUrl("http://www.baidu.com")
                        .withMedia(image)
                        .setListenerList(new UMShareListener() {
                            @Override
                            public void onResult(SHARE_MEDIA platform) {
                                Toast.makeText(MainActivity.this, platform + " " +
                                        "分享成功啦", Toast
                                        .LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(SHARE_MEDIA platform, Throwable t) {
                                Toast.makeText(MainActivity.this, platform + " " +
                                        "分享失败啦", Toast
                                        .LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA platform) {
                                Toast.makeText(MainActivity.this, platform + " " +
                                        "分享取消了", Toast
                                        .LENGTH_SHORT).show();
                            }
                        })
                        .open();
                break;
            case R.id.btn2:
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.btn3:
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.btn4:
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.RENREN, umAuthListener);
                break;
        }

    }
}
