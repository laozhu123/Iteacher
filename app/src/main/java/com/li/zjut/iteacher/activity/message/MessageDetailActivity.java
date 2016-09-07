package com.li.zjut.iteacher.activity.message;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.message.CommentListAdapter;
import com.li.zjut.iteacher.bean.message.CommentContent;
import com.li.zjut.iteacher.common.Utils;

import java.util.ArrayList;
import java.util.List;

public class MessageDetailActivity extends BaseActivity implements View.OnClickListener {

    WebView webView;
    List<CommentContent> mDatas = new ArrayList<>();
    TextView tvMore, send, cancel;
    ListView lv;
    CommentListAdapter adapter;
    EditText content;
    View hidden,background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.message_detail), true);
        adapter = new CommentListAdapter(mDatas, this);

        initView();
        getDataFromNetwork();
    }

    private void getDataFromNetwork() {
        webView = (WebView) findViewById(R.id.webView);
        //WebView加载web资源
        webView.loadUrl("http://www.baidu.com");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });


        mDatas.add(new CommentContent("laoli", "nihaoma !!", "2016/6/13 12:30", 12, 5));
        mDatas.add(new CommentContent("laoli", "nihaoma !!", "2016/6/13 12:30", 12, 5));
        mDatas.add(new CommentContent("laoli", "nihaoma !!", "2016/6/13 12:30", 12, 5));
        mDatas.add(new CommentContent("laoli", "nihaoma !!", "2016/6/13 12:30", 12, 5));
        mDatas.add(new CommentContent("laoli", "nihaoma !!", "2016/6/13 12:30", 12, 5));
        mDatas.add(new CommentContent("laoli", "nihaoma !!", "2016/6/13 12:30", 12, 5));
        adapter.notifyDataSetChanged();
        Utils.setListViewHeightBasedOnChildren(lv);
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        findViewById(R.id.comment).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);

        tvMore = (TextView) findViewById(R.id.more);
        tvMore.setOnClickListener(this);
        send = (TextView) findViewById(R.id.send);
        cancel = (TextView) findViewById(R.id.cancel);
        content = (EditText) findViewById(R.id.content);
        hidden = findViewById(R.id.hidden);
        background = findViewById(R.id.background);
        background.setOnClickListener(this);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);
        send.setText(getString(R.string.message_send));
        cancel.setText(getString(R.string.cancel));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Toast.makeText(getApplicationContext(), "点赞了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.comment:
                hidden.setVisibility(View.VISIBLE);
                break;
            case R.id.more:
                mDatas.add(new CommentContent("laoli", "nihaoma !!", "2016/6/13 12:30", 12, 5));
                adapter.notifyDataSetChanged();
                Utils.setListViewHeightBasedOnChildren(lv);
                break;
            case R.id.background:
            case R.id.cancel:
                hidden.setVisibility(View.GONE);
                break;
            case R.id.send:
                if (content.getText().toString()==null||content.getText().toString().equals("")){
                    Toast.makeText(this,getString(R.string.moretext),Toast.LENGTH_SHORT).show();
                }else{
//                    send();
                    hidden.setVisibility(View.GONE);
                }
                break;
        }
    }
}
