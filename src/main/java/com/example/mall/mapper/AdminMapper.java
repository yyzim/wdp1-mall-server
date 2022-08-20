package com.example.mall.mapper;

import com.example.mall.model.po.AdminPO;
import com.example.mall.model.vo.AllAdminsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname AdminMapper
 * @Description
 * @Date 2022-08-19 17:52
 * @Created by Yang Yi-zhou
 */
public interface AdminMapper {
    AdminPO selectAdminPOByAccountAndPassword(@Param("account") String account, @Param("password") String password);

    String selectNameByAccount(@Param("account") String account);

    List<AdminPO> selectAllAdminsFromAdmin();

    List<AdminPO> selectAdminsFromAdminByAccountAndName(@Param("account") String account, @Param("name") String name);

    Integer insertIntoAdmin(@Param("admin") AdminPO admin);

    Integer deleteAdminsById(@Param("id") Integer id);

    AdminPO selectAdminFromAdminById(@Param("id") Integer id);

    Integer updateAdminById(@Param("admin") AdminPO admin);

    Integer updateAdminPasswordByNameAndPassword(@Param("name") String name, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);
}
