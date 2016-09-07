package com.li.zjut.iteacher.common.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.li.zjut.iteacher.bean.mylesson.Curriculum;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        // 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
        // mFactory);
        // 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 添加课程
     *
     * @param ls
     */
    public void add(List<Curriculum> ls) {
        db.beginTransaction(); // 开始事务
        try {
            for (Curriculum curriculum : ls) {
                db.execSQL(
                        "INSERT INTO lesson VALUES(null,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{curriculum.getId(),
                                curriculum.getCourseTimeId(), curriculum.getText(),
                                curriculum.getPlace(), curriculum.getBegin(), curriculum.getEnd(),
                                curriculum.getFromWeek(), curriculum.getEndWeek(), curriculum.getSingle_double(),
                                curriculum.getWeekday(),curriculum.getCourseId()});
            }
            db.setTransactionSuccessful(); // 设置事务成功完成
        } finally {
            db.endTransaction(); // 结束事务
        }
    }

//    /**
//     * update Agenda's age
//     *
//     * @param curriculum
//     */
//    public void updateAgenda(Curriculum curriculum) {
//        ContentValues cv = new ContentValues();
//        cv.put("notification", curriculum.getNotification());
//        cv.put("repeat1", Agenda.getRepeat1());
//        cv.put("detail", Agenda.getDetail());
//        cv.put("begintime", Agenda.getBegintime());
//        cv.put("tag", Agenda.getTag());
//        cv.put("flag", Agenda.getFlag());
//        cv.put("types", Agenda.getType());
//        db.update("agenda", cv, "schedule_id = ?", new String[]{Agenda.getSchedule_id() + ""});
//    }

    /**
     * delete就的lesson表
     */
    public void deleteOld() {
        db.delete("lesson", null, null);
    }


    public void removeOneLesson(int Lid) {
        db.delete("lesson", "Lid = ?", new String[]{Lid + ""});
    }

    public void removeOneClass(int CourseTimeId) {
        db.delete("lesson", "CourseTimeId = ?", new String[]{CourseTimeId+""});
    }

    public void removeOneCur(String CourseId) {
        db.delete("lesson", "CourseId = ?", new String[]{CourseId});
    }

    /**
     * query all lesson, return list
     *
     * @return List<Curriculum>
     */
    public List<Curriculum> query() {
        ArrayList<Curriculum> curriculumArrayList = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Curriculum curriculum = new Curriculum();
            curriculum.setId(c.getInt(c.getColumnIndex("Lid")));
            curriculum.setCourseTimeId(c.getInt(c.getColumnIndex("CourseTimeId")));
            curriculum.setText(c.getString(c.getColumnIndex("LN")));
            curriculum.setPlace(c.getString(c.getColumnIndex("Place")));
            curriculum.setBegin(c.getInt(c.getColumnIndex("Begin")));
            curriculum.setEnd(c.getInt(c.getColumnIndex("End")));
            curriculum.setFromWeek(c.getInt(c.getColumnIndex("BeginWeek")));
            curriculum.setEndWeek(c.getInt(c.getColumnIndex("EndWeek")));
            curriculum.setSingle_double(c.getInt(c.getColumnIndex("SingleDouble")));
            curriculum.setWeekday(c.getInt(c.getColumnIndex("WeekDay")));
            curriculum.setCourseId(c.getInt(c.getColumnIndex("CourseId")));
            curriculumArrayList.add(curriculum);
        }
        c.close();
        return curriculumArrayList;
    }

    /**
     * query all Agendas, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM lesson", new String[0]);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
        helper.close();
    }
}