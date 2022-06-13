package servlets;

import dao.FruitDAO;
import dao.impl.FruitDAOImpl;
import myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rkwpro
 * @email rkwpro@163.com
 * @create 2022-06-13 0:42
 */
@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fid = req.getParameter("fid");
        int fidInt = Integer.parseInt(fid);
        fruitDAO.delFruit(fidInt);
        resp.sendRedirect("index");

    }
}
