package com.hog2020.ex39datastorageinternal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);
    }

    public void clicksave(View view) {

        //저장할 데이터
        String data = et.getText().toString();
        et.setText("");

        //액티비티 클래스는 이미 내장메모리(Internal)에
        //file 을 저장 할 수 있도록
        //Stream 을 열수 있는 기능 메소드가 존재함
        try {
            FileOutputStream fos =openFileOutput("Data.txt",MODE_APPEND);
            //fos 은 바이트단위로 데이터를 보내야 하기 에 사용하기 불편함
            //그래서 문자스트림으로 변환하고. 더 나아가서 보조문자스트림을 쓰면 편해짐.
            PrintWriter writer = new PrintWriter(fos);
            
            writer.println(data);
            writer.flush();
            writer.close();

            Toast.makeText(this, "saved ", Toast.LENGTH_SHORT).show();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clickload(View view) {

        try {
            FileInputStream fis = openFileInput("Data.txt");//바이트 스트림
            //바이트스트림을 문자스트림으로 변환
            InputStreamReader isr = new InputStreamReader(fis);
            //문자스트림(isr)은 한번에 한글자만 읽어져서 사용하기 불편함
            //한줄씩 읽어들이는 능력을 가진 보조문자스트림 사용
            BufferedReader reader = new BufferedReader(isr);
            String line =reader.readLine();//한줄읽기[단, 줄바꿈문자는 가져오지 않음]

            StringBuffer buffer =new StringBuffer();

            while(line != null){

                buffer.append(line+"\n");
                line= reader.readLine();//다음줄 읽기
            }

            tv.setText(buffer.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}