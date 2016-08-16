package com.angcyo.eventbus30demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class Main3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //EventBus.getDefault().post(new MsgEvent("From Main3"));

                EventBus.getDefault().postSticky(new MsgEvent("From Main3 With Sticky"));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                EventBus.getDefault().register(Main3Activity.this);
            }
        });
    }

    /**
     * threadMode 表示方法在什么线程执行   (Android更新UI只能在主线程, 所以如果需要操作UI, 需要设置ThreadMode.MainThread)
     * sticky     表示是否是一个粘性事件   (如果你使用postSticky发送一个事件,那么需要设置为true才能接受到事件)
     * priority   优先级                 (如果有多个对象同时订阅了相同的事件, 那么优先级越高,会优先被调用.)
     */
    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 100)
    public void onEvent(MsgEvent event) {
        Log.i("-->", "Main3 onEvent : " + EventBus.getDefault().hasSubscriberForEvent(MsgEvent.class));
        EventBus.getDefault().removeStickyEvent(event);
    }
}
