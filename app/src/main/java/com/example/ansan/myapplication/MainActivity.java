package com.example.ansan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button: // 폴더 삭제

                break;
            case R.id.button2: // 폴더 생성

                break;
            case R.id.button3: // 파일 생성

                break;
            case R.id.button4: // 파일 읽기

                break;
            case R.id.button5: // 파일 목록 가져오기

                break;
        }
    }
}
