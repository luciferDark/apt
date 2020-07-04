package com.ll.apt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ll.apt_annotation.LLBindView;
import com.ll.apt_library.KylinKnife;

public class MainActivity extends AppCompatActivity {
    public static  String TAG = "MainActivity";
    @LLBindView(R.id.test_apt)
    TextView textView;

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
}