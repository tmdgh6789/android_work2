package com.example.ansan.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.editText);
    }

    public void onClick(View v) {
        if (!isStoragePermissionGranted()) {
            Toast.makeText(getApplicationContext(), "SD Card 사용 불가", Toast.LENGTH_SHORT).show();
            return;
        }

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String folder = path + "/myLoveDir";
        String fileName = folder + "/myFile.txt";

        File myFolder = new File(folder);

        switch (v.getId()) {
            case R.id.button: // 폴더 생성
                myFolder.mkdir();
                Toast.makeText(getApplicationContext(), "폴더 생성", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2: // 폴더 삭제
                myFolder.delete();
                Toast.makeText(getApplicationContext(), "폴더 삭제", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3: // 파일 생성
                try {
                    FileOutputStream fos = new FileOutputStream(fileName);
                    String str = "Hello";
                    fos.write(str.getBytes());
                    fos.close();
                    Toast.makeText(getApplicationContext(), "파일 생성", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "파일 생성 에러", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "파일 쓰기 에러", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.button4: // 파일 읽기
                try {
                    FileInputStream fis = new FileInputStream(fileName);
                    byte[] arr = new byte[fis.available()];
                    fis.read(arr);
                    fis.close();

                    Toast.makeText(getApplicationContext(), new String(arr), Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "파일 없음", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "파일 없음", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.button5: // 파일 목록 가져오기
                File fileList[] = new File(path).listFiles();
                String str = "";
                for (int i = 0; i <fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        str += "<폴더>" + fileList[i].toString() + "\n";
                    } else {
                        str += "<파일>" + fileList[i].toString() + "\n";
                    }
                }
                et.setText(str);
                break;
        }
    }

    String TAG = "TEST";
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
