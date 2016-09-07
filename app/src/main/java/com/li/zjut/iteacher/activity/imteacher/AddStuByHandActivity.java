package com.li.zjut.iteacher.activity.imteacher;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStuByHandActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stu_by_hand);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.add_student), true);
        initView();
    }

    private void initView() {
        TextView txTitleRig = (TextView) findViewById(R.id.txTitleRig);
        txTitleRig.setText(getString(R.string.add));
        txTitleRig.setVisibility(View.VISIBLE);
        txTitleRig.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txTitleRig:

                judge();
                break;
            default:
                break;
        }
    }

    /*去后台确认当前电话号码是否是用户*/
    private void judge() {
        /*如果是用户，就用popwindow显示用户信息，而后确认添加*/
        showPopWindow();
    }

    private void showPopWindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_stu_show_stu_info, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                getWindow().getDecorView().getWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sure:
                        window.dismiss();
                        break;
                    case R.id.cancel:
                        window.dismiss();
                        break;
                    default:
                        break;
                }
            }
        };
        view.findViewById(R.id.sure).setOnClickListener(listener);
        view.findViewById(R.id.cancel).setOnClickListener(listener);
        /*设置用户图片姓名*/
        CircleImageView pic = (CircleImageView) view.findViewById(R.id.pic);
        TextView name = (TextView) view.findViewById(R.id.name);
        pic.setImageResource(R.mipmap.img_pic_test);
        name.setText("老朱");

        window.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.5f);

        //添加pop窗口关闭事件
        window.setOnDismissListener(new poponDismissListener());
        window.showAtLocation(AddStuByHandActivity.this.findViewById(R.id.view),
                Gravity.CENTER_VERTICAL, 0, 0);
    }

    /*监听popwindow的dismiss事件*/
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /*设置屏幕背景透明度*/
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
