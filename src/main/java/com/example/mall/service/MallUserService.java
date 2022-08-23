package com.example.mall.service;

import com.example.mall.model.bo.*;
import com.example.mall.model.vo.MallLoginVO;
import com.example.mall.model.vo.MallSignupVO;
import com.example.mall.model.vo.UpdatePwdVO;
import com.example.mall.model.vo.UpdateUserDataVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname MallUserService
 * @Description
 * @Date 2022-08-21 21:25
 * @Created by Yang Yi-zhou
 */
public interface MallUserService {


    MallLoginVO login(HttpServletRequest req,MallLoginBO mallLoginBO);

    MallSignupVO signup(HttpServletRequest req, MallSignupBO mallSignupBO);

    UpdateUserDataVO updateUserData(UpdateUserDataBO updateUserDataBO);

    UpdatePwdVO updatePwd(UpdatePwdBO updatePwdBO);
}
