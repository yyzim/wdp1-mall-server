package com.example.mall.service;

import com.example.mall.mapper.GoodsMapper;
import com.example.mall.mapper.OrderMapper;
import com.example.mall.mapper.UserMapper;
import com.example.mall.model.bo.AddOrderBO;
import com.example.mall.model.bo.SendCommentBO;
import com.example.mall.model.bo.SettleAccountsBO;
import com.example.mall.model.po.*;
import com.example.mall.model.vo.*;
import com.example.mall.util.MybatisUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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

            GetOrderByStateVO.DataDTO.GoodsDTO goodsDTO = new GetOrderByStateVO.DataDTO.GoodsDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), orderPO.getSpecId(), orderPO.getSpecName(), unitPrice);

            dataDTOList.add(new GetOrderByStateVO.DataDTO(orderPO.getId(), orderPO.getStateId(), orderPO.getNumber(), orderPO.getAmount(), orderPO.getGoodsId(), orderPO.getCreateTime().toString(), orderPO.getHasComment(), goodsDTO));

        }
        //set data
        getOrderByStateVO.setData(dataDTOList);


        return getOrderByStateVO;
    }

    @Override
    public SettleAccountsVO settleAccounts(HttpServletRequest req, SettleAccountsBO settleAccountsBO) {
        //再session域中取出user
        UserPO user = (UserPO) req.getSession().getAttribute("user");
        //解析BO
        List<SettleAccountsBO.CartListDTO> cartList = settleAccountsBO.getCartList();
        for (SettleAccountsBO.CartListDTO cartDTO : cartList) {
            SqlSession session = MybatisUtils.openSession();
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            //获取参数
            Integer orderId = cartDTO.getId();
            Integer goodsNum = cartDTO.getGoodsNum();
            Double amount = cartDTO.getAmount();
            //构造一个OrderPO
            OrderPO orderPO = new OrderPO();
            orderPO.setId(orderId);
            orderPO.setNumber(goodsNum);
            orderPO.setAmount(amount);
            //设置订单状态为1 //0 未付款 1 未发货 2 已发货 3 已到货
            orderPO.setStateId(1);

            //维护库存容量
            maintainStockNum(session, orderId, -1 * goodsNum);
            //修改订单状态
            Integer affectedRows = mapper.updateOrderByOrderPO(orderPO);
            if (affectedRows != 0) {
                session.commit();
            }
            session.close();
        }
        //VO
        SettleAccountsVO settleAccountsVO = new SettleAccountsVO(0);

        return settleAccountsVO;
    }

    /**
     * @param orderId  订单号
     * @param goodsNum 变化的订单数 -为减少 +为增加
     */
    private static void maintainStockNum(SqlSession session, Integer orderId, Integer goodsNum) {
        //根据orderId获取specId
        OrderPO tmpOrder = session.getMapper(OrderMapper.class).selectOrderById(orderId);
        //减少/增加对应规格的数量
        session.getMapper(GoodsMapper.class).updateGoodsSpecStockNum(tmpOrder.getSpecId(), goodsNum);
    }

    @Override
    public AddOrderVO addOrder(AddOrderBO addOrderBO) {
        //解析BO
        String nickname = addOrderBO.getToken();
        Integer goodsDetailId = addOrderBO.getGoodsDetailId();
        Integer num = addOrderBO.getNum();
        Double amount = addOrderBO.getAmount();
        Integer state = addOrderBO.getState();

        //根据nickname去拿user信息
        SqlSession session = MybatisUtils.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        UserPO userPO = userMapper.selectUserByNickname(nickname);

        //根据goodsDetailId去拿到商品信息
        GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
        GoodsPO goodsPO = goodsMapper.selectGoodsPOByGoodsSpecId(goodsDetailId);
        GoodsSpecPO goodsSpecPO = goodsMapper.selectGoodsSpecPOByGoodsSpecId(goodsDetailId);
        //构造一个orderPO
        OrderPO orderPO = new OrderPO(null, userPO.getId(), userPO.getNickname(), userPO.getRecipient(), userPO.getAddress(), userPO.getPhone(), goodsPO.getName(), goodsPO.getId(), goodsSpecPO.getName(), goodsSpecPO.getId(), num, amount, state, new Timestamp(System.currentTimeMillis()), null, null);
        //插入数据库
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);
        Integer affectedRows = orderMapper.insertOrderPO(orderPO);

        if (state == 1) {
            //减少对应规格的对应数量
            maintainStockNum(session, orderPO.getId(), -1 * num);
        }


        //VO
        AddOrderVO addOrderVO = new AddOrderVO();

        if (affectedRows == 0) {
            addOrderVO.setCode(10000);
            addOrderVO.setMessage("添加失败");
            session.rollback();
        } else {
            addOrderVO.setCode(0);
            session.commit();
        }

        session.close();


        return addOrderVO;
    }

    @Override
    public PayVO pay(Integer orderId) {
        changeOrderState(orderId, 1);
        //VO
        PayVO payVO = new PayVO();
        payVO.setCode(0);

        return payVO;
    }

    private static void changeOrderState(Integer orderId, Integer stateId) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        OrderMapper mapper = session.getMapper(OrderMapper.class);
        //更新订单状态
        OrderPO orderPO = new OrderPO();
        orderPO.setId(orderId);
        //维护一下库存，如果订单状态0-> 1，就减少该规格的库存 //0 未付款 1 未发货 2 已发货 3 已到货
        //先拿到PO
        OrderPO selectOrder = mapper.selectOrderById(orderId);
        if (selectOrder.getStateId() == 0 && stateId == 1) {
            //减少订单对应的数量
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            //减少对应规格的对应数量
            goodsMapper.updateGoodsSpecStockNum(selectOrder.getSpecId(), -1 * selectOrder.getNumber());
        }
