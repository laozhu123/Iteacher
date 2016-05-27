package com.li.zjut.iteacher.widget.draggrid;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.checkIn.TodayLessonsActivity;
import com.li.zjut.iteacher.activity.myLesson.CurTableActivity;
import com.li.zjut.iteacher.bean.DargChildInfo;
import com.li.zjut.iteacher.bean.DragIconInfo;
import com.li.zjut.iteacher.common.StaticData;

import java.util.ArrayList;

/**
 * 类: CustomGroup <p>
 * 描述: TODO <p>
 * 作者: wedcel wedcel@gmail.com<p>
 * 时间: 2015年8月25日 下午6:54:26 <p>
 */

public class CustomGroup extends ViewGroup {

    private CustomAboveView mCustomAboveView;
    private CustomBehindParent mCustomBehindParent;
    private boolean isEditModel = false;
    public static final int COLUMNUM = 4;
    private Context mContext;
    //所有以的list
    private ArrayList<DragIconInfo> allInfoList = new ArrayList<DragIconInfo>();
    /**
     * 显示的带more的list
     */
    private ArrayList<DragIconInfo> homePageInfoList = new ArrayList<DragIconInfo>();
    /**
     * 可展开的list
     */
    private ArrayList<DragIconInfo> expandInfoList = new ArrayList<DragIconInfo>();

    /**
     * 不可展开的list
     */
    private ArrayList<DragIconInfo> onlyInfoList = new ArrayList<DragIconInfo>();

    private InfoEditModelListener editModelListener;


    public interface InfoEditModelListener {
        public void onModelChanged(boolean isEditModel);
    }

