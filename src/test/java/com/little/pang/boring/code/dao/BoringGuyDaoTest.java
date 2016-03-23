package com.little.pang.boring.code.dao;

import com.little.pang.boring.code.model.BoringGuy;
import com.little.pang.boring.code.serivce.SpringCacheAnnotation;
import com.little.pang.boring.code.serivce.cache.SimpleCacheImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by jaky on 3/23/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context.xml")
public class BoringGuyDaoTest {

    private static Logger logger = LoggerFactory.getLogger(BoringGuyDaoTest.class);

    @Resource
    private BoringGuyDao boringGuyDao;


   /* @Before
    public void before() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("context.xml");
        boringGuyDao = ac.getBean(BoringGuyDao.class);
    }*/

    @Test
    public void insertOneTest() {
        logger.info("insert one test result {}", boringGuyDao.insertOne(new BoringGuy("jaky.wang")));
    }

}
