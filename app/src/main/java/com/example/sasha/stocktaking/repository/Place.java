package com.example.sasha.stocktaking.repository;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by sasha on 05.06.15.
 */
@DatabaseTable(tableName = "Places")
public class Place {
    public enum Type{LABORATORY,AUDITORY,UTILITY_ROOM}
    @DatabaseField(id = true, canBeNull = false)
    private int id;
    @DatabaseField()
    private String title;
    @DatabaseField(dataType = DataType.ENUM_INTEGER)
    Type type;
    @DatabaseField(dataType = DataType.DATE)
    private Date changed;

    @ForeignCollectionField(eager = true)
    public Collection<Item> points = new ArrayList<Item>();
    public Place() {
    }

    public Place(String title, Type type, Date changed) {
        this.title = title;
        this.type = type;
        this.changed = changed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }
}