//        if (selectOrder.getStateId() == 0 && stateId == 4) {
//            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
//            //释放对应订单的数量
//            goodsMapper.updateGoodsSpecStockNum(selectOrder.getSpecId(), selectOrder.getNumber());
//        }

        //更改订单状态
        orderPO.setStateId(stateId);
        mapper.updateOrderByOrderPO(orderPO);
        session.commit();
        session.close();
    }

    @Override
    public ConfirmReceiveVO confirmReceive(Integer orderId) {
        changeOrderState(orderId, 3);

        return new ConfirmReceiveVO(0);
    }

    @Override
    public SendCommentVO sendComment(SendCommentBO sendCommentBO) {
        //解析BO
        String nickname = sendCommentBO.getToken();
        Integer orderId = sendCommentBO.getOrderId();
        Integer goodsId = sendCommentBO.getGoodsId();
        Integer specId = sendCommentBO.getGoodsDetailId();
        String content = sendCommentBO.getContent();
        Integer score = sendCommentBO.getScore();
        //拿到id
        SqlSession session = MybatisUtils.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        UserPO userPO = userMapper.selectUserByNickname(nickname);
        //封装PO
        GoodsCommentPO goodsCommentPO = new GoodsCommentPO(null, userPO.getId(), orderId, goodsId, specId, content, 1.0 * score, new Timestamp(System.currentTimeMillis()));
        //插入数据库
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);
        Integer affectedRows = orderMapper.insertGoodsCommentPO(goodsCommentPO);
        //VO
        SendCommentVO sendCommentVO = new SendCommentVO();
        //session
        if (affectedRows == 0) {
            sendCommentVO.setCode(10000);
            sendCommentVO.setMessage("评论失败");
        } else {
            sendCommentVO.setCode(0);
            session.commit();
        }
        //关闭资源
        session.close();


        return sendCommentVO;
    }

    //做一个伪删除 把状态码变成4
    @Override
    public DeleteOrderVO deleteOrder(Integer orderId) {
        changeOrderState(orderId, 4);
        DeleteOrderVO deleteOrderVO = new DeleteOrderVO();
        deleteOrderVO.setCode(0);

        return deleteOrderVO;
    }
}
