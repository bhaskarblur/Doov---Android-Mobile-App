package com.blurspace.doov.Models;

import java.util.List;

public class CurrentDreamModel {

    public static class curdreammodel {

        public curdreammodel() { }

        public DreamsModel dreaminfo;
        public List<todoRec> dailytodo;
        public List<todostatRec> dailytodostats;
        public dailyinfo dailyinfo;

        public curdreammodel(DreamsModel dreaminfo, List<todoRec> dailytodo, List<todostatRec> dailytodostats, CurrentDreamModel.dailyinfo dailyinfo) {
            this.dreaminfo = dreaminfo;
            this.dailytodo = dailytodo;
            this.dailytodostats = dailytodostats;
            this.dailyinfo = dailyinfo;
        }

        public DreamsModel getDreaminfo() {
            return dreaminfo;
        }

        public void setDreaminfo(DreamsModel dreaminfo) {
            this.dreaminfo = dreaminfo;
        }

        public List<todoRec> getDailytodo() {
            return dailytodo;
        }

        public void setDailytodo(List<todoRec> dailytodo) {
            this.dailytodo = dailytodo;
        }

        public List<todostatRec> getDailytodostats() {
            return dailytodostats;
        }

        public void setDailytodostats(List<todostatRec> dailytodostats) {
            this.dailytodostats = dailytodostats;
        }

        public CurrentDreamModel.dailyinfo getDailyinfo() {
            return dailyinfo;
        }

        public void setDailyinfo(CurrentDreamModel.dailyinfo dailyinfo) {
            this.dailyinfo = dailyinfo;
        }

    }

    public class todoRec {
        public String todoname;
        public String start_date;

        public String getTodoname() {
            return todoname;
        }

        public void setTodoname(String todoname) {
            this.todoname = todoname;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public todoRec() {
        }

        public todoRec(String todoname, String start_date) {
            this.todoname = todoname;
            this.start_date = start_date;
        }
    }

    public class todostatRec {
        public String date;
        public String task_done;
        public String tasks_count;

        public todostatRec(String date, String task_done, String tasks_count) {
            this.date = date;
            this.task_done = task_done;
            this.tasks_count = tasks_count;
        }

        public todostatRec() {
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTask_done() {
            return task_done;
        }

        public void setTask_done(String task_done) {
            this.task_done = task_done;
        }

        public String getTasks_count() {
            return tasks_count;
        }

        public void setTasks_count(String tasks_count) {
            this.tasks_count = tasks_count;
        }
    }

    public class dailyinfo {
        public String daycount;
        public List<String> tasksdone;
        public String date;


        public dailyinfo(String daycount, List<String> tasksdone, String date) {
            this.daycount = daycount;
            this.tasksdone = tasksdone;
            this.date = date;
        }

        public String getDaycount() {
            return daycount;
        }

        public void setDaycount(String daycount) {
            this.daycount = daycount;
        }

        public List<String> getTasksdone() {
            return tasksdone;
        }

        public void setTasksdone(List<String> tasksdone) {
            this.tasksdone = tasksdone;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}


