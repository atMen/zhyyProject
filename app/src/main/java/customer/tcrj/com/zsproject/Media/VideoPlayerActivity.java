package customer.tcrj.com.zsproject.Media;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.DialogHelper;
import customer.tcrj.com.zsproject.bean.LoginInfo;
import customer.tcrj.com.zsproject.bean.bean;
import customer.tcrj.com.zsproject.first.XmIconActivity;
import customer.tcrj.com.zsproject.net.ApiConstants;


public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    MyVideoView vvView;
    ImageView iv_play_video;
    ProgressBar progressBar;
    ImageView iv_video_screenshot;
    Button btn_upload;

    ImageView btnback;
    TextView txtTitle;
    String path;

    private MyOkHttp mMyOkhttp;
    LoginInfo.ResultBean data;

    private String ProID;
    private String id;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue),5);

        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        data = (LoginInfo.ResultBean) ACache.get(this).getAsObject("userinfo");
        mLoadingDialog = DialogHelper.getLoadingDialog(this);

        id = getIntent().getStringExtra("mlid");
        ProID = getIntent().getStringExtra("ProID");

        iv_play_video= findViewById(R.id.iv_play_video);
        vvView = findViewById(R.id.vv_view);
        progressBar = findViewById(R.id.progress);
        iv_video_screenshot = findViewById(R.id.iv_video_screenshot);
        btnback = findViewById(R.id.btnback);
        txtTitle = findViewById(R.id.txtTitle);
        btn_upload = findViewById(R.id.btn_upload);
        initView(savedInstanceState);
    }

    //播放网络资源
//    videoView.setVideoPath(url);
//        videoView.requestFocus();
//        videoView.start();


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void initView(Bundle savedInstanceState) {
        btnback.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        txtTitle.setText("视频上传");
         path = getIntent().getStringExtra("path");
        Log.e("TAG","path"+path);
        if (!TextUtils.isEmpty(path)) {
            vvView.setVideoPath(path);
            setImage(path);

        }

//        String videopath = getIntent().getStringExtra("videopath");
//        if (!TextUtils.isEmpty(videopath)) {
//            vvView.setVideoURI(videopath);
//
//
//        }




        initListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initListener() {


        iv_play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vvView.isPlaying()) {
                    iv_play_video.setVisibility(View.GONE);
                    iv_video_screenshot.setVisibility(View.GONE);
                    vvView.start();
                }
            }
        });


        vvView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                iv_video_screenshot.setVisibility(View.VISIBLE);
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

    private Bitmap thumbnail;

    private void setImage(String videoUri){
        if (!TextUtils.isEmpty(videoUri)) {
//            File file = new File(videoUri);
//            long fileSize = getFileSize(file);
//            String formetFileSize = FormetFileSize(fileSize);
//            Log.d("upLoadImageToNet", "视频大小:" + ">>>>>>>>>" + formetFileSize);
//            spsize.setText("视频大小:" + formetFileSize);
        thumbnail = VideoUltls.getVideoThumbnail(videoUri);
        }
        if (thumbnail != null) {
            iv_video_screenshot.setImageBitmap(thumbnail);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnback:

                finish();
                break;
            case R.id.btn_upload:
//              showLoadingDialog("正在上传资料内容..");
                showLoadingDialog("正在努力上传...");
                new MyAsyncTask().execute(1);
                break;
            default:
                break;
        }

    }

    public class MyAsyncTask extends AsyncTask<Integer, String, String> {
        public MyAsyncTask() {
            super();
        }

        //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
        @Override
        protected void onPreExecute() {
//            showLoadingDialog("正在努力上传...");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Log.e("xxxxxx","xxxxxxexecute传入参数="+integers[0]);

            String base64File = null;
            try {
                base64File = Utils.encodeBase64File(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return base64File;
        }
        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(String result) {
            Log.e("TAG","线程结束"+result);

            String time = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss",
                    Locale.getDefault()).format(System.currentTimeMillis());
            if(result != null){
                getDataFromNet(result);
            }
        }

        /**
         * 这里的Intege参数对应AsyncTask中的第二个参数
         * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
         * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
         */
        @Override
        protected void onProgressUpdate(String... values) {
            String vlaue = values[0]+"";
            Log.e("TAG","xxxxxx vlaue="+vlaue);
        }
    }

    private void getDataFromNet(String result) {


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("fileStr", "sfz.mp4;"+result);
            jsonObject.put("StaffID", data.getName());
            jsonObject.put("SpotID", ProID);
            jsonObject.put("SpotMenuID", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.DW_ICON_PUCH_API)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new GsonResponseHandler<bean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Toast.makeText(VideoPlayerActivity.this, error_msg, Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        Log.e("TAG","msg"+error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, bean response) {
                        Toast.makeText(VideoPlayerActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        hideLoadingDialog();
                        if(response.getStat() == 1){
                            //上传成功，发消息通知页面刷新数据

                            Log.e("TAG","上传成功");
                        }
                    }
                });
    }

    protected Dialog mLoadingDialog = null;

    protected void showLoadingDialog(String str) {
        if (mLoadingDialog != null) {
            TextView tv = (TextView) mLoadingDialog.findViewById(R.id.tv_load_dialog);
            tv.setText(str);
            mLoadingDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


}
