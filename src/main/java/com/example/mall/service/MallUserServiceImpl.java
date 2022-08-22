package com.example.mall.service;

import com.example.mall.mapper.UserMapper;
import com.example.mall.model.bo.MallLoginBO;
import com.example.mall.model.bo.MallSignupBO;
import com.example.mall.model.po.UserPO;
import com.example.mall.model.vo.MallLoginVO;
import com.example.mall.model.vo.MallSignupVO;
import com.example.mall.util.MybatisUtils;
import com.example.mall.util.ParseUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @Classname MallUserServiceImpl
 * @Description
 * @Date 2022-08-21 21:25
 * @Created by Yang Yi-zhou
 */
public class MallUserServiceImpl implements MallUserService {


    @Override
    public MallLoginVO login(MallLoginBO mallLoginBO) {
        //解析BO
        String email = mallLoginBO.getEmail();
        String pwd = mallLoginBO.getPwd();
        //去数据库做验证
        //mapper
        SqlSession session = MybatisUtils.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        //VO
        MallLoginVO mallLoginVO = new MallLoginVO();
        //查询
        UserPO userPO = mapper.selectUserPOByEmailAndPwd(email, pwd);
        //判断
        if (userPO == null) {
            //数据库中没有该用户
            mallLoginVO.setCode(10000);
            mallLoginVO.setMessage("账户或密码错误");

        } else {
            //登陆成功
            mallLoginVO.setCode(0);
            mallLoginVO.setData(new MallLoginVO.DataDTO(0, userPO.getNickname(), userPO.getNickname()));
        }
        //关闭资源
        session.close();


        return mallLoginVO;
    }

    @Override
    public MallSignupVO signup(MallSignupBO mallSignupBO) {
        //PO
        UserPO userPO = new UserPO(null, mallSignupBO.getEmail(), mallSignupBO.getNickname(), mallSignupBO.getPwd(), mallSignupBO.getRecipient(), mallSignupBO.getAddress(), mallSignupBO.getPhone());
        //mapper
        SqlSession session = MybatisUtils.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        //插入数据库
        Integer affectedRows = mapper.insertUserByUserPO(userPO);
        //VO
        MallSignupVO mallSignupVO = new MallSignupVO();
        //判断一下
        if (affectedRows == 0) {
            //失败
            mallSignupVO.setCode(10000);
            mallSignupVO.setMessage("注册失败");
            session.rollback();
        } else {
            //成功
            mallSignupVO.setCode(0);
            mallSignupVO.setData(new MallSignupVO.DataDTO(0, userPO.getNickname(), userPO.getNickname()));
            session.commit();
        }

        //关闭资源
        session.close();

        return mallSignupVO;
    }
}
