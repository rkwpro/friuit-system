package controllers;

import dao.FruitDAO;
import dao.impl.FruitDAOImpl;
import myspringmvc.ViewBaseServlet;
import pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.*;
/**
 * @author rkwpro
 * @email rkwpro@163.com
 * @create 2022-06-14 14:10
 */

public class FruitController {
 
    public FruitDAO fruitDAO = new FruitDAOImpl();

//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//    }
    private String index(String oper,String keyword ,Integer pageNo,HttpServletRequest req)  {

        HttpSession session = req.getSession();
        if(pageNo == null)
            pageNo = 1;
//        Integer pageNo = 1;
     //  String keyword = null;
       // String oper = req.getParameter("oper");
        if(oper != null && "search".equals(oper)){
            pageNo = 1;
//            keyword = req.getParameter("keyword");
            if(keyword == null || "".equals(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);
        }
        else
        {
//        String pageNoStr = req.getParameter("pageNo");
//            if(pageNoStr!=null&&!"".equals(pageNoStr)){
//                pageNo = Integer.parseInt(pageNoStr);
//            }
            Object keywordObj = session.getAttribute("keyword");
            if(keywordObj != null) {
                keyword = (String) keywordObj;
            }else{
                keyword = "";
            }

        }

//        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword,pageNo);
        int fruitCount = fruitDAO.getFruitCount(keyword);


        session.setAttribute("fruitList",fruitList);
        session.setAttribute("count",(fruitCount + 5  - 1)/5);
        if(pageNo < 1 || pageNo > (fruitCount + 5  - 1)/5) {
            pageNo = 1;
        }
        session.setAttribute("pageNo",pageNo);
        return "index";
        //super.processTemplate("index",req,resp);

    }
    private String add(Integer fid,String fname ,Integer price,Integer fcount,String remark) {
        if(fid == null)
            return "add";
        fruitDAO.addFruit(new Fruit(fid,fname,price,fcount,remark));
          return "redirect:fruit.do";
    }

        private String del(Integer fid)  {
        if(fid != null)
            fruitDAO.delFruit(fid);
            return "redirect:fruit.do";
        }
    protected String edit(Integer fid,HttpServletRequest req){

        if (fid != null)
        {
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            req.setAttribute("fruit",fruit);
            return "edit";
        }
        return "error";
    }
    private String update(Integer fid,String fname ,Integer price,Integer fcount,String remark) {
        Fruit fruit = new Fruit(fid,fname,price,fcount,remark);
//        fid, String fname, Integer price, Integer fcount, String remark
        fruitDAO.updateFruit(fruit);
        /*
             resp.sendRedirect("fruit.do");
             本来的代码重定向，现在方法不去重定向了，返回值让上层去重定向
         */
        return "redirect:fruit.do";
//      重定向重新获得请求那你
    }

}
// error:java.lang.IllegalArgumentException: argument type mismatch
