package com.example.mall.service;

import com.example.mall.mapper.GoodsMapper;
import com.example.mall.mapper.OrderMapper;
import com.example.mall.model.bo.ChangeOrderBO;
import com.example.mall.model.bo.OrdersByPageBO;
import com.example.mall.model.po.GoodsSpecPO;
import com.example.mall.model.po.OrderPO;
import com.example.mall.model.vo.ChangeOrderVO;
import com.example.mall.model.vo.DeleteOrderVO;
import com.example.mall.model.vo.OrderVO;
import com.example.mall.model.vo.OrdersByPageVO;
import com.example.mall.util.MybatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname OrderServiceImpl
 * @Description
 * @Date 2022-08-21 14:16
 * @Created by Yang Yi-zhou
 */
public class OrderServiceImpl implements OrderSerive {
    @Override
    public OrdersByPageVO ordersByPage(OrdersByPageBO ordersByPageBO) {
        //解析BO
        Integer state = ordersByPageBO.getState();
        Integer currentPage = ordersByPageBO.getCurrentPage();
        Integer pageSize = ordersByPageBO.getPagesize();
        String moneyLimitLow = ordersByPageBO.getMoneyLimit1();
        String moneyLimitHigh = ordersByPageBO.getMoneyLimit2();
        String goodsName = ordersByPageBO.getGoods();
        String address = ordersByPageBO.getAddress();
        String userName = ordersByPageBO.getName();
        String orderId = ordersByPageBO.getId();

        //mapper
        SqlSession session = MybatisUtils.openSession();
        OrderMapper mapper = session.getMapper(OrderMapper.class);
        //去查找符合要求的订单
        OrderPO orderPO = new OrderPO();
        //订单状态
        if (state == null || state != -1) {
            //-1表示查询所有订单
            orderPO.setStateId(state);
        }
        if (!StringUtils.isBlank(goodsName)) {
            orderPO.setGoodsName(goodsName);
        }
        if (!StringUtils.isBlank(address)) {
            orderPO.setAddress(address);
        }
        if (!StringUtils.isBlank(userName)) {
            orderPO.setNickname(userName);
        }
        if (!StringUtils.isBlank(orderId)) {
            orderPO.setId(Integer.parseInt(orderId));
        }
        //数据验证
        moneyLimitLow = StringUtils.isBlank(moneyLimitLow) ? null : moneyLimitLow;
        moneyLimitHigh = StringUtils.isBlank(moneyLimitHigh) ? null : moneyLimitHigh;
        //分页数据
        Integer offset = pageSize * (currentPage - 1);

        //根据PO的条件去做一个分页查询
        List<OrderPO> orderPOList = mapper.selectOrderListByOrderPOWithPage(orderPO, moneyLimitLow, moneyLimitHigh, offset, pageSize);

        //封装到VO里返回
        OrdersByPageVO ordersByPageVO = new OrdersByPageVO();
        //状态码
        ordersByPageVO.setCode(0);
        //OrdersByPageVO.DataDTO
        OrdersByPageVO.DataDTO dataDTO = new OrdersByPageVO.DataDTO();
        //总订单数
        Integer totalOrderNumber = mapper.selectOrderTotalNumberByOrderPOWithPage(orderPO, moneyLimitLow, moneyLimitHigh);
        dataDTO.setTotal(totalOrderNumber);
        //        OrdersByPageVO.DataDTO.OrdersDTO
        List<OrdersByPageVO.DataDTO.OrdersDTO> ordersDTOList = new ArrayList<>();
        //把PO封装进来
        for (OrderPO order : orderPOList) {
            OrdersByPageVO.DataDTO.OrdersDTO.UserDTO userDTO = new OrdersByPageVO.DataDTO.OrdersDTO.UserDTO(order.getNickname(), order.getReceiver(), order.getAddress(), order.getPhone());

            OrdersByPageVO.DataDTO.OrdersDTO ordersDTO = new OrdersByPageVO.DataDTO.OrdersDTO(order.getId(), order.getUserId(), order.getGoodsId(), order.getGoodsName(), order.getSpecName(), order.getNumber(), order.getAmount(), order.getStateId(), parseStateIdToState(order.getStateId()), userDTO);

            ordersDTOList.add(ordersDTO);
        }
        dataDTO.setOrders(ordersDTOList);

        ordersByPageVO.setData(dataDTO);
        //关闭资源
        session.close();

        return ordersByPageVO;
    }

