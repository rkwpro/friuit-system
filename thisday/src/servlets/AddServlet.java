package servlets;

import dao.FruitDAO;
import dao.impl.FruitDAOImpl;
import myspringmvc.ViewBaseServlet;
import pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rkwpro
 * @email rkwpro@163.com
 * @create 2022-06-13 1:40
 */
@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
   private FruitDAO fruitDao = new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        System.out.println("ddddddddddddddddddddd");
        super.processTemplate("add",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        System.out.println("ssssssssssssssss");
        int fid = 0;
        String fcount = req.getParameter("fcount");
        int fcountInt = Integer.parseInt(fcount);
        String fname = req.getParameter("fname");
        String price = req.getParameter("price");
        int priceInt = Integer.parseInt(price);
        String remark = req.getParameter("remark");
        fruitDao.addFruit(new Fruit(fid,fname,priceInt,fcountInt,remark));
        System.out.println("这是什么啊");
        resp.sendRedirect("index");


    }
}
