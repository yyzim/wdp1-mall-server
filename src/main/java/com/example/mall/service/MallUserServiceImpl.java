package com.example.mall.service;

import com.example.mall.mapper.UserMapper;
import com.example.mall.model.bo.MallLoginBO;
import com.example.mall.model.bo.MallSignupBO;
import com.example.mall.model.bo.UpdatePwdBO;
import com.example.mall.model.bo.UpdateUserDataBO;
import com.example.mall.model.po.UserPO;
import com.example.mall.model.vo.*;
import com.example.mall.util.MybatisUtils;
import com.example.mall.util.ParseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Classname MallUserServiceImpl
 * @Description
 * @Date 2022-08-21 21:25
 * @Created by Yang Yi-zhou
 */
public class MallUserServiceImpl implements MallUserService {


    @Override
    public MallLoginVO login(HttpServletRequest req, MallLoginBO mallLoginBO) {
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
            //做一个权限控制 把userPO放进session域里
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", userPO);
        }
        //关闭资源
        session.close();


        return mallLoginVO;
    }

    @Override
    public MallSignupVO signup(HttpServletRequest req, MallSignupBO mallSignupBO) {
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
            //做一个权限控制 把userPO放进session域里
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", userPO);
        }

        //关闭资源
        session.close();

        return mallSignupVO;
    }

    @Override
    public UpdateUserDataVO updateUserData(UpdateUserDataBO updateUserDataBO) {
        SqlSession session = MybatisUtils.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        Integer id = updateUserDataBO.getId();
        String nickname = updateUserDataBO.getNickname();
        String recipient = updateUserDataBO.getRecipient();
        String phone = updateUserDataBO.getPhone();
        String address = updateUserDataBO.getAddress();

        UserPO userPO = new UserPO(id, null, nickname, null, recipient, address, phone);
        Integer affectedRows = mapper.updateUserWithUserPO(userPO);

        UpdateUserDataVO updateUserDataVO = new UpdateUserDataVO();

        if (affectedRows > 0) {
            session.commit();
            updateUserDataVO.setCode(0);
        } else {
            session.rollback();
            updateUserDataVO.setCode(10000);
            updateUserDataVO.setMessage("修改失败");
        }

        session.close();

        return updateUserDataVO;
    }

    @Override
    public UpdatePwdVO updatePwd(UpdatePwdBO updatePwdBO) {
        SqlSession session = MybatisUtils.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        Integer id = updatePwdBO.getId();
        String oldPwd = updatePwdBO.getOldPwd();
        String newPwd = updatePwdBO.getNewPwd();
        String confirmPwd = updatePwdBO.getConfirmPwd();

        UpdatePwdVO updatePwdVO = new UpdatePwdVO();

        if (StringUtils.isBlank(newPwd) || !StringUtils.equals(newPwd, confirmPwd)) {
            updatePwdVO.setCode(10000);
            updatePwdVO.setMessage("两次密码不一致");
            return updatePwdVO;
        }

        UserPO userPO = new UserPO();
        userPO.setId(id);
        userPO.setPwd(newPwd);
        Integer affectedRows = mapper.updateUserWithUserPO(userPO);

        if (affectedRows > 0) {
            session.commit();
            updatePwdVO.setCode(0);
        } else {
            session.rollback();
            updatePwdVO.setCode(10000);
            updatePwdVO.setMessage("修改失败");
        }

        session.close();

        return updatePwdVO;
    }
}
