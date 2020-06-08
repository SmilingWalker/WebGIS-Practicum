package controller;

import DAO.DButil;
import Utils.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class infoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //发送详细信息搜索请求
        //解决中文乱码问题
        req.setCharacterEncoding("UTF-8");
        //解决跨域问题
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Cache-Control","no-cache");
        //接收查询的Ajax请求
        int fid =Integer.parseInt(req.getParameter("fid"));
        System.out.println(fid);
        //执行查询的框架
        String searchResult = DButil.getInfoByGid(fid);
        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        if(searchResult!=null){
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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        //解决跨域问题
        resp.setHeader("access-control-allow-headers","Authorization, Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, X-Requested-By, If-Modified-Since, X-File-Name, X-File-Type, Cache-Control, Origin");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Cache-Control","no-cache");
        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        JsonResponse json = new JsonResponse(202);
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
    }
}
