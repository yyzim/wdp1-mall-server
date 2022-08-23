package com.example.mall.service;

import com.example.mall.mapper.AdminMapper;
import com.example.mall.model.bo.*;
import com.example.mall.model.po.AdminPO;
import com.example.mall.model.vo.*;
import com.example.mall.util.MybatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname AdminServiceImpl
 * @Description
 * @Date 2022-08-19 17:38
 * @Created by Yang Yi-zhou
 */
public class AdminServiceImpl implements AdminService {

    @Override
    public AllAdminsVO getAllAdmins() {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        //封装
        List<AdminPO> adminPOList = mapper.selectAllAdminsFromAdmin();
        AllAdminsVO allAdminsVO = new AllAdminsVO();
        allAdminsVO.setCode(0);
        List<AllAdminsVO.DataDTO> data = new ArrayList<>();
        for (AdminPO admin : adminPOList) {
            data.add(new AllAdminsVO.DataDTO(admin.getId(), admin.getAccount(), admin.getName(), admin.getPassword()));
        }
        allAdminsVO.setData(data);

        session.close();
        return allAdminsVO;
    }

    @Override
    public SearchAdminsVO searchAdmins(SearchAdminsBO searchAdminsBO) {
        //拿到mapper
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        //获取PO
        List<AdminPO> adminPOList = mapper.selectAdminsFromAdminByAccountAndName(searchAdminsBO.getEmail(), searchAdminsBO.getNickname());
        //封装到VO中
        SearchAdminsVO searchAdminsVO = new SearchAdminsVO();
        searchAdminsVO.setCode(0);
        List<SearchAdminsVO.DataVO> data = new ArrayList<>();
        for (AdminPO admin : adminPOList) {
            data.add(new SearchAdminsVO.DataVO(admin.getId(), admin.getAccount(), admin.getName(), admin.getPassword()));
        }
        searchAdminsVO.setData(data);
        session.close();
        return searchAdminsVO;
    }

    @Override
    public AddAdminssVO addAdminss(AddAdminssBO addAdminssBO) {
        //拿到mapper
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        //把BO封装到PO
        AdminPO adminPO = new AdminPO();
        adminPO.setAccount(addAdminssBO.getEmail());
        adminPO.setName(addAdminssBO.getNickname());
        adminPO.setPassword(addAdminssBO.getPwd());
        //声明一个VO
        AddAdminssVO addAdminssVO = new AddAdminssVO();
        //把PO添加到数据库中
        Integer affectedRows = mapper.insertIntoAdmin(adminPO);
        if (affectedRows == 1) {
            //添加成功,返回状态码0
            addAdminssVO.setCode(0);
            //提交事务
            session.commit();
        } else {
            //添加失败，返回状态码10000
            addAdminssVO.setCode(10000);
            //回滚事务
            session.rollback();
        }
        //关闭资源 返回VO
        session.close();
        return addAdminssVO;
    }

    @Override
    public Boolean deleteAdmins(Integer id) {
        //操作数据库
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        Integer affectedRows = mapper.deleteAdminsById(id);
        if (affectedRows == 1) {
            //删除成功
            session.commit();
            session.close();
            return true;
        }
        session.rollback();
        session.close();
        return false;
    }

    @Override
    public GetAdminsInfoVO getAdminsInfo(Integer id) {
        //操作数据库
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        //获取到id对应的PO
        AdminPO adminPO = mapper.selectAdminFromAdminById(id);
        //VO
        GetAdminsInfoVO getAdminsInfoVO = new GetAdminsInfoVO();
        if (adminPO == null) {
            //查询失败
            getAdminsInfoVO.setCode(10000);
            session.rollback();
        } else {
            //查询成功 把PO封装进VO
            getAdminsInfoVO.setCode(0);
            GetAdminsInfoVO.DataVO data = new GetAdminsInfoVO.DataVO(adminPO.getId(), adminPO.getAccount(), adminPO.getName(), adminPO.getPassword());
            getAdminsInfoVO.setData(data);
            session.commit();
        }
        session.close();
        return getAdminsInfoVO;
    }

    @Override
    public UpdateAdminssVO updateAdminss(UpdateAdminssBO updateAdminssBO) {
        //操作数据库
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        //把BO封装进PO
        AdminPO adminPO = new AdminPO(updateAdminssBO.getId(), updateAdminssBO.getEmail(), updateAdminssBO.getNickname(), updateAdminssBO.getPwd());
        //更新数据库
        Integer affectedRows = mapper.updateAdminById(adminPO);
        //声明VO
        UpdateAdminssVO updateAdminssVO = new UpdateAdminssVO();
        if (affectedRows == 1) {
            //修改成功
            updateAdminssVO.setCode(0);
            session.commit();
        } else {
            //修改失败
            updateAdminssVO.setCode(10000);
            session.rollback();
        }
        session.close();
        return updateAdminssVO;
    }

    @Override
    public ChangePwdVO changePwdBO(ChangePwdBO changePwdBO) {
        //操作数据库
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        //解析BO
        String oldPwd = changePwdBO.getOldPwd();
        String confirmPwd = changePwdBO.getConfirmPwd();
        String adminToken = changePwdBO.getAdminToken();
        String newPwd = changePwdBO.getNewPwd();
        //VO声明
        ChangePwdVO changePwdVO = new ChangePwdVO();
        //验证是否为空
        if (StringUtils.isBlank(newPwd) || StringUtils.isBlank(oldPwd) || StringUtils.isBlank(confirmPwd)) {
            changePwdVO.setCode(10000);
            changePwdVO.setMessage("密码不能为空");
            return changePwdVO;
        }
        //验证confirm是否正确
        if (!StringUtils.equals(newPwd, confirmPwd)) {
            changePwdVO.setCode(10000);
            changePwdVO.setMessage("两次密码不一致");
            return changePwdVO;
        }
        //写入数据库
        Integer affectedRows = mapper.updateAdminPasswordByNameAndPassword(adminToken, oldPwd, newPwd);
        if (affectedRows == 0) {
            changePwdVO.setCode(10000);
            changePwdVO.setMessage("更改失败");
            session.rollback();
        } else {
            changePwdVO.setCode(0);
            session.commit();
        }

        return changePwdVO;
    }

    @Override
    public LogoutAdminVO logoutAdmin(LogoutAdminBO logoutAdminBO) {

        LogoutAdminVO logoutAdminVO = new LogoutAdminVO();
        logoutAdminVO.setCode(0);
        return logoutAdminVO;
    }

    @Override
    public Integer login(AdminLoginBO adminLoginBO) {
        //解析BO
        String account = adminLoginBO.getEmail();
        String password = adminLoginBO.getPwd();
        //根据信息查询数据库
        SqlSession session = MybatisUtils.openSession();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        AdminPO adminPO = mapper.selectAdminPOByAccountAndPassword(account, password);
        session.close();
        //比对信息
        if (adminPO != null && StringUtils.equals(adminPO.getAccount(), account) && StringUtils.equals(adminPO.getPassword(), password)) {
            return 200;
        }
        return 404;
    }

    @Override
    public String getAdminName(String account) {
        //根据信息查询数据库
        return MybatisUtils.openSession().getMapper(AdminMapper.class).selectNameByAccount(account);
    }
}
