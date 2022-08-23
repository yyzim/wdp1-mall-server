package com.example.mall.mapper;

import com.example.mall.model.po.AdminPO;
import com.example.mall.model.po.GoodsPO;
import com.example.mall.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Classname TestAdminMapper
 * @Description
 * @Date 2022-08-19 18:55
 * @Created by Yang Yi-zhou
 */
public class TestAdminMapper {
    //    selectStockNumFromTableGoodsSpecByGoodsId
    @Test
    public void testSelectStockNumFromTableGoodsSpecByGoodsId() {

        for (int i = 0; i < 10; i++) {
            SqlSession session = MybatisUtils.openSession();
            GoodsMapper mapper = session.getMapper(GoodsMapper.class);
            long s = System.currentTimeMillis();
            Integer num = mapper.selectStockNumFromTableGoodsSpecByGoodsId(19);
            long e = System.currentTimeMillis();
            System.out.println("num = " + num + ", time = " + (e - s));
            session.close();
        }
    }

    @Test
    public void testSelectGoodsFromTableGoodsByTypeId() {
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        List<GoodsPO> goodsPOS = mapper.selectGoodsFromTableGoodsByTypeId(1);
        System.out.println("goodsPOS = " + goodsPOS);
    }

    @Test
    public void testSelectAdminFromAdminById() {
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);

        AdminPO adminPO = mapper.selectAdminFromAdminById(1001);
        System.out.println("adminPO = " + adminPO);
    }

    @Test
    public void testSelectAdminPOByAccountAndPassword() {
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);

        AdminPO adminPO = mapper.selectAdminPOByAccountAndPassword("admin", "admin");

        System.out.println("adminPO = " + adminPO);
    }

    @Test
    public void testSelectNameByAccount() {
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        String admin = mapper.selectNameByAccount("admin");
        System.out.println("admin = " + admin);

    }

    @Test
    public void testSelectAllAdminsFromAdmin() {
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        List<AdminPO> adminPOS = mapper.selectAllAdminsFromAdmin();
        System.out.println("adminPOS = " + adminPOS);

    }

    //    selectAdminsFromAdminByAccountAndName
    @Test
    public void testSelectAdminsFromAdminByAccountAndName() {
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        List<AdminPO> admin = mapper.selectAdminsFromAdminByAccountAndName("admin", "");
        System.out.println("admin = " + admin);

    }

    //    insertIntoAdmin
    @Test
    public void testInsertIntoAdmin() {
//        SqlSession session = MybatisUtils.openSession();
//        AdminMapper mapper = session.getMapper(AdminMapper.class);
//        Integer integer = mapper.insertIntoAdmin(new AdminPO(null, "TestAdmin1", "TestAdmin1", "123456"));
//        session.commit();
//        session.close();

    }
}
