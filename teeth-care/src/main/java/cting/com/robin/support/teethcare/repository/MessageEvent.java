package cting.com.robin.support.teethcare.repository;

import java.util.ArrayList;

import cting.com.robin.support.teethcare.models.IRecord;

public class MessageEvent<T extends IRecord> {
    private String message;
    private boolean isFinish = false;
    private ArrayList<T> list;

    public MessageEvent() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean loadFinish) {
        this.isFinish = loadFinish;
    }
}
