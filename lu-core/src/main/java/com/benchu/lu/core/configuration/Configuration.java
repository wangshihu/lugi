package com.benchu.lu.core.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benchu.lu.common.constans.ConfConstants;
import com.benchu.lu.common.exception.LuException;
import com.benchu.lu.utils.PropertyUtil;
import com.benchu.lu.utils.ValueUtil;
import com.google.common.base.Preconditions;

/**
 * @author benchu
 * @version 2017/3/9.
 */
public class Configuration implements ConfConstants {
    private static Logger logger = LoggerFactory.getLogger(Configuration.class);

    private static Configuration instance;

    private long requestTimeout;
    private long serverHandleTimeout;
    private int connectionTimeout;
    private int bossThreads;
    private int workerThreads;
    private int serverMaxThreadNum;
    private int serverCoreThreadNum;
    private int port;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    public static Configuration load() {
        InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("lu.properties");
        Properties properties = new Properties();
        if (input == null) {
            logger.warn("cannot find {lu.properties},make sure the file at classPath");
        } else {
            try {
                properties.load(input);
            } catch (IOException e) {
                throw new LuException("load configuration error", e);
            }
        }
        return load(properties);
    }

    public static Configuration load(Properties properties) {
        Configuration result = new Configuration();
        Field[] fields = Configuration.class.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Conf conf = field.getDeclaredAnnotation(Conf.class);
            NotConf notConf = field.getDeclaredAnnotation(NotConf.class);
            if (notConf == null || conf != null) {
                field.setAccessible(true);
                try {
                    Type type = field.getGenericType();
                    ConfEntity entity = getConfEntity(field, conf);
                    Object value = PropertyUtil.getValueByType(properties, entity.getKey(), type);
                    field.set(result, ValueUtil.getNotNullValue(value, entity.getDefaultValue()));
                } catch (IllegalAccessException e) {
                    throw new LuException("load configuration error:", e);
                }
            }

        }
        return result;
    }

    private static ConfEntity getConfEntity(Field field, Conf conf) {
        ConfEntity entity = conf == null ? ConfEntity.none : conf.value();
        if (entity == ConfEntity.none) {
            entity = ConfEntity.getEntityByName(field.getName());
            Preconditions.checkNotNull(entity, "cannot find ConfEntity with " + field.getName());
        }
        return entity;
    }

    public int getWorkerThreads() {
        return workerThreads;
    }

    public int getBossThreads() {
        return bossThreads;
    }

    public long getServerHandleTimeout() {
        return serverHandleTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public long getRequestTimeout() {
        return requestTimeout;
    }

    public int getServerMaxThreadNum() {
        return serverMaxThreadNum;
    }

    public int getServerCoreThreadNum() {
        return serverCoreThreadNum;
    }

    public int getPort() {
        return port;
    }
}
