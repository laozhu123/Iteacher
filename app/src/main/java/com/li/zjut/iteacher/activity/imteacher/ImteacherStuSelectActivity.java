package com.li.zjut.iteacher.activity.imteacher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.imteacher.AddStuListAdapter;
import com.li.zjut.iteacher.bean.imteacher.ContentData;
import com.li.zjut.iteacher.bean.imteacher.Junior;
import com.li.zjut.iteacher.widget.common.Zhu_ContentViewManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ImteacherStuSelectActivity extends BaseActivity implements View.OnClickListener {

    private ListView listview;
    ArrayList<HashMap<String, Object>> show_data;
    ArrayList<HashMap<String, Object>> all_data;
    AddStuListAdapter adapter;
    LinearLayout content;
    Zhu_ContentViewManager cvManager;
    final String TAG = "ImteacherStuSelectAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imteacher_stu_select);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.teacher_stu_select), true);

        initView();
        initData();
    }

    private void initData() {

        listview.setOnItemClickListener(item_Listener);
        getConnect();
    }

    private void initView() {

        listview = (ListView) findViewById(R.id.list);
        content = (LinearLayout) findViewById(R.id.content);
        cvManager = new Zhu_ContentViewManager(this, content);
        cvManager.setClickListener(setClickListener());
        TextView titleRig = (TextView) findViewById(R.id.txTitleRig);
        titleRig.setText(getString(R.string.sure));
        titleRig.setOnClickListener(this);
        titleRig.setVisibility(View.VISIBLE);
    }

    private void getConnect() {
        ArrayList<Junior> list = new ArrayList<>();
        show_data = new ArrayList<HashMap<String, Object>>();
        all_data = new ArrayList<HashMap<String, Object>>();

        ArrayList<String> ids = getIntent()
                .getStringArrayListExtra("ids");
        if (ids == null)
            ids = new ArrayList<>();

        HashMap<String, Object> map;
        boolean tag;

        for (int i = 0; i < 5; i++) {
            list.add(new Junior(i + "", "helo" + i, "17764507394", ""));
        }

        for (int i = 0; i < list.size(); i++) {
            tag = false;
            for (int k = 0; k < ids.size(); k++) {
                if (list.get(i).getId().equals(ids.get(k))) {
                    tag = true;
                    break;
                }
            }
            if (tag) {
                map = new HashMap<String, Object>();
                map.put("id", list.get(i).getId());
                map.put("name", list.get(i).getName());
                map.put("phone", list.get(i).getMobile());
                map.put("photo", list.get(i).getPicture());
                map.put("isCheck", true);

                ContentData content = new ContentData(
                        list.get(i).getId(), list.get(i).getName(),
                        list.get(i).getMobile());
                cvManager.add(content);
                show_data.add(map);
                all_data.add(map);
            } else {
                map = new HashMap<String, Object>();
                map.put("id", list.get(i).getId());
                map.put("name", list.get(i).getName());
                map.put("phone", list.get(i).getMobile());
                map.put("photo", list.get(i).getPicture());
                map.put("isCheck", false);
                show_data.add(map);
                all_data.add(map);
            }
        }
        adapter = new AddStuListAdapter(this,
                show_data);
        listview.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener item_Listener = new AdapterView.OnItemClickListener() {

        @SuppressLint("NewApi")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long arg3) {

            if ((Boolean) show_data.get(position).get("isCheck")) {
                show_data.get(position).put("isCheck", false);
                cvManager.remove((String) show_data.get(position).get("id"));
                adapter.notifyDataSetChanged();
            } else {
                show_data.get(position).put("isCheck", true);

                String id = (String) show_data.get(position).get("id");
                ContentData contentData = new ContentData(id,
                        (String) show_data.get(position).get("name"),
                        (String) show_data.get(position).get("phone"));
                cvManager.add(contentData);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private View.OnClickListener setClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v.getTag() != null) {
                    String id = v.getTag().toString();
                    cvManager.remove(id);
                    for (int i = 0; i < all_data.size(); i++) {
                        if (all_data.get(i).get("id").equals(id)) {
                            all_data.get(i).put("isCheck", false);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        };
        return listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txTitleRig:
                returnStus();
                break;
        }
    }

    private void returnStus() {
        int n = cvManager.getViewCount();
        Log.d(TAG, "returnStus" + n);
        String[] teamate = new String[n];
        String[] ids = new String[n];
        String[] mobile = new String[n];

        for (int i = 0; i < n; i++) {
            teamate[i] = cvManager.getContentData().get(i).getName();
            ids[i] = cvManager.getContentData().get(i).getId();
            mobile[i] = cvManager.getContentData().get(i).getMobile();
        }
        Intent intent = new Intent();
        intent.putExtra("ids", ids);
        intent.putExtra("teamate", teamate);
        intent.putExtra("mobile", mobile);
        setResult(RESULT_OK, intent);
        finish();
    }
}
