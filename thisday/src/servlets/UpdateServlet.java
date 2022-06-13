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
 * @create 2022-06-12 21:11
 */
@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html;charset=GBK");
        req.setCharacterEncoding("utf-8");
        String fid = req.getParameter("fid");
        String fname = req.getParameter("fname");
        String price = req.getParameter("price");
        String fcount = req.getParameter("fcount");
        String remark = req.getParameter("remark");
        int priceInt = Integer.parseInt(price);
        int fcountInt = Integer.parseInt(fcount);
        int fidInt = Integer.parseInt(fid);
        Fruit fruit = new Fruit(fidInt,fname,priceInt,fcountInt,remark);
//        fid, String fname, Integer price, Integer fcount, String remark
        fruitDAO.updateFruit(fruit);
//        super.processTemplate("index",req,resp);
//      重定向重新获得请求那你
        resp.sendRedirect("index");
    }
}