    /**
     * 标题: 构造器 <p>
     * 描述: TODO <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:29:30 <p>
     *
     * @param context
     * @param attrs
     */
    public CustomGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutParams upParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        mCustomAboveView = new CustomAboveView(context, this);
        addView(mCustomAboveView, upParams);
        LayoutParams downParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        mCustomBehindParent = new CustomBehindParent(mContext, this);
        addView(mCustomBehindParent, downParams);
        initData();
    }


    public CustomGroup(Context context) {
        this(context, null);
    }

    public InfoEditModelListener getEditModelListener() {
        return editModelListener;
    }

    public void setEditModelListener(InfoEditModelListener editModelListener) {
        this.editModelListener = editModelListener;
    }

    /**
     * 方法: initData <p>
     * 描述: 初始化监听和数据 <p>
     * 参数:  <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:29:40
     */
    private void initData() {

        setCustomViewClickListener(new CustomAboveView.CustomAboveViewClickListener() {

            @Override
            public void onSingleClicked(DragIconInfo iconInfo) {
                // TODO Auto-generated method stub
                dispatchSingle(iconInfo);
            }

            @Override
            public void onChildClicked(DargChildInfo childInfo) {
                // TODO Auto-generated method stub
                dispatchChild((childInfo));
            }
        });

        initIconInfo();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasure = 0;
        int heightMeasure = 0;
        if (isEditModel) {
            mCustomBehindParent.measure(widthMeasureSpec, heightMeasureSpec);
            widthMeasure = mCustomBehindParent.getMeasuredWidth();
            heightMeasure = mCustomBehindParent.getMeasuredHeight();
        } else {
            mCustomAboveView.measure(widthMeasureSpec, heightMeasureSpec);
            widthMeasure = mCustomAboveView.getMeasuredWidth();
            heightMeasure = mCustomAboveView.getMeasuredHeight();
        }
        setMeasuredDimension(widthMeasure, heightMeasure);

    }

    /**
     * 方法: onLayout <p>
     * 描述: TODO<p>
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b       <p/>
     * @see ViewGroup#onLayout(boolean, int, int, int, int) <p>
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isEditModel) {
            int behindHeight = mCustomBehindParent.getMeasuredHeight();
            mCustomBehindParent.layout(l, 0, r, behindHeight + t);
        } else {
            int aboveHeight = mCustomAboveView.getMeasuredHeight();
            mCustomAboveView.layout(l, 0, r, aboveHeight + t);
        }
    }

    /**
     * 方法: initIconInfo <p>
     * 描述: 初始化数据 <p>
     * 参数:  <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:33:14
     */
    private void initIconInfo() {


        allInfoList.clear();
        allInfoList.addAll(StaticData.iconInfoList);
        getPageInfoList();

        refreshIconInfo();
    }


    /**
     * 方法: getPageInfoList <p>
     * 描述: 初始化显示 <p>
     * 参数:  <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:37:33
     */
    private void getPageInfoList() {
        homePageInfoList.clear();
        int count = 0;
        for (DragIconInfo info : allInfoList) {
            if (count < 13) {
                homePageInfoList.add(info);
                count++;
            } else {
                break;
            }
        }
    }

    /**
     * 方法: refreshIconInfo <p>
     * 描述: 刷新信息 <p>
     * 参数:  <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:38:11
     */
    private void refreshIconInfo() {
        judeHomeInfoValid();

        ArrayList<DragIconInfo> moreInfo = getMoreInfoList(allInfoList, homePageInfoList);
        expandInfoList = getInfoByType(moreInfo, DragIconInfo.CATEGORY_EXPAND);
        onlyInfoList = getInfoByType(moreInfo, DragIconInfo.CATEGORY_ONLY);
        setIconInfoList(homePageInfoList);
    }


    /**
     * 方法: judeHomeInfoValid <p>
     * 描述: 判断下显示里面是否包含更多 或者看下是否是最后一个 固定更多的位置 <p>
     * 参数:  <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:38:37
     */
    private void judeHomeInfoValid() {
        boolean hasMoreInfo = false;
        int posit = 0;
        for (int index = 0; index < homePageInfoList.size(); index++) {
            DragIconInfo tempInfo = homePageInfoList.get(index);
            if (tempInfo.getId() == CustomAboveView.MORE) {
                hasMoreInfo = true;
                posit = index;
                break;
            }
        }
        if (!hasMoreInfo) {
            //没有更多 增加
            homePageInfoList.add(new DragIconInfo(CustomAboveView.MORE, "更多", R.mipmap.icon_home_more, 0, new ArrayList<DargChildInfo>()));
        } else {
            if (posit != homePageInfoList.size() - 1) {
                //不是最后一个
                DragIconInfo moreInfo = homePageInfoList.remove(posit);
                homePageInfoList.add(moreInfo);
            }
        }
    }


    /**
     * 方法: getInfoByType <p>
     * 描述: TODO <p>
     * 参数: @param moreInfo
     * 参数: @param categorySpt
     * 参数: @return <p>
     * 返回: ArrayList<DragIconInfo> <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午6:50:25
     */
    private ArrayList<DragIconInfo> getInfoByType(ArrayList<DragIconInfo> moreInfo, int categorySpt) {
        ArrayList<DragIconInfo> typeList = new ArrayList<DragIconInfo>();
        for (DragIconInfo info : moreInfo) {
            if (info.getCategory() == categorySpt) {
                typeList.add(info);
            }
        }
        return typeList;
    }


    public void setCustomViewClickListener(CustomAboveView.CustomAboveViewClickListener gridViewClickListener) {
        mCustomAboveView.setGridViewClickListener(gridViewClickListener);
    }

    /**
     * 方法: setIconInfoList <p>
     * 描述: 设置信息 <p>
     * 参数: @param iconInfoList <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午6:45:55
     */
    public void setIconInfoList(ArrayList<DragIconInfo> iconInfoList) {
        mCustomAboveView.refreshIconInfoList(iconInfoList);
        mCustomBehindParent.refreshIconInfoList(iconInfoList);
    }


    public boolean isEditModel() {
        return isEditModel;
    }

    public void cancleEidtModel() {
        setEditModel(false, 0);
    }


    public void setEditModel(boolean isEditModel, int position) {
        this.isEditModel = isEditModel;
        if (isEditModel) {
            mCustomAboveView.setViewCollaps();
            mCustomAboveView.setVisibility(View.GONE);
            mCustomBehindParent.notifyDataSetChange(mCustomAboveView.getIconInfoList());
            mCustomBehindParent.setVisibility(View.VISIBLE);
            mCustomBehindParent.drawWindowView(position, mCustomAboveView.getFirstEvent());
        } else {
            homePageInfoList.clear();
            homePageInfoList.addAll(mCustomBehindParent.getEditList());
            mCustomAboveView.refreshIconInfoList(homePageInfoList);
            mCustomAboveView.setVisibility(View.VISIBLE);
            mCustomBehindParent.setVisibility(View.GONE);
            if (mCustomBehindParent.isModifyedOrder()) {
                mCustomBehindParent.cancleModifyOrderState();
            }
            mCustomBehindParent.resetHidePosition();
        }
        if (editModelListener != null) {
            editModelListener.onModelChanged(isEditModel);
        }
    }


    public void sendEventBehind(MotionEvent ev) {
        mCustomBehindParent.childDispatchTouchEvent(ev);
    }

    /**
     * 方法: getMoreInfoList <p>
     * 描述: TODO <p>
     * 参数: @param allInfoList
     * 参数: @param homePageInfoList
     * 参数: @return <p>
     * 返回: ArrayList<DragIconInfo> <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午6:57:06
     */
    private ArrayList<DragIconInfo> getMoreInfoList(ArrayList<DragIconInfo> allInfoList, ArrayList<DragIconInfo> homePageInfoList) {
        ArrayList<DragIconInfo> moreInfoList = new ArrayList<DragIconInfo>();
        moreInfoList.addAll(allInfoList);
        moreInfoList.removeAll(homePageInfoList);
        return moreInfoList;
    }


    /**
     * 方法: deletHomePageInfo <p>
     * 描述: TODO <p>
     * 参数: @param dragIconInfo <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午6:56:19
     */
    public void deletHomePageInfo(DragIconInfo dragIconInfo) {
        homePageInfoList.remove(dragIconInfo);
        mCustomAboveView.refreshIconInfoList(homePageInfoList);
        int category = dragIconInfo.getCategory();
        switch (category) {
            case DragIconInfo.CATEGORY_ONLY:
                onlyInfoList.add(dragIconInfo);
                break;
            case DragIconInfo.CATEGORY_EXPAND:
                expandInfoList.add(dragIconInfo);
                break;
            default:
                break;
        }
        allInfoList.remove(dragIconInfo);
        allInfoList.add(dragIconInfo);
    }


    /**
     * 方法: dispatchChild <p>
     * 描述: 点击child <p>
     * 参数: @param childInfo <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:30:58
     */
    protected void dispatchChild(DargChildInfo childInfo) {
        if (childInfo == null) {
            return;
        }
        Toast.makeText(mContext, "点击了item" + childInfo.getName() + childInfo.getId(), Toast.LENGTH_SHORT).show();

    }


    /**
     * 方法: dispatchSingle <p>
     * 描述: 没child的点击 <p>
     * 参数: @param dragInfo <p>
     * 返回: void <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:30:40
     */
    public void dispatchSingle(DragIconInfo dragInfo) {
        if (dragInfo == null) {
            return;
        }
        jump(dragInfo.getId());
    }

    private void jump(int id) {

        switch (id) {
            case 1:
                mContext.startActivity(new Intent(mContext, TodayLessonsActivity.class));
                break;
            case 2:
                mContext.startActivity(new Intent(mContext, CurTableActivity.class));
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 99999:
                break;
        }
    }


    public boolean isValideEvent(MotionEvent ev, int scrolly) {
        return mCustomBehindParent.isValideEvent(ev, scrolly);
    }


    public void clearEditDragView() {
        mCustomBehindParent.clearDragView();
    }


}
