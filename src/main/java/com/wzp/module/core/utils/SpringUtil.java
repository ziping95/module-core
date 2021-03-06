package com.wzp.module.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpringUtil {

    private static SpringUtil springUtil;

    private final ApplicationContext applicationContext;

    public SpringUtil(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void init() {
        springUtil = this;
    }

    public static Object getBean(String name) {
       return springUtil.applicationContext.getBean(name);
    }

    public static void publishEvent(ApplicationEvent applicationEvent) {
        springUtil.applicationContext.publishEvent(applicationEvent);
    }
}
