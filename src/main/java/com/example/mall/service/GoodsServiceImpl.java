package com.example.mall.service;

import com.example.mall.mapper.GoodsMapper;
import com.example.mall.mapper.UserMapper;
import com.example.mall.model.bo.AddGoodsBO;
import com.example.mall.model.bo.AddTypeBO;
import com.example.mall.model.bo.ReplyBO;
import com.example.mall.model.bo.UpdateGoodsBO;
import com.example.mall.model.po.*;
import com.example.mall.model.vo.*;
import com.example.mall.util.MybatisUtils;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname GoodsServiceImpl
 * @Description
 * @Date 2022-08-20 10:43
 * @Created by Yang Yi-zhou
 */
public class GoodsServiceImpl implements GoodsService {
    @Override
    public GetTypeVO getType() {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //获取所有类型
        List<GoodsTypePO> goodsTypePOList = mapper.selectAllGoodsTypeFromTableGoodsType();
        //PO封装进VO
        GetTypeVO getTypeVO = new GetTypeVO();
        getTypeVO.setCode(0);
        List<GetTypeVO.DataVO> data = new ArrayList<>();
        for (GoodsTypePO goodsTypePO : goodsTypePOList) {
            data.add(new GetTypeVO.DataVO(goodsTypePO.getId(), goodsTypePO.getName()));
        }
        getTypeVO.setData(data);
        //关闭资源
        session.close();

        return getTypeVO;
    }

