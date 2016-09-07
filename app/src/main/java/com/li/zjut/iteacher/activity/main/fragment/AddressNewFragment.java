package com.li.zjut.iteacher.activity.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.addresslist.AddressActivity;
import com.li.zjut.iteacher.activity.addresslist.ClassGroupActivity;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.activity.addresslist.ApartmentActivity;
import com.li.zjut.iteacher.bean.address.Department;

import java.util.Locale;

/**
 * Created by LaoZhu on 2016/7/11.
 */
public class AddressNewFragment extends Fragment implements View.OnClickListener {

    ListView lvOftenContact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_new, null);
        MainActivity.content.setVisibility(View.VISIBLE);
        initView(view);
        getData();
        return view;
    }

    private void initView(View view) {

        View layout = view.findViewById(R.id.toolbar);
        TextView title = (TextView) layout.findViewById(R.id.title);
        title.setText(getString(R.string.addresslist));

        view.findViewById(R.id.contact_local).setOnClickListener(this);
        view.findViewById(R.id.class_group).setOnClickListener(this);
        view.findViewById(R.id.apartment_group).setOnClickListener(this);
        view.findViewById(R.id.contact_concern).setOnClickListener(this);
        lvOftenContact = (ListView) view.findViewById(R.id.often_contact);
    }

    private void getData() {
        String[] strs = new String[10];
        for (int i = 0; i < strs.length; i++) {
            if (i % 2 == 0) {
                strs[i] = String.format(Locale.getDefault(), "anten", i);
            } else {
                strs[i] = String.format(Locale.getDefault(), "abera", i);
            }
        }
        lvOftenContact.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strs));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            本地联系人
            case R.id.contact_local:
                Department local = new Department();
                local.setName("手机通讯录");
                startActivity(new Intent(getActivity(), AddressActivity.class).putExtra("peoples", local));
                break;
//            班级群组
            case R.id.class_group:
                startActivity(new Intent(getActivity(), ClassGroupActivity.class));
                break;
//            学校部门
            case R.id.apartment_group:
                startActivity(new Intent(getActivity(),ApartmentActivity.class));
                break;
//            特别关注
            case R.id.contact_concern:
                break;
        }
    }
}
