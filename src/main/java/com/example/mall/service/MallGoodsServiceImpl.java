package com.example.mall.service;

import com.example.mall.mapper.GoodsMapper;
import com.example.mall.mapper.OrderMapper;
import com.example.mall.mapper.UserMapper;
import com.example.mall.model.bo.AskGoodsMsgBO;
import com.example.mall.model.bo.GetGoodsByTypeBO;
import com.example.mall.model.bo.SearchGoodsBO;
import com.example.mall.model.po.*;
import com.example.mall.model.vo.*;
import com.example.mall.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MallGoodsServiceImpl
 * @Description
 * @Date 2022-08-22 9:52
 * @Created by Yang Yi-zhou
 */
public class MallGoodsServiceImpl implements MallGoodsService {
    GoodsService goodsService = new GoodsServiceImpl();

    @Override
    public GetGoodsByTypeVO getGoodsByType(Integer typeId) {
        GetGoodsByTypeVO goodsByTypeVO = goodsService.getGoodsByType(typeId);

        return goodsByTypeVO;
    }

    @Override
    public MallSearchGoodsVO searchGoods(String keyword) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //根据关键词去搜索
        List<GoodsPO> goodsPOList = mapper.selectGoodsListByKeyword(keyword);
        //VO
        MallSearchGoodsVO mallSearchGoodsVO = new MallSearchGoodsVO();
        //set code
        mallSearchGoodsVO.setCode(0);
        //set data
        List<MallSearchGoodsVO.DataDTO> dataDTOList = new ArrayList<>();
        for (GoodsPO goodsPO : goodsPOList) {
            dataDTOList.add(new MallSearchGoodsVO.DataDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), goodsPO.getPrice(), goodsPO.getTypeId()));
        }
        mallSearchGoodsVO.setData(dataDTOList);

        session.close();

        return mallSearchGoodsVO;
    }

    //todo
    @Override
    public GetGoodsMsgVO getGoodsMsg(Integer id) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        //获取该商品的Msg
        List<MsgPO> msgPOList = goodsMapper.selectMsgListByGoodsId(id);
        //VO
        GetGoodsMsgVO getGoodsMsgVO = new GetGoodsMsgVO();
        //set code
        getGoodsMsgVO.setCode(0);
        //构造data
        List<GetGoodsMsgVO.DataDTO> dataDTOList = new ArrayList<>();
        for (MsgPO msgPO : msgPOList) {
            //根据userid拿到用户名
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserPO userPO = mapper.selectUserById(msgPO.getUserId());

            dataDTOList.add(new GetGoodsMsgVO.DataDTO(msgPO.getId(), msgPO.getContent(), userPO.getNickname(), msgPO.getCreateTime().toString(), new GetGoodsMsgVO.DataDTO.ReplyDTO(msgPO.getReplyContent(), msgPO.getReplyTime() == null ? null : msgPO.getReplyTime().toString())));
        }
        //set data
        getGoodsMsgVO.setData(dataDTOList);

        session.close();


        return getGoodsMsgVO;
    }

    @Override
    public MallGetGoodsInfoVO getGoodsInfo(int id) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        //VO
        MallGetGoodsInfoVO mallGetGoodsInfoVO = new MallGetGoodsInfoVO();
        //先读商品信息
        GoodsPO goodsPO = goodsMapper.selectGoodsById(id);

        //set code
        mallGetGoodsInfoVO.setCode(0);

        //构造spec
        List<MallGetGoodsInfoVO.DataDTO.SpecsDTO> specsDTOList = new ArrayList<>();
        //根据goodsId去获取规格信息
        List<GoodsSpecPO> goodsSpecPOList = goodsMapper.selectGoodsSpecListByGoodsId(id);
        for (GoodsSpecPO specPO : goodsSpecPOList) {
            specsDTOList.add(new MallGetGoodsInfoVO.DataDTO.SpecsDTO(specPO.getId(), specPO.getName(), specPO.getStockNum(), specPO.getPrice()));
        }

        //构造data
        MallGetGoodsInfoVO.DataDTO dataDTO = new MallGetGoodsInfoVO.DataDTO(goodsPO.getImg(), goodsPO.getName(), goodsPO.getDescription(), goodsPO.getTypeId(), specsDTOList, goodsPO.getPrice());

        //set data
        mallGetGoodsInfoVO.setData(dataDTO);

        session.close();


        return mallGetGoodsInfoVO;
    }

    @Override
    public GetGoodsCommentVO getGoodsComment(Integer goodsId) {
        //先根据goodsId拿到CommentPOList
        //mapper
        SqlSession session = MybatisUtils.openSession();
        OrderMapper mapper = session.getMapper(OrderMapper.class);
        //goodsId拿到CommentPOList
        List<GoodsCommentPO> goodsCommentPOList = mapper.selectGoodsCommentPOListByGoodsId(goodsId);
        //VO
        GetGoodsCommentVO getGoodsCommentVO = new GetGoodsCommentVO();
        //set code
        getGoodsCommentVO.setCode(0);
        //commentListDTO
        List<GetGoodsCommentVO.DataDTO.CommentListDTO> commentListDTOList = new ArrayList<>();

        //rate
        double rate = 0;

        //PO ->>>
        for (GoodsCommentPO goodsCommentPO : goodsCommentPOList) {
            //根据userId 获取 nickname
            UserMapper userMapper = session.getMapper(UserMapper.class);
            UserPO userPO = userMapper.selectUserById(goodsCommentPO.getUserId());

            //new UserDTO
            GetGoodsCommentVO.DataDTO.CommentListDTO.UserDTO userDTO = new GetGoodsCommentVO.DataDTO.CommentListDTO.UserDTO();
            userDTO.setNickname(userPO.getNickname());

            //根据specId获取specName
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            GoodsSpecPO goodsSpecPO = goodsMapper.selectGoodsSpecPOByGoodsSpecId(goodsCommentPO.getSpecId());

            if (goodsSpecPO == null) {
                //就去specDe里找
                goodsSpecPO = goodsMapper.selectGoodsSpecPOFromDeByGoodsSpecId(goodsCommentPO.getSpecId());
            }


            commentListDTOList.add(new GetGoodsCommentVO.DataDTO.CommentListDTO(userDTO, goodsCommentPO.getScore(), goodsCommentPO.getId(), goodsSpecPO == null ? null : goodsSpecPO.getName(), goodsCommentPO.getContent(), goodsCommentPO.getCreateTime().toString(), goodsCommentPO.getUserId()));

            //rate
            rate += goodsCommentPO.getScore();
        }

        //计算下rate
        rate /= goodsCommentPOList.size() == 0 ? 1 : goodsCommentPOList.size();

        //set data
        GetGoodsCommentVO.DataDTO dataDTO = new GetGoodsCommentVO.DataDTO(commentListDTOList, rate);
        getGoodsCommentVO.setData(dataDTO);

        session.close();

        return getGoodsCommentVO;
    }

    @Override
    public AskGoodsMsgVO askGoodsMsg(AskGoodsMsgBO askGoodsMsgBO) {
        //解析BO
        String nickname = askGoodsMsgBO.getToken();
        String msg = askGoodsMsgBO.getMsg();
        Integer goodsId = Integer.parseInt(askGoodsMsgBO.getGoodsId());
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        //msgPO
        MsgPO msgPO = new MsgPO();
        msgPO.setGoodsId(goodsId);
        msgPO.setContent(msg);
        msgPO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //根据nickname去获取userId
        UserPO userPO = session.getMapper(UserMapper.class).selectUserByNickname(nickname);

        msgPO.setUserId(userPO.getId());
        //msg状态为未回复
        msgPO.setState(1);
        //插入msg表中
        Integer affectedRows = goodsMapper.insertMsgUseMsgPO(msgPO);

        //VO
        AskGoodsMsgVO askGoodsMsgVO = new AskGoodsMsgVO();
        if (affectedRows == 0) {
            askGoodsMsgVO.setCode(10000);
            session.rollback();
        } else {
            askGoodsMsgVO.setCode(0);
            session.commit();
        }
        //关闭资源
        session.close();

        return askGoodsMsgVO;
    }

    @Override
    public GetGoodsByTypeVO getIndexGoodsByType(Integer typeId) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);

        //封装进VO
        GetGoodsByTypeVO getGoodsByTypeVO = new GetGoodsByTypeVO();
        getGoodsByTypeVO.setCode(0);

        List<GetGoodsByTypeVO.DataDTO> data = new ArrayList<>();
        //根据typeID获取商品
        if (typeId == -1) {
            List<Integer> typeIdList = mapper.selectGoodsTypeId();

            for (Integer id : typeIdList) {
                List<GoodsPO> goodsPOList = mapper.selectGoodsFromTableGoodsByTypeIdWithPagesize(id, 0, 5);

                for (GoodsPO goodsPO : goodsPOList) {
                    //根据goodsId获取库存
                    Integer stockNum = null;
                    stockNum = mapper.selectStockNumFromTableGoodsSpecByGoodsId(goodsPO.getId());
                    data.add(new GetGoodsByTypeVO.DataDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), goodsPO.getPrice(), goodsPO.getTypeId(), stockNum));
                }
            }
            getGoodsByTypeVO.setData(data);
            session.close();
            return getGoodsByTypeVO;
        }
        List<GoodsPO> goodsPOList = mapper.selectGoodsFromTableGoodsByTypeIdWithPagesize(typeId, 0, 50);
        for (GoodsPO goodsPO : goodsPOList) {
            //根据goodsId获取库存
            Integer stockNum = null;
            stockNum = mapper.selectStockNumFromTableGoodsSpecByGoodsId(goodsPO.getId());
            data.add(new GetGoodsByTypeVO.DataDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), goodsPO.getPrice(), goodsPO.getTypeId(), stockNum));
        }
        getGoodsByTypeVO.setData(data);
        session.close();

        return getGoodsByTypeVO;

    }

    @Override
    public GetGoodsByTypeWithPageVO getGoodsByTypeWithPage(GetGoodsByTypeBO getGoodsByTypeBO) {
        Integer typeId = Integer.parseInt(getGoodsByTypeBO.getTypeId());
        Integer pageSize = getGoodsByTypeBO.getPageSize();
        Integer currentPage = getGoodsByTypeBO.getCurrentPage();

        SqlSession session = MybatisUtils.openSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<GoodsPO> goodsPOList = goodsMapper.selectGoodsFromTableGoodsByTypeIdWithPagesize(typeId, (currentPage - 1) * pageSize, pageSize);

        List<GetGoodsByTypeWithPageVO.DataDTO.GoodsDTO> goodsDTOList = new ArrayList<>();
        for (GoodsPO goodsPO : goodsPOList) {
            //根据goodsId获取库存
            Integer stockNum = null;
            stockNum = goodsMapper.selectStockNumFromTableGoodsSpecByGoodsId(goodsPO.getId());
            goodsDTOList.add(new GetGoodsByTypeWithPageVO.DataDTO.GoodsDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), goodsPO.getPrice(), goodsPO.getTypeId(), stockNum));
        }

        GetGoodsByTypeWithPageVO getGoodsByTypeWithPageVO = new GetGoodsByTypeWithPageVO();
        getGoodsByTypeWithPageVO.setCode(0);

        Integer total = goodsMapper.selectGoodsNumByTypeId(typeId);

        GetGoodsByTypeWithPageVO.DataDTO dataDTO = new GetGoodsByTypeWithPageVO.DataDTO(total, goodsDTOList);

        getGoodsByTypeWithPageVO.setData(dataDTO);


        return getGoodsByTypeWithPageVO;
    }

    @Override
    public GetGoodsVO searchGoodsWithPage(SearchGoodsBO searchGoodsBO) {
        String keyword = searchGoodsBO.getKeyword();
        Integer currentPage = searchGoodsBO.getCurrentPage();
        Integer pagesize = searchGoodsBO.getPageSize();

        SqlSession session = MybatisUtils.openSession();
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);

        List<GoodsPO> goodsPOList = goodsMapper.selectGoodsListByKeywordWithPage(keyword, pagesize, (currentPage - 1) * pagesize);


        List<GetGoodsVO.DataDTO.GoodsDTO> goodsDTOList = new ArrayList<>();
        for (GoodsPO goodsPO : goodsPOList) {
            //根据goodsId获取库存
            Integer stockNum = null;
            stockNum = goodsMapper.selectStockNumFromTableGoodsSpecByGoodsId(goodsPO.getId());
            goodsDTOList.add(new GetGoodsVO.DataDTO.GoodsDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), goodsPO.getPrice(), goodsPO.getTypeId(), stockNum));
        }

        GetGoodsVO getGoodsVO = new GetGoodsVO();
        getGoodsVO.setCode(0);

        Integer total = goodsMapper.selectGoodsNumByKeyword(keyword);

        GetGoodsVO.DataDTO dataDTO = new GetGoodsVO.DataDTO(total, goodsDTOList);

        getGoodsVO.setData(dataDTO);


        return getGoodsVO;
    }
}
