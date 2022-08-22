package com.example.mall.service;

import com.example.mall.model.bo.AdminLoginBO;
import com.example.mall.model.bo.MallLoginBO;
import com.example.mall.model.bo.MallSignupBO;
import com.example.mall.model.vo.MallLoginVO;
import com.example.mall.model.vo.MallSignupVO;

/**
 * @Classname MallUserService
 * @Description
 * @Date 2022-08-21 21:25
 * @Created by Yang Yi-zhou
 */
public interface MallUserService {


    MallLoginVO login(MallLoginBO mallLoginBO);

    MallSignupVO signup(MallSignupBO mallSignupBO);
}
