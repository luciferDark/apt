package com.ll.apt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ll.apt_annotation.LLBindView;
import com.ll.apt_annotation.LLOnClick;
import com.ll.apt_library.KylinKnife;

public class MainActivity extends AppCompatActivity {
    public static  String TAG = "MainActivity";
    @LLBindView(R.id.test_apt)
    TextView textView;
    @LLBindView(R.id.test_apt_1)
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KylinKnife.bind(this);
        if (textView != null){
            textView.setText("sdadasd");
        } else {
            Log.d(TAG, "onCreate: textView is null");
        }
    }

    @LLOnClick(R.id.test_apt)
    public void textViewOnclick(View view){
        if (textView != null){
            textView.setText("textViewOnclick");
        } else {
            Log.d(TAG, "textViewOnclick: textView is null");
        }
    }
    @LLOnClick(R.id.test_apt_1)
    public void textViewOnclick1(View view){
        if (textView1 != null){
            textView1.setText("textViewOnclick1");
        } else {
            Log.d(TAG, "textViewOnclick: textView is null");
        }
    }
}