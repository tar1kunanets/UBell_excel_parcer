package com.michaelkunynets.excelparcer;

import java.util.Date;

public class Classroom {
    private Date date;
    private String auditorium;
    private String subject;
    private String subjectType;
    private Integer subjectNumber;
    private String teacher;
    private String group;
    private boolean Empty = false;

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

    public void SendData() {
        if (Empty)
            System.out.println("Empty");
        else
            System.out.println("Дата: " + date + "\n Аудиторія: " + auditorium + "\n Пара: " + subjectNumber + "\n Викладач:"
                    + teacher + "\n Групи: " + group + "\n Предмет: " + subject + "\n Тип предмету: " + subjectType);

    }
}
