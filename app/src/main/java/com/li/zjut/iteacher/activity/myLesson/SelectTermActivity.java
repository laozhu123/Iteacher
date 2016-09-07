package com.li.zjut.iteacher.activity.myLesson;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.mylesson.TermsListAdapter;
import com.li.zjut.iteacher.bean.mylesson.Term;
import com.li.zjut.iteacher.bean.mylesson.Terms;
import com.li.zjut.iteacher.widget.CustomDialog;
import com.li.zjut.iteacher.widget.common.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SelectTermActivity extends BaseActivity implements View.OnClickListener {

    TermsListAdapter adapter;
    List<Terms> mDatas = new ArrayList<>();
    String selectYear = "";
    String selectTerm = "";
    int id = -1;
    final int REQUEST_CREATE_TERM = 1;
    String year;
    String term;
    List<String> arrayYear, arrayTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_term);

        initView();
        initData();
    }

    private void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.select_term));
        ImageView leftImg = (ImageView) findViewById(R.id.left_img);
        leftImg.setImageResource(R.drawable.backarrow);
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setOnClickListener(this);
        findViewById(R.id.tv_create_term).setOnClickListener(this);

        ListView lvTerms = (ListView) findViewById(R.id.lv_terms);
        adapter = new TermsListAdapter(mDatas, this);
        adapter.setListener(new TermsListAdapter.OnClickListener() {
            @Override
            public void onClick(int position, int up_down) {
                Log.d("position", position + "-" + up_down);
                selectYear = mDatas.get(position).getYear();
                selectTerm = mDatas.get(position).getList().get(up_down).getName();
                id = mDatas.get(position).getList().get(up_down).getId();
                adapter.notifyDataSetChanged();
            }
        });
        lvTerms.setAdapter(adapter);
    }

    private void initData() {
        Terms t1 = new Terms();
        t1.setYear("2016-2017");
        List<Term> l1 = new ArrayList<>();
        Term tm1 = new Term();
        tm1.setName("上学期");
        Term tm2 = new Term();
        tm2.setName("下学期");
        l1.add(tm1);
        l1.add(tm2);
        t1.setList(l1);

        Terms t2 = new Terms();
        t2.setYear("2015-2016");
        List<Term> l2 = new ArrayList<>();
        Term tm = new Term();
        tm.setName("上学期");
        l2.add(tm);
        t2.setList(l2);

        mDatas.add(t1);
        mDatas.add(t2);
        adapter.notifyDataSetChanged();
        arrayTerm = Arrays.asList(getResources().getStringArray(R.array.term));
        arrayYear = Arrays.asList(getResources().getStringArray(R.array.year));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.left_img:
                intent.putExtra("selectYear", selectYear);
                intent.putExtra("selectTerm", selectTerm);
                intent.putExtra("id", id);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_create_term:

                showYearDialog();
                break;
        }
    }

    private void showYearDialog() {

        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(arrayYear);
        Calendar c = Calendar.getInstance();
        int index = c.get(Calendar.YEAR) - 2010;
        int add = c.get(Calendar.MONTH) > 7 ? 0 : -1;
        wv.setSeletion(index + add);
        year = arrayYear.get(index + add);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                year = arrayYear.get(selectedIndex - 2);
            }
        });

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setContentView(outerView);
        builder.setTitle("选择年份");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                showTermDialog();
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    private void showTermDialog() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(arrayTerm);
        Calendar c = Calendar.getInstance();
        int term = c.get(Calendar.MONTH) > 7 ? 0 : 1;
        wv.setSeletion(term);
        this.term = arrayTerm.get(term);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                SelectTermActivity.this.term = arrayTerm.get(selectedIndex - 2);
            }
        });

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setContentView(outerView);
        builder.setTitle("选择学期");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
//                Intent intent = new Intent();
//                intent.setClass(SelectTermActivity.this, CreateTermActivity.class);
//                startActivityForResult(intent, REQUEST_CREATE_TERM);
                addTerm();
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    private void addTerm() {
        boolean newYear = true;
        boolean haveTerm = false;
        for (Terms terms : mDatas) {
            Log.d("1", year);
            Log.d("1", terms.getYear());
            if (year.equals(terms.getYear())) {
                newYear = false;
                for (Term term : terms.getList()) {
                    if (term.getName().equals(this.term)) {
                        haveTerm = true;
                        break;
                    }
                }
            }
            if (!newYear) {
                if (!haveTerm) {
                    Term t = new Term();
                    t.setName(this.term);
                    terms.getList().add(t);
                    adapter.notifyDataSetChanged();
                }
                break;
            }
        }
        if (newYear) {
            Terms ts = new Terms();
            ts.setYear(this.year);
            Term tm = new Term();
            tm.setName(this.term);
            ts.setList(new ArrayList<Term>());
            ts.getList().add(tm);
            mDatas.add(ts);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE_TERM:  //创建新学期

                    break;
            }
        }
    }
}
