package com.example.mysns.dao;

import java.util.List;

public interface DataDao {

    public <T> List<T> selectList(String statement);

    public <T> List<T> selectList(String statement, Object parameterObject);

    public <T> T selectOne(String statement, Object parameterObject);

    public <T> T selectOne(String statement);

    public int insert(String statement, Object parameterObject);

    public int delete(String statement, Object parameterObject);

    public int update(String statement, Object parameterObject);

}
