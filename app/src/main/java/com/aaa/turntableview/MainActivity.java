package com.aaa.turntableview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //定义轮盘
    private Turntable turntable;
    //定义箭头
    private ImageView image1;
    //定义箭头转动的角度
    private RotateAnimation anim;
    //轮盘当前位置
    private int CurrentRadiosId;
    //轮盘分割的角度
    private List<Float> listRadios;
    //旋转的总角度
    private List<Float> listImaRadios;
    //产生随机数
    private int round;
    //奖项数组
    private String[] text = {"谢谢抽奖", "中奖一百块", "中奖一千块", "一个女朋友", "老婆", "别墅", "番狗"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turntable= findViewById(R.id.turntable);
        image1 = findViewById(R.id.image1);
        round = text.length;
        turntable.setText(text);
        CurrentRadiosId = 0;
        listRadios = new ArrayList<>();
        listRadios = turntable.getRadioList();
        listImaRadios = new ArrayList<>();
        for (int i = 0;i<listRadios.size();i++){
            float rado = listRadios.get(i)-270;
            if (rado>0|rado==0){
                listRadios.add(rado);
            }else {
                listImaRadios.add(360+rado);
            }
        }

    }

    public void rotate(View view) {
        image1.setClickable(false);
        int sc = new Random().nextInt(round);
        rotateTo(CurrentRadiosId, sc);
        CurrentRadiosId = sc;
    }

    private void rotateTo(int currentId, int rotateId) {
        float rotateRadio = 360 * 4 + listImaRadios.get(rotateId);
        anim = new RotateAnimation(listImaRadios.get(currentId),
                rotateRadio, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image1.setClickable(true);
                Toast.makeText(MainActivity.this, "" + text[CurrentRadiosId], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image1.startAnimation(anim);
    }
}
