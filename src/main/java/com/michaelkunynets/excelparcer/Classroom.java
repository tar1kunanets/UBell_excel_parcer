package com.michaelkunynets.excelparcer;

import org.json.simple.JSONObject;
import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.logging.Logger;

public class Classroom {
    private Date date;
    private String auditorium;
    private String subject;
    private String subjectType;
    private Integer subjectNumber;
    private String teacher;
    private String group;
    private boolean Empty = false;

    public boolean isEmpty() {
        return Empty;
    }

    public void setEmpty(boolean empty) {
        Empty = empty;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }


    public void setSubjectNumber(String subjectNumber) {
        this.subjectNumber = Integer.valueOf(subjectNumber.substring(0, 1));
    }


    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public void setGroup(String group) {
        this.group = group;
    }

    public String DataToTable() {
        if (Empty)
            return ("Empty");
        else
            return ("Дата: " + date + "\n Аудиторія: " + auditorium + "\n Пара: " + subjectNumber + "\n Викладач:"
                    + teacher + "\n Групи: " + group + "\n Предмет: " + subject + "\n Тип предмету: " + subjectType);

    }

    public JSONObject DataToServer() {
        JSONObject object = new JSONObject();
        object.put("date", date);
        object.put("auditorium", auditorium);
        object.put("subjectNum", subjectNumber);
        object.put("teacher", teacher);
        object.put("groups", group);
        object.put("subject", subject);
        object.put("subjectType", subjectType);
        return object;
    }
}
