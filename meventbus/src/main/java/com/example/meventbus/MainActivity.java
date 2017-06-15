package com.example.meventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.button3)
    Button button3;
    boolean isRegiste = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void getEvent(MessageEvent messageEvent) {
        Log.e("tag", "getEvent: " + messageEvent.getMessage());
        text.setText(messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getStickyEvent(StickyMessageEvent messageEvent){
        Toast.makeText(this, "进入粘性回调", Toast.LENGTH_SHORT).show();
        Log.e("tag", "getStickyEvent: " + messageEvent.getMessage());
        text.setText(messageEvent.getMessage());
    }

    @OnClick({R.id.text, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.button3:
                Toast.makeText(this, "点击了sticky事件", Toast.LENGTH_SHORT).show();
                Log.e("tag", isRegiste + "");
                if (isRegiste){
                    EventBus.getDefault().register(this);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
