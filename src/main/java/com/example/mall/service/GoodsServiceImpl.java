package com.example.mall.service;

import com.example.mall.mapper.GoodsMapper;
import com.example.mall.model.bo.AddGoodsBO;
import com.example.mall.model.bo.AddTypeBO;
import com.example.mall.model.po.GoodsPO;
import com.example.mall.model.po.GoodsSpecPO;
import com.example.mall.model.po.GoodsTypePO;
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
}
