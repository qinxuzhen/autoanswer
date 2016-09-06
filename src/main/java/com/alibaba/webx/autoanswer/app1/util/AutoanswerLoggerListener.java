package com.alibaba.webx.autoanswer.app1.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class AutoanswerLoggerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SLF4JBridgeHandler.install();
        Logger log = LoggerFactory.getLogger(ServletContextListener.class);
        log.error("-----------------------------test rootLog---------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        
    }

}
