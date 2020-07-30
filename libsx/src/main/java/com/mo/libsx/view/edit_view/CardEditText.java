package com.mo.libsx.view.edit_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.mo.libsx.modle.listener.textListener.KOnTextChangedListener;

/**
 * @ author：mo
 * @ data：2020/7/28:16:38
 * @ 功能：卡号格式输入框，4位一空格
 */
public class CardEditText extends AppCompatEditText {
    public CardEditText(Context context) {
        super(context);
    }


    public CardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBankCardTypeOn();
    }


    public CardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private boolean isRun = false;
    private String d = "";


    private int getEditTextCursorIndex(EditText editText) {


        int i = editText.getText().toString().length();
        return i;


    }


    public void setBankCardTypeOn() {
        CardEditText.this.addTextChangedListener(new KOnTextChangedListener() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (isRun) {
                    isRun = false;
                    return;
                }
                isRun = true;
                d = "";
                String newStr = s.toString();
                newStr = newStr.replace(" ", "");


                int index = 0;
                while ((index + 4) < newStr.length()) {
                    d += (newStr.substring(index, index + 4) + " ");
                    index += 4;
                }
                d += (newStr.substring(index, newStr.length()));
                int i = getEditTextCursorIndex(CardEditText.this);


                CardEditText.this.setText(d);
                try {


                    if (i % 5 == 0 && before == 0) {
                        if (i + 1 <= d.length()) {// 判断位数再设置，否则在第四位的时候按空格程序会崩掉
                            CardEditText.this.setSelection(i + 1);
                        } else {
                            CardEditText.this.setSelection(d.length());
                        }
                    } else if (before == 1 && i < d.length()) {
                        CardEditText.this.setSelection(i);
                    } else if (before == 0 && i < d.length()) {
                        CardEditText.this.setSelection(i);
                    } else
                        CardEditText.this.setSelection(d.length());
                } catch (Exception e) {
                    // VposUtility.showException(e);
                }


            }


            private void insertText(EditText editText, String mText) {
                editText.getText().insert(getEditTextCursorIndex(editText),
                        mText);
            }


        });
    }
}