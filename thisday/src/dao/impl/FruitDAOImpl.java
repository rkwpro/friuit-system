package dao.impl;

import dao.FruitDAO;

import dao.base.BaseDAO;
import pojo.Fruit;

import java.nio.LongBuffer;
import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList(Integer pageNo) {

        return super.executeQuery("select * from t_fruit limit ? , 5",(pageNo - 1) * 5);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return  super.load("select * from t_fruit where fid = ?" , fid);
    }


    @Override
    public void addFruit(Fruit fruit) {
        String sql = "insert into t_fruit values(0,?,?,?,?)";
        int count = super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark()) ;
        //insert语句返回的是自增列的值，而不是影响行数
        //System.out.println(count);
        //return count>0;
    }

    @Override
    public int updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ? , price = ?,fcount = ? ,remark = ?  where fid = ? " ;
       return super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getFid());
    }

    @Override
    public Fruit getFruitByFname(String fname) {
        return super.load("select * from t_fruit where fname like ? ",fname);
    }

    @Override
    public void delFruit(int fid) {
        String sql = "delete from t_fruit where fid = ? " ;
        super.executeUpdate(sql,fid);
    }

    @Override
    public int getFruitCount() {
        return ((Long) super.executeComplexQuery("select count(*) from t_fruit")[0]).intValue();
    }
}