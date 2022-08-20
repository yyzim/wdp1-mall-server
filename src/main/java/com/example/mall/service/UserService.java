package com.example.mall.service;

import com.example.mall.model.vo.AllUserVO;
import com.example.mall.model.vo.DeleteUserVO;
import com.example.mall.model.vo.SearchUserVO;

/**
 * @Classname UserService
 * @Description
 * @Date 2022-08-20 9:46
 * @Created by Yang Yi-zhou
 */
public interface UserService {
    AllUserVO allUser();

    DeleteUserVO deleteUser(int id);

    SearchUserVO searchUser(String word);
}
