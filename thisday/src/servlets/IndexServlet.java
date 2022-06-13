package servlets;

import dao.impl.FruitDAOImpl;
import myspringmvc.ViewBaseServlet;
import pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author rkwpro
 * @email rkwpro@163.com
 * @create 2022-06-11 17:24
 */
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Integer pageNo = 1;
        String oper = req.getParameter("oper");
        if(oper != null && "search".equals(oper)){
            pageNo = 1;
        }
        else
        {
            String pageNoStr = req.getParameter("pageNo");
            if(pageNoStr!=null&&!"".equals(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }
        }


        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(pageNo);
        int fruitCount = fruitDAO.getFruitCount();
        HttpSession session = req.getSession();

        session.setAttribute("fruitList",fruitList);
        session.setAttribute("count",(fruitCount + 5  - 1)/5);
        if(pageNo < 1 || pageNo > (fruitCount + 5  - 1)/5) {
            pageNo = 1;
        }
        session.setAttribute("pageNo",pageNo);
        super.processTemplate("index",req,resp);


    }
}
