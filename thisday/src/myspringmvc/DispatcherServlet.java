package myspringmvc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author rkwpro
 * @email rkwpro@163.com
 * @create 2022-06-19 21:06
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
   private Map<String,Object> beanMap = new HashMap<>();
    public DispatcherServlet(){
//        反射
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
//        工厂
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
//            获取所有的bean结点
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for(int i = 0 ; i < beanNodeList.getLength() ; i ++){
                Node beanNode = beanNodeList.item(i);
                if(beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Object beanObj = Class.forName(className).newInstance();
                    beanMap.put(beanId,beanObj);

                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        /*
            for example: servletPath 接收的信息 /hello.do ————> "hello"
                       : but we need key:"hello" and value:"helloController"
                       : so you need applicationContext.xml to tell this
                       : if you do it
                       : then you can use method to read this xml
                       : through this bean we can init this xml
         */
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastDotIndex);
        Object controllerBeanObj = beanMap.get(servletPath);
//        ************************************************************
        String operate = null;
        operate = req.getParameter("operate");
        if(operate == null || "".equals(operate))
        {
            operate = "index";
        }
        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for(Method method:methods){
                if(operate.equals(method.getName()))
                {
                    Parameter[] parameters = method.getParameters();
                    //存放参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for(int i = 0 ; i < parameters.length;i ++ ){
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if("req".equals(parameterName)){
                            parameterValues[i] = req;
                        }else if("resp".equals(parameterName)){
                            parameterValues[i] = resp;
                        }else if("sesssion".equals(parameterName)){
                            parameterValues[i] = req.getSession();
                        }else
                        {
                            String parameterValue = req.getParameter(parameterName);
                            String typeName = parameter.getType().getName();
                            Object parameterObj = parameterValue;
                            if(parameterValue != null)
                            if("java.lang.Integer".equals(typeName)){
                                parameterObj = Integer.parseInt(parameterValue);
                            }
                            parameterValues[i] = parameterObj;
                        }



                    }
                    method.setAccessible(true);

                    String methodReturnStr = String.valueOf(method.invoke(controllerBeanObj, parameterValues));
                    //好棒的代码
                    if(methodReturnStr.startsWith("redirect:")){ //example:"redirect:fruit.do"
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    }else
                    {
                        if(methodReturnStr != null || !"".equals(methodReturnStr))
                        {
                            super.processTemplate(methodReturnStr,req,resp);
                        }
                    }

                }
            }
               /*
                        下面是源码
                         public static String valueOf(Object obj) {
                             return (obj == null) ? "null" : obj.toString();
                        }
                 */

//            }else {
//                System.out.println("可能是你的数据库没打开");
//                throw new RuntimeException("OPERATOR值非法,或者是可能是你的数据库没打开");
//            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
