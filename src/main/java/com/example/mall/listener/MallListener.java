package com.example.mall.listener;

import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Classname MallListener
 * @Description
 * @Date 2022-08-23 16:14
 * @Created by Yang Yi-zhou
 */
@WebListener
public class MallListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String configPath = "config.properties";
        Properties properties = new Properties();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(configPath);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ServletContext servletContext = servletContextEvent.getServletContext();

        //后台地址
        String server_host = properties.getProperty("server_host");
        System.out.println("server_host = " + server_host);
        servletContext.setAttribute("server_host", server_host);

        //前台地址
        String front_host = properties.getProperty("front_host");
        System.out.println("front_host = " + front_host);
        servletContext.setAttribute("front_host", front_host);
        //前台地址
        String img_host = properties.getProperty("img_host");
        System.out.println("img_host = " + img_host);
        servletContext.setAttribute("img_host", img_host);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
