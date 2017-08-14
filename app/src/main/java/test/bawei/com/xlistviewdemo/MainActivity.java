package test.bawei.com.xlistviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener{

    private XListView xListView;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private int refreshIndex = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xListView = (XListView)findViewById(R.id.xlistview);
        list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            list.add("item----"+1);
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,list);

        xListView.setXListViewListener(this);
        xListView.setPullLoadEnable(true);
        xListView.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {

        new Thread(){
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.add(0,"刷新的数据---"+refreshIndex++);
                        adapter.notifyDataSetChanged();
                        stopXListView();
                    }
                });
            }
        }.start();
    }

    @Override
    public void onLoadMore() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.add("加载更多的数据---"+refreshIndex++);
                        adapter.notifyDataSetChanged();
                        stopXListView();
                    }
                });
            }
        }.start();
    }
    private void stopXListView(){
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }
}
