package org.learn;

import di.annotation.AutoConfiguration;
import di.annotation.Configure;

import java.util.Collection;

@AutoConfiguration
public class AutoWebConfiguration {
    @Configure
    public void configure(Collection<Object> beans) {
        System.out.println("==========================================================================");
        System.out.println("Web Configuration");
        for (Object bean : beans) {
            System.out.println(bean.getClass().getName());
        }
        System.out.println("==========================================================================");
    }
}
