package com.li.zjut.iteacher.common.email;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.nononsenseapps.filepicker.FilePickerActivity;

/**
 * Created by michael on 16/6/2.
 */
public class UIUtil
{

    /**
     * 文件浏览器
     * */
    public static void startFilePickerActivity(Context context, int requestDirectoryCode)
    {
        Intent i = new Intent(context, FilePickerActivity.class);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        ((Activity)context).startActivityForResult(i, requestDirectoryCode);
    }


}
