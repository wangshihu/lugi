package com.benchu.lu;

import java.util.List;

import com.benchu.lu.common.proxy.JavaProxyFactory;
import com.benchu.lu.common.proxy.ProxyFactory;
import com.benchu.lu.core.configuration.Configuration;
import com.benchu.lu.network.Transport;
import com.benchu.lu.network.netty.NettyCodecAdaptor;
import com.benchu.lu.network.netty.NettyNioTransport;
import com.benchu.lu.network.serialize.LubboCodec;
import com.benchu.lu.spi.Directory;
import com.benchu.lu.spi.Module;

/**
 * @author benchu
 * @version 2016/10/30.
 */
public class LuContext {
    private Transport transport;
    private ProxyFactory proxyFactory;

    private Configuration configuration;
    private List<Module> modules;
    private Directory directory;

    public LuContext(Configuration configuration, List<Module> modules) {
        this.configuration = configuration;
        this.modules = modules;
    }

    public void init() {
        configuration = Configuration.load();
        //proxy
        proxyFactory = new JavaProxyFactory();
        //codec
        LubboCodec codec = new LubboCodec();
        NettyCodecAdaptor adaptor = new NettyCodecAdaptor();
        adaptor.setCodec(codec);
        //transport
        this.transport = new NettyNioTransport(adaptor, configuration);

        for (Module module : modules) {
            module.init(this);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public ProxyFactory getProxyFactory() {
        return proxyFactory;
    }

    public Transport getTransport() {
        return transport;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }
}
