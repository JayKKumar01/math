package com.kid.math.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.service.controls.templates.TemperatureControlTemplate;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kid.math.R;
import com.kid.math.databinding.ActivityDivBinding;
import com.kid.math.databinding.ActivityMainBinding;
import com.kid.math.databinding.ActivityMulBinding;
import com.kid.math.databinding.ActivitySubBinding;

import java.util.Random;

public class div extends AppCompatActivity{
    ActivityDivBinding binding;

    float dx,dy;

    boolean changed = false;

    int digit = 1;
    int hs = 0;

    boolean mp = false;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDivBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        generate();


        binding.ans1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                changed = false;
                drag(view, event);
                return true;
            }
        });
        binding.ans2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                changed = false;
                drag(view, event);
                return true;
            }
        });
        binding.ans3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                changed = false;
                drag(view, event);
                return true;
            }
        });

    }

    private void drag(View v, MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN){
            dx = v.getX() - event.getRawX();
            dy = v.getY() - event.getRawY();
            //isMoving = false;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            //isMoving = true;

//            v.setX(event.getRawX() + dx);
//            v.setY(event.getRawY() + dy);

            v.animate().x(event.getRawX() + dx)
                    .y(event.getRawY() + dy)
                    .setDuration(0).start();

        }

        if (event.getAction() == MotionEvent.ACTION_UP){
            View w = binding.box;
            if (v.getY() + v.getHeight() < w.getY() && (v.getX() >= w.getX() &&(v.getX() + v.getWidth()) <= (w.getX() + w.getWidth()))){
                check(v);
            }
            v.animate().translationX(0).translationY(0).setDuration(1000);
        }
    }

    private void check(View v) {
        ImageView teacher = binding.teacher;
        ImageView kid = binding.kid;

        TextView text = (TextView) v;
        int kidAns = Integer.parseInt(text.getText().toString());
        binding.ans.setText(kidAns + "");

        int sum = Integer.parseInt(binding.ques1.getText().toString())
                / Integer.parseInt(binding.ques2.getText().toString());

        if (sum == kidAns){
            playSound(1);
            teacher.setImageDrawable(getDrawable(R.drawable.happy_teacher));
            kid.setImageDrawable(getDrawable(R.drawable.happy_kid));
            binding.check.setText("RIGHT");
            binding.check.setBackgroundColor(Color.parseColor("#00FF0B"));

            generate();
            hs += 2;
            binding.hs.setText("HS : "+hs);
        }
        else {
            playSound(0);
            teacher.setImageDrawable(getDrawable(R.drawable.sad_teacher));
            kid.setImageDrawable(getDrawable(R.drawable.sad_kid));
            binding.check.setText("WRONG");
            binding.check.setBackgroundColor(Color.RED);
            hs -= 1;
            binding.hs.setText("HS : "+hs);
        }

    }

    public void playSound(int i){

        if (mp){
            player.release();
            mp = false;
        }
        if (i == 1){
            player = MediaPlayer.create(this,R.raw.right);
            player.start();
            mp = true;
        }
        else {
            player = MediaPlayer.create(this,R.raw.wrong);
            player.start();
            mp = true;
        }
    }




    public void generate(){
        Random random = new Random();
        int a = Integer.parseInt(String.format("%02d",random.nextInt(100)));
        int b = Integer.parseInt(getDigit());
        a *= b;
        if(a>=b & b!=0){
            binding.ques1.setText(a+"");
            binding.ques2.setText(b+"");
            binding.ans.setText("");

            Random ans = new Random();
            int x = ans.nextInt(3) +  1;

            a = a/b;

            int p = a+10;
            int q = 0;

            if (a-10 <0){
                q = a+ 3;
            }
            else {
                q = a - 10;
            }

            if (x == 1){
                binding.ans1.setText(a+"");
                binding.ans2.setText(p+"");
                binding.ans3.setText(q+"");
            }
            if (x == 2){
                binding.ans2.setText(a+"");
                binding.ans1.setText(p+"");
                binding.ans3.setText(q+"");
            }
            if (x == 3){
                binding.ans3.setText(a+"");
                binding.ans2.setText(p+"");
                binding.ans1.setText(q+"");
            }
        }
        else {
            generate();
        }
    }
    public String getDigit(){
        Random random = new Random();
        String str = "";
        switch (digit){
            case 1:
                str = String.format("%01d",random.nextInt(10));
                break;
            case 2:
                str = String.format("%02d",random.nextInt(100));
                break;
            case 3:
                str = String.format("%03d",random.nextInt(1000));
                break;
        }

        return str;
    }




    public void changeDigit(View view){
        TextView text = (TextView) view;
        digit = Integer.parseInt(text.getText().toString());

        int c1 = Color.parseColor("#00000000");
        int c2 = Color.parseColor("#CDCD1010");

        binding.two.setBackgroundColor(c1);
        binding.three.setBackgroundColor(c1);
        binding.four.setBackgroundColor(c1);

        text.setBackgroundColor(c2);
        generate();
    }




    public void gotoAdd(View view){
        Intent intent = new Intent(div.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void gotoSub(View view){
        Intent intent = new Intent(div.this,sub.class);
        startActivity(intent);
        finish();
    }
    public void gotoMul(View view){
        Intent intent = new Intent(div.this,mul.class);
        startActivity(intent);
        finish();
    }


}