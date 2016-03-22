package serivce;


import com.little.pang.boring.code.serivce.SpringCacheAnnotation;
import com.little.pang.boring.code.serivce.cache.SimpleCacheImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by jaky on 3/21/16.
 */
@Service
public class SpringCacheAnnotationTest {

    private static Logger logger = LoggerFactory.getLogger(SpringCacheAnnotationTest.class);

    private SpringCacheAnnotation springCacheAnnotation;

    private SimpleCacheImpl simpleCache;

    private SimpleCacheManager simpleCacheManager;

    @Before
    public void before() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("context.xml");
        springCacheAnnotation = ac.getBean(SpringCacheAnnotation.class);
        simpleCache = ac.getBean(SimpleCacheImpl.class);
        simpleCacheManager = ac.getBean(SimpleCacheManager.class);
    }

    @Test
    public void loadTest() {
        String value = springCacheAnnotation.load("little");
        logger.info("little value is {}", value);

        String cacheValue = springCacheAnnotation.load("little");
        logger.info("little value is {}", cacheValue);

    }
}
