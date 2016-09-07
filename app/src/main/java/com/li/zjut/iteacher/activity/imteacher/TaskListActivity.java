package com.li.zjut.iteacher.activity.imteacher;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.imteacher.fragment.TaskFinishFragment;
import com.li.zjut.iteacher.activity.imteacher.fragment.TaskGoingFragment;

public class TaskListActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager fm;
    private TaskFinishFragment tasrkFinishFragment;
    private TaskGoingFragment taskGoingFragment;

    private TextView txLeft, txRig;
    private View lineLeft, lineRig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.task), true);
        initView();
        initData();
    }

    private void initView() {
        txLeft = (TextView) findViewById(R.id.tx_left);
        txRig = (TextView) findViewById(R.id.tx_rig);
        lineLeft = findViewById(R.id.line_left);
        lineRig = findViewById(R.id.line_rig);
        txLeft.setText(getString(R.string.going));
        txRig.setText(getString(R.string.finish));
        txRig.setOnClickListener(this);
        txLeft.setOnClickListener(this);
        txLeft.setSelected(true);
        lineLeft.setSelected(true);
    }

    private void initData() {

        //添加fragment
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        tasrkFinishFragment = new TaskFinishFragment();
        taskGoingFragment = new TaskGoingFragment();
        ft.add(R.id.fragment, taskGoingFragment).add(R.id.fragment, tasrkFinishFragment).commit();
        showFrag1(true);
    }

    /*设置那个fragment显示 */
    private void showFrag1(boolean visible) {
        FragmentTransaction ft = fm.beginTransaction();
        if (visible) {
            ft.hide(tasrkFinishFragment);
            ft.show(taskGoingFragment);
        } else {
            ft.show(tasrkFinishFragment);
            ft.hide(taskGoingFragment);
        }
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_left:
                leftOrRight(true);
                break;
            case R.id.tx_rig:
                leftOrRight(false);
                break;
            case R.id.left_img:
                finish();
                break;
            default:
                break;
        }
    }

    /*设置选择项的背景颜色*/
    private void leftOrRight(boolean isLeft) {
        if (isLeft && !txLeft.isSelected()) {
            txLeft.setSelected(true);
            lineLeft.setSelected(true);
            txRig.setSelected(false);
            lineRig.setSelected(false);
            showFrag1(true);
            return;
        }
        if (!isLeft && !txRig.isSelected()) {
            txLeft.setSelected(false);
            lineLeft.setSelected(false);
            txRig.setSelected(true);
            lineRig.setSelected(true);
            showFrag1(false);
        }
    }
}
