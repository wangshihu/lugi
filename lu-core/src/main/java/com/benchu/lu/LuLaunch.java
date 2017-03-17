package com.benchu.lu;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benchu.lu.core.configuration.Configuration;
import com.benchu.lu.spi.Module;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class LuLaunch {
    private static Logger logger = LoggerFactory.getLogger(LuLaunch.class);
    private LuContext context;
    public static LuLaunch instance;

    public static LuLaunch getInstance() {
        if (instance == null) {
            instance = new LuLaunch();
        }
        return instance;
    }

    public LuContext launch() {
        if (context != null) {
            return context;
        }
        ServiceLoader<Module> loader = ServiceLoader.load(Module.class);
        List<Module> modules = new ArrayList<>();
        for (Module module : loader) {
            modules.add(module);
        }
        context = new LuContext(Configuration.getInstance(), modules);
        context.init();
        logger.info("lu launch has completed...");
        return context;
    }

    public LuContext getContext() {
        return context;
    }
}
