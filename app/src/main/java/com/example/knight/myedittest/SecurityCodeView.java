package com.example.knight.myedittest;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by knight on 2017/3/29.
 */

public class SecurityCodeView extends RelativeLayout {

    private EditText mETCode;
    private TextView[] mTVs;
    private StringBuilder stringBuilder = new StringBuilder();

    private InputActionListener listener;

    public SecurityCodeView(Context context) {
        this(context, null);
    }

    public SecurityCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.view_security_edit, this);

        mETCode = (EditText) findViewById(R.id.edit_code);
        mTVs = new TextView[4];
        mTVs[0] = (TextView) findViewById(R.id.tv_code_01);
        mTVs[1] = (TextView) findViewById(R.id.tv_code_02);
        mTVs[2] = (TextView) findViewById(R.id.tv_code_03);
        mTVs[3] = (TextView) findViewById(R.id.tv_code_04);

        mETCode.setCursorVisible(false);

        setListener();
    }

    /**
     * 添加监听
     */
    private void setListener() {

        mETCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    //stringBuilder.delete(0, stringBuilder.length());
                    if (stringBuilder.length() > 3) {
                        mETCode.setText("");
                        return;
                    } else {
                        stringBuilder.append(s);
                        mETCode.setText("");

                        if (stringBuilder.length() == 4) {
                            if (listener != null) {
                                listener.onComplitedInput();
                            }
                        }
                    }
                    for (int i = 0; i < stringBuilder.length(); i++) {
                        mTVs[i].setText(String.valueOf(stringBuilder.charAt(i)));
                        mTVs[i].setBackgroundResource(R.mipmap.bg_verify_press);
                    }
                }
            }
        });

        mETCode.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (isDeleting())
                        return true;
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 是否正在删除状态
     *
     * @return
     */
    private boolean isDeleting() {
        int count = stringBuilder.length();

        Log.e("SecurityCodeView", "count:" + count);
        if (count > 0) {
            stringBuilder.delete(count - 1, count);
            mTVs[count - 1].setText("");
            mTVs[count - 1].setBackgroundResource(R.mipmap.bg_verify);
            if (listener != null) {
                listener.isDeleteAction(true);
            }
            return true;
        } else if (count == 0) {
            return true;
        }
        return false;
    }

    /**
     * 获得输入的验证码
     *
     * @return
     */
    public String getSecurityCode() {
        return stringBuilder.toString();
    }

    /**
     * 清空输入
     */
    public void clearInput() {
        stringBuilder.delete(0, stringBuilder.length());
        for (int i = 0; i < stringBuilder.length(); i++) {
            mTVs[i].setText("");
            mTVs[i].setBackgroundResource(R.mipmap.bg_verify);
        }
    }

    /**
     * 设置输入监听
     *
     * @param listener
     */
    public void setOnInputActionListener(InputActionListener listener) {
        this.listener = listener;
    }

    public interface InputActionListener {

        void onComplitedInput();

        void isDeleteAction(boolean isDeleting);
    }

}
