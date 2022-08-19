package com.example.mall.util;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Classname MybatisUtils
 * @Description
 * @Date 2022-8-1 20:03
 * @Created by Yang Yi-zhou
 */
public class MybatisUtils {

    static SqlSessionFactory factory;

    static {
        //1 读取配置文件
        InputStream config = null;
        try {
            config = Resources.getResourceAsStream("mybatis-config.xml");
            //2 SqlSessionFactoryBuilder
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            //3 SqlSessionFactory
            factory = builder.build(config);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static SqlSession openSession() {
        return factory.openSession();
    }

}
