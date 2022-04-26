package com.example.mysns.dao;



import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataDaoImpl implements DataDao {

    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public DataDaoImpl(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public <T> List<T> selectList(String statement){
        return sqlSessionTemplate.selectList(statement);
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameterObject) {
        return sqlSessionTemplate.selectList(statement, parameterObject);
    }

    @Override
    public <T> T selectOne(String statement, Object parameterObject){
        return sqlSessionTemplate.selectOne(statement, parameterObject);
    }

    public <T> T selectOne(String statement){
        return sqlSessionTemplate.selectOne(statement);
    }

    @Override
    public int insert(String statement, Object parameterObject) {
        return sqlSessionTemplate.insert(statement, parameterObject);
    }

    @Override
    public int delete(String statement, Object parameterObject) {
        return sqlSessionTemplate.delete(statement, parameterObject);
    }

    @Override
    public int update(String statement, Object parameterObject) {
        return sqlSessionTemplate.update(statement, parameterObject);
    }
}
