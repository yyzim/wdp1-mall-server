package com.example.mall.service;

import com.example.mall.model.bo.AddAdminssBO;
import com.example.mall.model.bo.AdminLoginBO;
import com.example.mall.model.bo.SearchAdminsBO;
import com.example.mall.model.bo.UpdateAdminssBO;
import com.example.mall.model.vo.*;

/**
 * @Classname AdminLoginService
 * @Description admin页面的业务逻辑
 * @Date 2022-08-19 17:32
 * @Created by Yang Yi-zhou
 */
public interface AdminService {


    /**
     * 验证数据库中是否有该用户
     * @param adminLoginBO 需要验证的admin信息
     * @return 200：找到该用户 404：找不到
     */
    Integer login(AdminLoginBO adminLoginBO);

    /**
     * 根据account获取用户的name
     * @param account 账户 admin表中 account是唯一的
     * @return 用户的name
     */
    String getAdminName(String account);

    AllAdminsVO getAllAdmins();

    SearchAdminsVO searchAdmins(SearchAdminsBO searchAdminsBO);

    AddAdminssVO addAdminss(AddAdminssBO addAdminssBO);

    Boolean deleteAdmins(Integer id);

    GetAdminsInfoVO getAdminsInfo(Integer id);

    UpdateAdminssVO updateAdminss(UpdateAdminssBO updateAdminssBO);
}
