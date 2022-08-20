package com.example.mall.service;

import com.example.mall.mapper.UserMapper;
import com.example.mall.model.po.UserPO;
import com.example.mall.model.vo.AllUserVO;
import com.example.mall.model.vo.DeleteUserVO;
import com.example.mall.model.vo.SearchUserVO;
import com.example.mall.service.UserService;
import com.example.mall.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UserServiceImpl
 * @Description
 * @Date 2022-08-20 9:46
 * @Created by Yang Yi-zhou
 */
public class UserServiceImpl implements UserService {
    @Override
    public AllUserVO allUser() {
        //拿到mapper
        SqlSession session = MybatisUtils.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        //去数据库取出数据
        List<UserPO> userPOList = mapper.selectUserListFromUser();
        //把PO封装进VO
        AllUserVO allUserVO = new AllUserVO();
        allUserVO.setCode(0);
        List<AllUserVO.DataVO> data = new ArrayList<>();
        for (UserPO userPO : userPOList) {
            data.add(new AllUserVO.DataVO(userPO.getId(), userPO.getEmail(), userPO.getNickname(), userPO.getPwd(), userPO.getRecipient(), userPO.getAddress(), userPO.getPhone()));
        }
        allUserVO.setData(data);
        //关闭资源
        session.close();

        return allUserVO;
    }

    @Override
    public DeleteUserVO deleteUser(int id) {
        //拿到mapper
        SqlSession session = MybatisUtils.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        //根据id去数据库删除数据
        Integer affectedRows = mapper.deleteUserById(id);
        //VO
        DeleteUserVO deleteUserVO = new DeleteUserVO();
        if (affectedRows == 0) {
            //删除失败
            deleteUserVO.setCode(10000);
            session.rollback();
        } else {
            deleteUserVO.setCode(0);
            session.commit();
        }
        //关闭资源
        session.close();
        return deleteUserVO;
    }

    @Override
    public SearchUserVO searchUser(String word) {
        //拿到mapper
        SqlSession session = MybatisUtils.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        //根据关键词word去数据库查询
        List<UserPO> userPOList = mapper.selectUserFromUserByWord(word);
        //把PO封装进VO
        SearchUserVO searchUserVO = new SearchUserVO();
        searchUserVO.setCode(0);
        List<SearchUserVO.DataVO> data = new ArrayList<>();
        for (UserPO userPO : userPOList) {
            data.add(new SearchUserVO.DataVO(userPO.getId(), userPO.getEmail(), userPO.getNickname(), userPO.getPwd(), userPO.getRecipient(), userPO.getAddress(), userPO.getPhone()));
        }
        searchUserVO.setData(data);
        //关闭资源
        session.close();

        return searchUserVO;
    }
}
