package com.akrivonos.a2chparser.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.interfaces.CallBack;
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils;

import java.lang.ref.WeakReference;

public class AdulthoodDialog extends Dialog {

    private WeakReference<Context> contextWeakReference;
    private CallBack callBack;
    private View.OnClickListener onClickListenerChoose = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean adultSetting = false;
            switch (view.getId()) {
                case R.id.btn_accept:
                    adultSetting = true;
                    break;
                case R.id.btn_cancel:
                    adultSetting = false;
                    break;
            }
            Context context = contextWeakReference.get();
            if (context != null) {
                SharedPreferenceUtils.setAultSetting(context, adultSetting);
            }
            dismiss();
        }
    };

    public AdulthoodDialog(Context context, CallBack callBack) {
        super(context);
        this.callBack = callBack;
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        callBack.call();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_adulthood);
        TextView accept = findViewById(R.id.btn_accept);
        accept.setOnClickListener(onClickListenerChoose);
        TextView cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(onClickListenerChoose);
    }
}
