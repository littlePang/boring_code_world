package com.little.pang.boring.code.dao;

import com.little.pang.boring.code.model.BoringGuy;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by jaky on 3/23/16.
 */
@Repository
public class BoringGuyDao {
    @Resource
    private SqlSession sqlSession;

    public int insertOne(BoringGuy boringGuy) {
        return sqlSession.insert("BoringGuyMapper.insertOne", boringGuy);
    }
}
