package com.humo.magazinereader;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.ViewGroup;

import com.hyc.libs.base.BaseActivity;
import com.hyc.libs.base.view.recyclerview.HRAdapter;
import com.hyc.libs.base.view.recyclerview.HRViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    RecyclerView recyclerView;

    LinearLayoutManager manager;

    @Override
    public int setLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public String setMainTitle() {
        return null;
    }

    @Override
    public void initialize() {
        recyclerView = findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        HRAdapter adapter = new HRAdapter() {
            @Override
            public HRViewHolder getHolder(ViewGroup parent) {
                return new VH(MainActivity.this,parent,null);
            }
        };

        recyclerView.setAdapter(adapter);

        List list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add(i+"");
        }
        adapter.addMore(list);

    }

}
