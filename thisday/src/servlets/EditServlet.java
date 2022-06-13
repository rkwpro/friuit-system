package servlets;

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

/**
 * @author rkwpro
 * @email rkwpro@163.com
 * @create 2022-06-12 18:19
 */
@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
//        System.out.println();
//        System.out.println(fidStr);
//        System.out.println();
        if (fidStr != null && !"".equals(fidStr))
        {  // System.out.println();
           // System.out.println(fidStr);

            int fid = Integer.parseInt(fidStr);
            System.out.println();
            System.out.println(fid);
            System.out.println();
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            req.setAttribute("fruit",fruit);
            super.processTemplate("edit",req,resp);

        }
    }
}
