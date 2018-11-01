package customer.tcrj.com.zsproject.Media;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jaeger.library.StatusBarUtil;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.bean.iconInfo;


public class VideoActivity extends AppCompatActivity {

    MyVideoView vvView;
    ImageView iv_play_video;
    ProgressBar progressBar;
    iconInfo.ResultBean item;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black),5);
        item = (iconInfo.ResultBean) getIntent().getSerializableExtra("videoInfo");
        iv_play_video= findViewById(R.id.iv_play_video);
        vvView = findViewById(R.id.vv_view);
        progressBar = findViewById(R.id.progress);
        initView(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void initView(Bundle savedInstanceState) {
        String fileUrl = item.getFileUrl();
        Log.e("TAG","fileUrl"+fileUrl);
        if (!TextUtils.isEmpty(fileUrl)) {
            vvView.setVideoPath(fileUrl);
        }

//        String videopath = getIntent().getStringExtra("videopath");
//        if (!TextUtils.isEmpty(videopath)) {
//            vvView.setVideoURI(videopath);
//
//
//        }


        iv_play_video.setVisibility(View.GONE);
        vvView.start();

        initListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initListener() {
        iv_play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vvView.isPlaying()) {
                    iv_play_video.setVisibility(View.GONE);
                    vvView.start();
                }
            }
        });


        vvView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                iv_play_video.setVisibility(View.VISIBLE);
            }
        });

        vvView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("TAG","onPrepared");
                progressBar.setVisibility(View.GONE);
            }
        });

        vvView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                        显示 Loading 图
                        progressBar.setVisibility(View.VISIBLE);
                        Log.e("TAG","显示 Loading 图");
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //隐藏 Loading 图
                        Log.e("TAG","//隐藏 Loading 图 ");
                        progressBar.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
                return false;
            } });

    }


}