    @Override
    public OrderVO order(Integer id) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        OrderMapper mapper = session.getMapper(OrderMapper.class);
        //根据id去数据库找信息
        OrderPO orderPO = mapper.selectOrderById(id);
        //封装进VO
        OrderVO orderVO = new OrderVO();
        //set code
        orderVO.setCode(0);
        //构造data
        //List<OrderVO.DataDTO.SpecDTO> 商品的所有规格的列表
        List<OrderVO.DataDTO.SpecDTO> specDTOList = new ArrayList<>();
        Integer goodsId = orderPO.getGoodsId();
        //获取goods的mapper
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        List<GoodsSpecPO> goodsSpecPOList = goodsMapper.selectGoodsSpecListByGoodsId(goodsId);
        //封装进specDTOList
        for (GoodsSpecPO specPO : goodsSpecPOList) {
            specDTOList.add(new OrderVO.DataDTO.SpecDTO(specPO.getId(), specPO.getName(), specPO.getPrice()));
        }

        //List<OrderVO.DataDTO.StatesDTO>
        List<OrderVO.DataDTO.StatesDTO> statesDTOList = new ArrayList<>();
        statesDTOList.add(new OrderVO.DataDTO.StatesDTO(0, "未付款"));
        statesDTOList.add(new OrderVO.DataDTO.StatesDTO(1, "未发货"));
        statesDTOList.add(new OrderVO.DataDTO.StatesDTO(2, "已发货"));
        statesDTOList.add(new OrderVO.DataDTO.StatesDTO(3, "已完成订单"));

        OrderVO.DataDTO dataDTO = new OrderVO.DataDTO(orderPO.getId(), orderPO.getAmount(), orderPO.getNumber(), orderPO.getGoodsId(), orderPO.getStateId(), orderPO.getGoodsName(), specDTOList, statesDTOList, new OrderVO.DataDTO.CurStateDTO(orderPO.getStateId()), new OrderVO.DataDTO.CurSpecDTO(orderPO.getSpecId()));

        //set data
        orderVO.setData(dataDTO);
        //关闭资源
        session.close();

        return orderVO;
    }

    @Override
    public ChangeOrderVO changeOrder(ChangeOrderBO changeOrderBO) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        OrderMapper mapper = session.getMapper(OrderMapper.class);
        //解析BO
        Integer orderId = Integer.parseInt(changeOrderBO.getId());
        Integer num = changeOrderBO.getNum();
        Integer specId = changeOrderBO.getSpec();
        Integer stateId = changeOrderBO.getState();

        //封装到PO里
        OrderPO orderPO = new OrderPO();
        orderPO.setId(orderId);
        orderPO.setNumber(num);
        orderPO.setSpecId(specId);
        orderPO.setStateId(stateId);
        //维护amount
        //先获取当前规格的价格
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        Double specPrice = goodsMapper.selectSpecPriceFromGoodsSpecById(specId);
        //计算当前的总金额
        Double amount = num * specPrice;
        //封装进PO里
        orderPO.setAmount(amount);

        //如果由订单状态由1 --> 0 则释放库存
        OrderPO tmpOrder = mapper.selectOrderById(orderId);
        if (tmpOrder.getStateId() == 1 && stateId == 0) {
            goodsMapper.updateGoodsSpecStockNum(tmpOrder.getSpecId(), tmpOrder.getNumber());
        }

        //去修改订单
        Integer updateAffectedRows = mapper.updateOrderByOrderPO(orderPO);
        //VO
        ChangeOrderVO changeOrderVO = new ChangeOrderVO();
        changeOrderVO.setCode(0);
        //提交
        if (updateAffectedRows != 0) {
            session.commit();
        }
        session.close();

        return changeOrderVO;
    }

    @Override
    public DeleteOrderVO deleteOrder(Integer id) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        OrderMapper mapper = session.getMapper(OrderMapper.class);
        //根据id去删除order数据
        Integer affectedRows = mapper.deleteOrderById(id);
        //VO
        DeleteOrderVO deleteOrderVO = new DeleteOrderVO();
        deleteOrderVO.setCode(0);

        if (affectedRows != 0) {
            session.commit();
        }
        session.close();

        return deleteOrderVO;
    }

    private String parseStateIdToState(Integer stateId) {
        String state = null;
        if (stateId == null) return null;
        switch (stateId) {
            case 0:
                state = "未付款";
                break;
            case 1:
                state = "未发货";
                break;
            case 2:
                state = "已发货";
                break;
            case 3:
                state = "已到货";
                break;
        }
        return state;
    }
}
