package com.benchu.lu.server;

import com.benchu.lu.LuLaunch;
import com.benchu.lu.core.event.ExposeEvent;
import com.benchu.lu.core.event.LuEventDispatcher;

/**
 * @author benchu
 * @version 2017/3/10.
 */
public class ExposeApi {
    public static void expose(Object targetBean) {
        LuLaunch.getInstance().launch();
        LuEventDispatcher.dispatch(new ExposeEvent(targetBean));
    }
}
