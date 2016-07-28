package com.jikexueyuan.mp3lrc;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ArrayList<LrcData> lrcDatas = null;
    private TextView tv_Lrc;
    private MediaPlayer mediaPlayer = null;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inintUI();

    }

    private void inintUI() {
        tv_Lrc = (TextView) findViewById(R.id.tv_Lrc);
        //获得解析后的歌词数据
        lrcDatas = getLrcDatas();
        //开启新线程播放音乐
        new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.xiaoshuiguo);
                //开启播放MP3
                mediaPlayer.start();
                while (mediaPlayer.isPlaying()) {
                    //当播放时 获得当前的播放时间
                    int currentTime = mediaPlayer.getCurrentPosition();
                    for (LrcData lrcData : lrcDatas) {
                        //如果该播放时间处于某一行歌词的开始结束时间之中 就发送消息
                        if (lrcData.getStartTime() < currentTime && lrcData.getEndTime() >currentTime) {
                            Message msg = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("lrc",lrcData.getLrc());
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mediaPlayer.release();
            }
        }).start();

        //获得消息使用handler更新主界面UI
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String str = bundle.getString("lrc");
                tv_Lrc.setText(str);
            }
        };
    }


    private ArrayList<LrcData> getLrcDatas() {
        ArrayList<LrcData> list = new ArrayList<>();
        //添加正则表达式
        Pattern pattern = Pattern.compile("\\d{2}");
        //获得应用内歌词文件输入流
        InputStream is = getResources().openRawResource(R.raw.shuiguo);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line = "";
        try {
            while ((line = br != null ? br.readLine() : null) != null) {
                //替换每行的符号
                line = line.replace("[", "");
                line = line.replace("]", "@");
                //利用split方法将每行字符串分割成2组字符串
                String[] splitdata = line.split("@");
                //判断是否为没有歌词的一行
                if (line.endsWith("@")) {
                    for (String str : splitdata) {
                        str = str.replace(":", "@");
                        str = str.replace(".", "@");
                        //将时间字符串分割成3组 并与正则表达式匹配
                        String[] timedata = str.split("@");
                        Matcher matcher = pattern.matcher(timedata[0]);
                        if (timedata.length == 3 && matcher.matches()) {
                            //提取分
                            int m = Integer.parseInt(timedata[0]);
                            //提取秒
                            int s = Integer.parseInt(timedata[1]);
                            //提取分秒
                            int ms = Integer.parseInt(timedata[2]);
                            //获得换算后的毫秒时间
                            int currentTime = (m * 60 + s) * 1000 + ms * 10;
                            LrcData lrcData = new LrcData();
                            //将时间设定为该行的起始时间
                            lrcData.setStartTime(currentTime);
                            //设定该行歌词为空
                            lrcData.setLrc("");
                            list.add(lrcData);
                        }
                    }
                } else {
                    //如果有歌词 首先将歌词部分提取
                    String lrccontent = splitdata[splitdata.length - 1];
                    //循环遍历时去掉歌词部分
                    for (int i = 0; i < splitdata.length - 1; ++i) {
                        String str = splitdata[i];
                        str = str.replace(":", "@");
                        str = str.replace(".", "@");
                        //将时间字符串分割成3组 并与正则表达式匹配
                        String[] timedata = str.split("@");
                        Matcher matcher = pattern.matcher(timedata[0]);
                        if (timedata.length == 3 && matcher.matches()) {
                            //提取分
                            int m = Integer.parseInt(timedata[0]);
                            //提取秒
                            int s = Integer.parseInt(timedata[1]);
                            //提取分秒
                            int ms = Integer.parseInt(timedata[2]);
                            //获得换算后的毫秒时间
                            int currentTime = (m * 60 + s) * 1000 + ms * 10;
                            LrcData lrcData = new LrcData();
                            //将时间设定为该行的起始时间
                            lrcData.setStartTime(currentTime);
                            //设定该行歌词
                            lrcData.setLrc(lrccontent);
                            list.add(lrcData);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (br != null) {
                    br.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //重新整理数据 准备添加每行的结束时间
        Iterator<LrcData> iterator = list.iterator();
        ArrayList<LrcData> arrayList = new ArrayList<>();
        LrcData oldVar = null;
        while (iterator.hasNext()) {
            //顺序得到每行的数据
            LrcData var = iterator.next();

            //如果是第一行 将数据赋值给oldVar
            if (oldVar == null) {
                oldVar = var;
            } else {
                //不是第一行 将该行的起始时间设定为上一行的结束时间 并将上一行的数据添加进集合
                int time = var.getStartTime();
                oldVar.setEndTime(time);
                arrayList.add(oldVar);
                oldVar = var;
            }
            //如果是最后一行 将该行结束时间设定为起始时间200毫秒后 然后添加到集合
            if (!iterator.hasNext()) {
                var.setEndTime(var.getStartTime()+200);
                arrayList.add(var);
            }
        }

        //返回整理好的完整数据
        return arrayList;
    }


}
