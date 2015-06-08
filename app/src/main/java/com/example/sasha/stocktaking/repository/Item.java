package com.example.sasha.stocktaking.repository;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sasha on 06.06.15.
 */
@DatabaseTable(tableName = "Items")
public class Item implements Serializable {
    public static final long serialVersionUID = -7060210544600464481L;
    public  enum State {BAD,MIDDLE,GOOD};
    @DatabaseField(id = true, canBeNull = false)
    private int id;
    @DatabaseField()
    private String title;
    @DatabaseField()
    private String note;
    @DatabaseField(dataType = DataType.ENUM_INTEGER)
    private State state;
    @DatabaseField
    private int invNum;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Place place;

    @DatabaseField(dataType = DataType.DATE)
    private Date changed;

    public Item() {
    }

    public Item(String title, String note, State state, int invNum, Place place, Date changed) {
        this.title = title;
        this.note = note;
        this.state = state;
        this.invNum = invNum;
        this.place = place;
        this.changed = changed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getInvNum() {
        return invNum;
    }

    public void setInvNum(int invNum) {
        this.invNum = invNum;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", state=" + state +
                ", invNum=" + invNum +
                ", place=" + place +
                ", changed=" + changed +
                '}';
    }
}
