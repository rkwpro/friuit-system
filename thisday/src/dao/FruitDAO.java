package dao;

import pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    //查询库存列表
    List<Fruit> getFruitList(String keyword,Integer pageNo);

    Fruit getFruitByFid(Integer fid);

    //新增库存
    void addFruit(Fruit fruit);

    //修改库存
    int updateFruit(Fruit fruit);

    //根据名称查询特定库存
    Fruit getFruitByFname(String fname);

    //删除特定库存记录
    void delFruit(int fid);

    //查询库存总记录条数
    int getFruitCount(String keyword );
}
