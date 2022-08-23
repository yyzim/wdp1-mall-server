package com.example.mall.service;

import com.example.mall.mapper.GoodsMapper;
import com.example.mall.model.po.GoodsTypePO;
import com.example.mall.model.vo.MallGetTypeVO;
import com.example.mall.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MallIndexServiceImpl
 * @Description
 * @Date 2022-08-22 9:37
 * @Created by Yang Yi-zhou
 */
public class MallIndexServiceImpl implements MallIndexService {
    @Override
    public MallGetTypeVO getType() {
        //mapper
        SqlSession session = MybatisUtils.openSession();
        GoodsMapper mapper = session.getMapper(GoodsMapper.class);
        //去获取商品类目信息
        List<GoodsTypePO> typePOList = mapper.selectAllGoodsTypeFromTableGoodsType();
        //VO
        MallGetTypeVO mallGetTypeVO = new MallGetTypeVO();
        //set code
        mallGetTypeVO.setCode(0);
        //set dataList
        List<MallGetTypeVO.DataDTO> dataDTOList = new ArrayList<>();
        for (GoodsTypePO goodsTypePO : typePOList) {
            dataDTOList.add(new MallGetTypeVO.DataDTO(goodsTypePO.getId(), goodsTypePO.getName()));
        }
        mallGetTypeVO.setData(dataDTOList);

        session.close();
        return mallGetTypeVO;
    }
}
