package com.li.zjut.iteacher.activity.imteacher;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.widget.WordWrapView;

import java.util.ArrayList;
import java.util.List;

public class AddTagsActivity extends BaseActivity implements View.OnClickListener {

    private WordWrapView wordWrapView;
    private String[] strs = new String[]{"哲学系", "新疆维吾尔族自治区", "新闻学", "心理学",
            "犯罪心理学", "明明白白", "西方文学史", "计算机", "掌声", "心太软", "生命",
            "程序开发"};
    private EditText etInput;
    private ArrayList<String> backData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.add_tags), true);
        initView();
        initData();
    }

    private void initView() {

        wordWrapView = (WordWrapView) this.findViewById(R.id.view_wordwrap);
        findViewById(R.id.v_blank).setOnClickListener(this);
        etInput = (EditText) findViewById(R.id.et_input);
        TextView txRigTitle = (TextView) findViewById(R.id.txTitleRig);
        txRigTitle.setText(getString(R.string.finish));
        txRigTitle.setVisibility(View.VISIBLE);
        txRigTitle.setOnClickListener(this);
    }

    private void initData() {
        String[] tags = getIntent().getStringArrayExtra("tags");
        if (tags != null) {
            for (String s : tags)
                backData.add(s);
        }
        for (int i = 0; i < 12; i++) {

            addView(strs[i], isContain(strs[i]));
        }
    }

    private boolean isContain(String s) {
        for (String s1 : backData) {
            if (s1.equals(s))
                return true;
        }
        return false;
    }

    private void addView(String s, boolean selected) {
        TextView textview = new TextView(this);
        textview.setText(s);
        textview.setBackgroundResource(R.drawable.tag_bg);
        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.tag_text_color);
        textview.setTextColor(csl);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    v.setSelected(false);
                    for (int i = 0; i < backData.size(); i++) {
                        if (backData.get(i).equals(((TextView) v).getText().toString())) {
                            backData.remove(i);
                            break;
                        }
                    }
                } else {
                    v.setSelected(true);
                    backData.add(((TextView) v).getText().toString());
                }
            }
        });
        textview.setSelected(selected);
        wordWrapView.addView(textview);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.v_blank:
                Editable text = etInput.getText();
                if (text != null && !text.toString().equals("")) {
                    addView(text.toString(), true);
                    backData.add(text.toString());
                    etInput.setText("");
                }
                break;
            case R.id.txTitleRig:
                intent.putStringArrayListExtra("tags", backData);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
