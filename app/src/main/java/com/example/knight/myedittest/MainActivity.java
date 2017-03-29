package com.example.knight.myedittest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SecurityCodeView codeView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeView = (SecurityCodeView) findViewById(R.id.code_msg);
        textView = (TextView) findViewById(R.id.text_info);

        codeView.setOnInputActionListener(new SecurityCodeView.InputActionListener() {
            @Override
            public void onComplitedInput() {
                if (codeView.getSecurityCode().equals("9527")) {
                    textView.setText("验证码输入正确！");
                    textView.setTextColor(Color.parseColor("#009944"));
                }else{
                    textView.setText("请在核实一下验证码");
                }
            }

            @Override
            public void isDeleteAction(boolean isDeleting) {
                textView.setText("输入验证码表示同意相关条款");
            }
        });
    }
}
