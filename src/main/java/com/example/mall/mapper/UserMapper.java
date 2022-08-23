package com.example.mall.mapper;

import com.example.mall.model.po.UserPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname AdminMapper
 * @Description
 * @Date 2022-08-19 17:52
 * @Created by Yang Yi-zhou
 */
public interface UserMapper {

    List<UserPO> selectUserListFromUser();

    Integer deleteUserById(@Param("id") Integer id);

    List<UserPO> selectUserFromUserByWord(@Param("word") String word);

    UserPO selectUserById(@Param("userId") Integer userId);

    UserPO selectUserPOByEmailAndPwd(@Param("email") String email, @Param("pwd") String pwd);

    String selectNameByEmail(@Param("email") String email);

    Integer insertUserByUserPO(@Param("userPO") UserPO userPO);

    UserPO selectUserByNickname(@Param("nickname") String nickname);

    Integer updateUserWithUserPO(@Param("userPO") UserPO userPO);
}
