package com.example.data;

import com.example.mall.mapper.GoodsMapper;
import com.example.mall.model.po.GoodsPO;
import com.example.mall.model.po.GoodsSpecPO;
import com.example.mall.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname ImportGoodsData
 * @Description
 * @Date 2022-08-24 14:02
 * @Created by Yang Yi-zhou
 */
public class ImportGoodsData {
    static String DataRootPath = "C:\\developSpace\\wdp1\\data\\";


    public static void main(String[] args) throws IOException {
        String[] dataName = {"手机", "女装", "男装", "电脑", "美妆", "数码", "图书", "男鞋", "女鞋", "珠宝", "钟表"};

        int count = 0;

        for (int i = 0; i < dataName.length; i++) {
            String pattern = "name=(.*),price=(.*),img=(.*)";
            Pattern r = Pattern.compile(pattern);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(DataRootPath + dataName[i] + ".txt"));
            String line;

//        line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll("\t", " ");
                Matcher matcher = r.matcher(line);
                if (matcher.find()) {
                    String name = matcher.group(1);
                    Double price = null;
                    try {
                        price = Double.parseDouble(matcher.group(2));
                    } catch (NumberFormatException e) {
                        price = 2000.0;
                    }
                    String img = matcher.group(3);

                    GoodsPO goodsPO = new GoodsPO();
                    goodsPO.setName(name);
                    goodsPO.setPrice(price);
                    goodsPO.setImg(img);
                    //手机
                    goodsPO.setTypeId(i + 1);

                    SqlSession session = MybatisUtils.openSession();
                    GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
                    Integer afr1 = goodsMapper.insertGoodsIntoTableGoods(goodsPO);

                    // 构造一下规格
                    List<GoodsSpecPO> goodsSpecPOList = new ArrayList<>();
                    goodsSpecPOList.add(new GoodsSpecPO(null, goodsPO.getId(), "黑色", 10000, price));
                    goodsSpecPOList.add(new GoodsSpecPO(null, goodsPO.getId(), "白色", 10000, price));
                    goodsSpecPOList.add(new GoodsSpecPO(null, goodsPO.getId(), "灰色", 10000, price));

                    //插入一下规格
                    Integer afr2 = goodsMapper.insertGoodsSpecListIntoTableGoodsSpec(goodsSpecPOList);

                    if (afr1 > 0 && afr2 > 0) {
                        session.commit();
                    } else {
                        session.rollback();
                    }
                    session.close();


                    System.out.println("正在插入第" + count++ + "条数据");
                }
            }
        }


    }
}
