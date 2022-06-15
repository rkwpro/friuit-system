package servlets;

import dao.FruitDAO;
import dao.impl.FruitDAOImpl;
import javassist.compiler.MemberResolver;
import myspringmvc.ViewBaseServlet;
import pojo.Fruit;

import javax.management.RuntimeErrorException;
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
@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    public FruitDAO fruitDAO = new FruitDAOImpl();
    String operate = null;
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        operate = req.getParameter("operate");
        if(operate == null || "".equals(operate))
        {
            operate = "index";
        }

            System.out.println("hewfrewfwesgvsdvgdsfcdsf............");
        System.out.println(operate);
        Method[] methods = this.getClass().getDeclaredMethods();
        for(Method m:methods){
            String methodName = m.getName();
            if(operate.equals(methodName))
            {
                try {
                    System.out.println();
                    System.out.println(this);
                    System.out.println();
                    m.invoke(this,req,resp);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }
           System.out.println("find not this method>>>>>>......");
        throw new RuntimeException("OPERATOR值非法");

    }
    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer pageNo = 1;
        String keyword = null;
        String oper = req.getParameter("oper");
        if(oper != null && "search".equals(oper)){
            pageNo = 1;
            keyword = req.getParameter("keyword");
            if(keyword == null || "".equals(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);
        }
        else
        { String pageNoStr = req.getParameter("pageNo");
            if(pageNoStr!=null&&!"".equals(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }
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
        super.processTemplate("index",req,resp);


    }
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");

        System.out.println("ssssssssssssssss");
        int fid = 0;
        String fcount = req.getParameter("fcount");
        if(fcount == null || "".equals(fcount))
        super.processTemplate("add",req,resp);

        int fcountInt = Integer.parseInt(fcount);
        String fname = req.getParameter("fname");
        String price = req.getParameter("price");
        int priceInt = Integer.parseInt(price);
        String remark = req.getParameter("remark");
        fruitDAO.addFruit(new Fruit(fid,fname,priceInt,fcountInt,remark));
        System.out.println("这是什么啊");


//        resp.sendRedirect("fruit.do");
        resp.sendRedirect("fruit.do");


    }

        private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("start open this del on html");
        String fid = req.getParameter("fid");
            int fidInt = Integer.parseInt(fid);
            fruitDAO.delFruit(fidInt);
            resp.sendRedirect("fruit.do");
        }
    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        System.out.println("start open this edit on html");
        operate = null;
        if (fidStr != null && !"".equals(fidStr))
        {
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            req.setAttribute("fruit",fruit);
            super.processTemplate("edit",req,resp);

        }
    }
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html;charset=GBK");
//        req.setCharacterEncoding("utf-8");

        String fid = req.getParameter("fid");
        if(fid == null || "".equals(fid))
            return ;
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
        resp.sendRedirect("fruit.do");
//        super.processTemplate("index",req,resp);
//      重定向重新获得请求那你


    }

}