    @Override
    public GetGoodsByTypeVO getGoodsByType(int typeId) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //根据typeID获取商品
        List<GoodsPO> goodsPOList = mapper.selectGoodsFromTableGoodsByTypeId(typeId);
        //封装进VO
        GetGoodsByTypeVO getGoodsByTypeVO = new GetGoodsByTypeVO();
        getGoodsByTypeVO.setCode(0);
        List<GetGoodsByTypeVO.DataDTO> data = new ArrayList<>();
        for (GoodsPO goodsPO : goodsPOList) {
            //根据goodsId获取库存
            Integer stockNum = mapper.selectStockNumFromTableGoodsSpecByGoodsId(goodsPO.getId());
            data.add(new GetGoodsByTypeVO.DataDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), goodsPO.getPrice(), goodsPO.getTypeId(), stockNum));
        }
        getGoodsByTypeVO.setData(data);
        session.close();

        return getGoodsByTypeVO;
    }

    @Override
    public AddTypeVO addType(AddTypeBO addTypeBO) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //解析BO
        String name = addTypeBO.getName();
        //VO
        AddTypeVO addTypeVO = new AddTypeVO();
        //检查是否为空
        if (StringUtils.isBlank(name)) {
            //添加失败
            addTypeVO.setCode(10000);
            addTypeVO.setMessage("输入为空");
            session.rollback();
            session.close();
            return addTypeVO;
        }
        //检查是否已经存在数据库中
        GoodsTypePO goodsTypePO = mapper.selectGoodsTypeFromTableGoodsTypeByName(name);
        if (goodsTypePO != null) {
            //添加失败
            addTypeVO.setCode(10000);
            addTypeVO.setMessage("添加的类目已存在");
            session.rollback();
            session.close();
            return addTypeVO;
        }
        //添加进数据库
        Integer affectedRows = mapper.insertIntoTableGoodsType(name);
        if (affectedRows == 0) {
            //添加失败
            addTypeVO.setCode(10000);
            addTypeVO.setMessage("添加失败");
            session.rollback();
            session.close();
            return addTypeVO;
        } else {
            addTypeVO.setCode(0);
            session.commit();
        }
        session.close();

        return addTypeVO;
    }

    @Override
    public ImgUploadVO imgUpload(HttpServletRequest req, HttpServletResponse resp) throws IOException, FileUploadException {
        //VO
        ImgUploadVO imgUploadVO = new ImgUploadVO(10000, "上传失败");
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iter = upload.getItemIterator(req);
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            String name = item.getFieldName();
            InputStream stream = item.openStream();
            if (item.isFormField()) {
                System.out.println("Form field " + name + " with value "
                        + Streams.asString(stream) + " detected.");
            } else {
                System.out.println("File field " + name + " with file name "
                        + item.getName() + " detected.");
                //保存到本地
                //使用时间戳作为图片名
                String imgName = "/static/image/" + System.currentTimeMillis() + item.getName();
                String realPath = req.getServletContext().getRealPath(imgName);
                File file = new File(realPath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                //保存文件
                int length = 0;
                byte[] data = new byte[1024];
                while ((length = stream.read(data)) != -1) {
                    fos.write(data, 0, length);
                }
                //关闭资源
                stream.close();
                fos.flush();
                fos.close();

                //VO
                imgUploadVO.setCode(0);
                imgUploadVO.setData(imgName);

            }
        }
        return imgUploadVO;
    }

    @Override
    public AddGoodsVO addGoods(AddGoodsBO addGoodsBO) {
        //解析BO
        String name = addGoodsBO.getName();
        //图片需要转发到服务器的地址
        String img = StringUtils.isBlank(addGoodsBO.getImg()) ? null : "http://localhost:8084" + addGoodsBO.getImg();
        Integer typeId = Integer.parseInt(addGoodsBO.getTypeId());
        String desc = addGoodsBO.getDesc();
        //规格信息
        List<AddGoodsBO.SpecListDTO> specList = addGoodsBO.getSpecList();
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //封装商品进PO
        GoodsPO goodsPO = new GoodsPO(null, img, name, desc, null, typeId);
        //插入数据库
        Integer goodsAffectedRows = mapper.insertGoodsIntoTableGoods(goodsPO);
        Integer goodsId = goodsPO.getId();
        //记录插入规格中的最低价格
        Double minPrice = Double.MAX_VALUE;
        //封装规格信息
        List<GoodsSpecPO> specPOList = new ArrayList<>();
        for (AddGoodsBO.SpecListDTO spec : specList) {
            minPrice = Math.min(minPrice, Double.parseDouble(spec.getUnitPrice()));
            specPOList.add(new GoodsSpecPO(null, goodsId, spec.getSpecName(), Integer.parseInt(spec.getStockNum()), Double.parseDouble(spec.getUnitPrice())));
        }
        //把规格信息插入数据库
        Integer specAffectedRows = mapper.insertGoodsSpecListIntoTableGoodsSpec(specPOList);
        //维护price为规格中的最低价格
        //查询当前的最低价格
        Double curPrice = mapper.selectPriceFromTableGoodsById(goodsId);
        if (curPrice == null || curPrice > minPrice) {
            //更新商品价格
            mapper.updateTableGoodsSetPriceById(minPrice, goodsId);
        }
        //VO
        AddGoodsVO addGoodsVO = new AddGoodsVO();
        if (goodsAffectedRows == 0 || specAffectedRows == 0) {
            //添加失败
            addGoodsVO.setCode(10000);
            addGoodsVO.setMessage("添加商品失败");
            session.rollback();
            session.close();
            return addGoodsVO;
        } else {
            //添加成功
            addGoodsVO.setCode(0);
            session.commit();
        }
        session.close();
        return addGoodsVO;
    }

    @Override
    public UpdateGoodsVO updateGoods(UpdateGoodsBO updateGoodsBO) {
        //解析BO
        Integer id = Integer.parseInt(updateGoodsBO.getId());
        String name = updateGoodsBO.getName();
        String img = updateGoodsBO.getImg();
        String desc = updateGoodsBO.getDesc();
        Integer typeId = updateGoodsBO.getTypeId();
        List<UpdateGoodsBO.SpecListDTO> specList = updateGoodsBO.getSpecList();
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //先删除原有数据
        Integer deleteGoodsAffectedRows = mapper.deleteGoodsById(id);
        Integer deleteGoodsSpecAffectedRows = mapper.deleteGoodsSpecByGoodsId(id);
        //封装规格信息
        List<GoodsSpecPO> specPOList = new ArrayList<>();
        //维护Price字段为最低价格
        Double minPrice = Double.MAX_VALUE;
        for (UpdateGoodsBO.SpecListDTO spec : specList) {
            minPrice = Math.min(minPrice, Double.parseDouble(spec.getUnitPrice()));
            specPOList.add(new GoodsSpecPO(null, id, spec.getSpecName(), spec.getStockNum(), Double.parseDouble(spec.getUnitPrice())));
        }
        //封装商品信息
        GoodsPO goodsPO = new GoodsPO(id, img, name, desc, minPrice, typeId);
        //插入数据库
        Integer insertGoodsAffectedRows = mapper.insertGoodsIntoTableGoods(goodsPO);
        Integer insertSpecListAffectedRows = mapper.insertGoodsSpecListIntoTableGoodsSpec(specPOList);
        //VO
        UpdateGoodsVO updateGoodsVO = new UpdateGoodsVO();
        //判断一下是否插入成功
        if (insertGoodsAffectedRows == 0 || insertSpecListAffectedRows != specList.size()) {
            //插入失败
            updateGoodsVO.setCode(10000);
            updateGoodsVO.setMessage("编辑商品失败");
            session.rollback();
        } else {
            updateGoodsVO.setCode(0);
            session.commit();
        }

        //关闭资源
        session.close();
        return updateGoodsVO;
    }

    @Override
    public GetGoodsInfoVO getGoodsInfo(int id) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //获取商品信息
        GoodsPO goodsPO = mapper.selectGoodsById(id);
        //获取规格信息
        List<GoodsSpecPO> specList = mapper.selectGoodsSpecListByGoodsId(id);
        //封装到VO返回
        GetGoodsInfoVO getGoodsInfoVO = new GetGoodsInfoVO();
        GetGoodsInfoVO.DataDTO.GoodsDTO goodsDTO = new GetGoodsInfoVO.DataDTO.GoodsDTO(goodsPO.getId(), goodsPO.getImg(), goodsPO.getName(), goodsPO.getDescription(), goodsPO.getTypeId(), goodsPO.getPrice());
        List<GetGoodsInfoVO.DataDTO.SpecsDTO> specsDTOList = new ArrayList<>();
        for (GoodsSpecPO spec : specList) {
            specsDTOList.add(new GetGoodsInfoVO.DataDTO.SpecsDTO(spec.getId(), spec.getName(), spec.getStockNum(), spec.getPrice()));
        }
        getGoodsInfoVO.setCode(0);
        getGoodsInfoVO.setData(new GetGoodsInfoVO.DataDTO(specsDTOList, goodsDTO));


        return getGoodsInfoVO;
    }

    @Override
    public DeleteGoodsVO deleteGoods(int id) {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //根据id去删除商品和规格信息
        Integer deleteGoodsAffectedRows = mapper.deleteGoodsById(id);
        Integer deleteGoodsSpecAffectedRows = mapper.deleteGoodsSpecByGoodsId(id);
        //VO
        DeleteGoodsVO deleteGoodsVO = new DeleteGoodsVO();
        //判断是否成功
        if (deleteGoodsAffectedRows != 0) {
            deleteGoodsVO.setCode(0);
            session.commit();
        } else {
            deleteGoodsVO.setCode(10000);
            deleteGoodsVO.setMessage("删除失败");
            session.rollback();
        }

        session.close();
        return deleteGoodsVO;
    }

    @Override
    public NoReplyMsgVO noReplyMsg() {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //获取未回复的留言 未回复的state为1
        List<MsgPO> msgPOList = mapper.selectNoReplyMsgList();
        //VO
        NoReplyMsgVO noReplyMsgVO = new NoReplyMsgVO();

        //set code
        noReplyMsgVO.setCode(0);
        //set data
        List<NoReplyMsgVO.DataDTO> dataDTOList = new ArrayList<>();
        for (MsgPO msgPO : msgPOList) {
            //根据goodsIdh获取goodsName
            GoodsPO goodsPO = mapper.selectGoodsById(msgPO.getGoodsId());
            String goodsName = goodsPO.getName();
            //根据userId获取userName
            UserMapper userMapper = session.getMapper(UserMapper.class);
            UserPO userPO = userMapper.selectUserById(msgPO.getUserId());
            String userName = userPO.getNickname();

            //封装
            dataDTOList.add(new NoReplyMsgVO.DataDTO(msgPO.getId(), msgPO.getUserId(), msgPO.getGoodsId(), msgPO.getContent(), msgPO.getState(), msgPO.getCreateTime().toString(), new NoReplyMsgVO.DataDTO.GoodsDTO(goodsName), new NoReplyMsgVO.DataDTO.UserDTO(userName)));
        }

        noReplyMsgVO.setData(dataDTOList);

        return noReplyMsgVO;
    }

    @Override
    public RepliedMsgVO repliedMsg() {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //获取未回复的留言 未回复的state为1
        List<MsgPO> msgPOList = mapper.selectRepliedMsgMsgList();
        //VO
        RepliedMsgVO repliedMsgVO = new RepliedMsgVO();

        //set code
        repliedMsgVO.setCode(0);
        //set data
        List<RepliedMsgVO.DataDTO> dataDTOList = new ArrayList<>();
        for (MsgPO msgPO : msgPOList) {
            //根据goodsIdh获取goodsName
            GoodsPO goodsPO = mapper.selectGoodsById(msgPO.getGoodsId());
            String goodsName = goodsPO.getName();
            //根据userId获取userName
            UserMapper userMapper = session.getMapper(UserMapper.class);
            UserPO userPO = userMapper.selectUserById(msgPO.getUserId());
            String userName = userPO.getNickname();

            //封装
            dataDTOList.add(new RepliedMsgVO.DataDTO(msgPO.getId(), msgPO.getUserId(), msgPO.getGoodsId(), msgPO.getContent(), msgPO.getReplyContent(), msgPO.getState(), msgPO.getCreateTime().toString(), new RepliedMsgVO.DataDTO.GoodsDTO(goodsName), new RepliedMsgVO.DataDTO.UserDTO(userName)));
        }

        repliedMsgVO.setData(dataDTOList);

        return repliedMsgVO;
    }

    @Override
    public ReplyVO reply(ReplyBO replyBO) {
        //解析BO
        Integer msgId = replyBO.getId();
        String replyContent = replyBO.getContent();
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);

        Timestamp replyTime = new Timestamp(System.currentTimeMillis());
        //更改数据库 需要插入replyContent和state
        Integer affectRows = mapper.updateReplyContentAndStateById(msgId, replyContent, replyTime);
        //VO
        ReplyVO replyVO = new ReplyVO();
        if (affectRows == 0) {
            replyVO.setCode(10000);
            replyVO.setMessage("回复失败");
            session.rollback();
        } else {
            replyVO.setCode(0);
            session.commit();
        }

        //关闭资源
        session.close();


        return replyVO;
    }
}
