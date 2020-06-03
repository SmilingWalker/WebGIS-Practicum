package controller;

import DAO.DButil;
import Utils.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println("-------------------------");
        String searchResult = DButil.SelectByName("武");
        resp.setStatus(200);
        resp.setHeader("content-type", "text/html;charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'/>");
        writer.write(searchResult);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决中文乱码问题
        req.setCharacterEncoding("UTF-8");
        System.out.println("=========================");
        //接收查询的Ajax请求
        String query = req.getParameter("name");
        System.out.println(query);

        //执行查询的框架
        String searchResult = DButil.SelectByName(query);
        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        if(query!=null&&searchResult!=null){
            JsonResponse json = new JsonResponse(200);
            json.addAttribute("geojson",searchResult);
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
        }
        else {
            JsonResponse json = new JsonResponse(202);
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
        }
    }


}
