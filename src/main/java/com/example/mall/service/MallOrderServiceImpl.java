package com.example.mall.service;

import com.example.mall.mapper.GoodsMapper;
import com.example.mall.mapper.OrderMapper;
import com.example.mall.model.po.GoodsPO;
import com.example.mall.model.po.OrderPO;
import com.example.mall.model.vo.GetOrderByStateVO;
import com.example.mall.util.MybatisUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MallOrderServiceImpl
 * @Description
 * @Date 2022-08-22 10:44
 * @Created by Yang Yi-zhou
 */
public class MallOrderServiceImpl implements MallOrderService {
    @Override
    public GetOrderByStateVO getOrderByState(int state, String token) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);

        //查询token去查order
        List<OrderPO> orderPOList = orderMapper.selectOrderByStateAndNickname(state, token);
        //VO
        GetOrderByStateVO getOrderByStateVO = new GetOrderByStateVO();
        //set code
        getOrderByStateVO.setCode(0);
        //构造data
        List<GetOrderByStateVO.DataDTO> dataDTOList = new ArrayList<>();
        for (OrderPO orderPO : orderPOList) {
            //拿到详细商品信息

            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            //根据id去查询商品的详细信息
            GoodsPO goodsPO = goodsMapper.selectGoodsById(orderPO.getGoodsId());
            //根据规格id去查规格单价
            Double unitPrice = goodsMapper.selectSpecPriceFromGoodsSpecById(orderPO.getSpecId());


            //todo goodsDetailId
            GetOrderByStateVO.DataDTO.GoodsDTO goodsDTO = new GetOrderByStateVO.DataDTO.GoodsDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), null, orderPO.getSpecName(), unitPrice);

            dataDTOList.add(new GetOrderByStateVO.DataDTO(orderPO.getId(), orderPO.getStateId(), orderPO.getNumber(), orderPO.getAmount(), orderPO.getGoodsId(), orderPO.getCreateTime().toString(), orderPO.getHasComment(), goodsDTO));

        }
        //set data
        getOrderByStateVO.setData(dataDTOList);


        return getOrderByStateVO;
    }
}
