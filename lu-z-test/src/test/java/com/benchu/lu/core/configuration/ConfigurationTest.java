package com.benchu.lu.core.configuration;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.benchu.lu.common.constans.ConfConstants;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class ConfigurationTest {
    @Test
    public void test(){
        Properties properties = new Properties();
        properties.put(ConfConstants.KEY_BOSS_THREADS, "19");
        properties.put(ConfConstants.KEY_REQUEST_TIMEOUT, "1999");
        Configuration configuration = Configuration.load(properties);
        Assert.assertEquals(configuration.getBossThreads(),19);
        Assert.assertEquals(configuration.getRequestTimeout(),1999);
        Assert.assertEquals(configuration.getConnectionTimeout(),ConfConstants.DEFAULT_CONNECTION_TIMEOUT);
    }
}
