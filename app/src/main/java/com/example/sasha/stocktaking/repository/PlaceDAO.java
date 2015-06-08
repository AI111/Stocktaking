package com.example.sasha.stocktaking.repository;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sasha on 1/26/15.
 */
public class PlaceDAO extends BaseDaoImpl<Place, Integer> {

    protected PlaceDAO(ConnectionSource connectionSource,
                       Class<Place> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Place> getAllCities() throws SQLException {
        return this.queryForAll();
    }
}

