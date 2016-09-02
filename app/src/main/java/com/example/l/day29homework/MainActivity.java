package com.example.l.day29homework;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    PullToRefreshListView pullToRefreshListView;
    List<String> datas;
    ArrayAdapter<String> adapter;
    Handler handler= new Handler();
    SlidingMenu slidingMenu;
    Button button1,button2,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setMenu(R.layout.my_left);
        slidingMenu.setFadeEnabled(true);
        slidingMenu.setBehindWidth((int) (getResources().getDisplayMetrics().widthPixels*0.8));
        slidingMenu.setBehindScrollScale(0.5f);
        slidingMenu.setShadowDrawable(R.mipmap.ic_launcher);
        slidingMenu.setShadowWidth(50);
        slidingMenu.attachToActivity(this,SlidingMenu.TOUCHMODE_FULLSCREEN);
        button1= (Button) slidingMenu.findViewById(R.id.button1Id);
        button2= (Button) slidingMenu.findViewById(R.id.button2Id);
        button3= (Button) slidingMenu.findViewById(R.id.button3Id);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        pullToRefreshListView= (PullToRefreshListView) findViewById(R.id.pulllist);
        initdata();
        adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.item_poll,datas);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.cxv);
        pullToRefreshListView.getRefreshableView().addHeaderView(imageView);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                            datas.add(0,"人生已多风雨");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                pullToRefreshListView.onRefreshComplete();
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                            datas.add("因为我，仍有梦");

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                pullToRefreshListView.onRefreshComplete();
                            }
                        });
                    }
                }).start();
            }
        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("zsp","position----"+position);
                if (position>2){
                    Log.d("zsp","内容---"+adapter.getItem(position-2));}
                else{
                    Log.d("zsp","内容---"+adapter.getItem(position-1));
                }
            }
        });


    }

    private void initdata() {
        datas=new ArrayList<>();
        for (int i=0;i<100;i++){
            datas.add("往事不要再提"+i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1Id:

                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
                break;
            case R.id.button2Id:
                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(50);
                break;
            case R.id.button3Id:
                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(100);
                break;

            default:break;
        }
    }
}
