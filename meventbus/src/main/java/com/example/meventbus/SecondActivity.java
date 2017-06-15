package com.example.meventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                MessageEvent messageEvent = new MessageEvent("text");
                EventBus.getDefault().post(messageEvent);
                break;
            case R.id.button2:
                sendStickyEvent();
                break;
        }
    }

    private void sendStickyEvent() {
        EventBus.getDefault().postSticky(new StickyMessageEvent("sticky event"));
//        finish();
        startActivity(new Intent(SecondActivity.this,MainActivity.class));
    }
}
