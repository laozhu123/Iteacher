package com.li.zjut.iteacher.common.contacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.li.zjut.iteacher.bean.address.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/2.
 */
public class Contacts {

    /*
     * 读取联系人的信息
     */
    public static List<People> readAllContacts(Context context) {
        List<People> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;

        if(cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while(cursor.moveToNext()) {

            String contactId = cursor.getString(contactIdIndex);
            String name = cursor.getString(nameIndex);

            People people = new People();
            people.setName(name);

            /*
             * 查找该联系人的phone信息
             */
            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            int phoneIndex = 0;
            if(phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while(phones.moveToNext()) {
                String phoneNumber = phones.getString(phoneIndex);
                people.setPhone(phoneNumber);
                break;
            }
            list.add(people);
        }

        return list;
    }
}
