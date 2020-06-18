package controller;

import DAO.DButil;
import Utils.JsonResponse;
import Utils.PayLoadValue;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;

public class UpdateInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /***
         * 为了接口方便，这里进行定义
         * 传入参数有三种 type
         * type = 1 :  表示添加新的城市信息
         * type = 2 : 表示对城市信息进行修改
         * type = 3 : 表示删除某个城市记录
         */
        //解决中文乱码问题
        req.setCharacterEncoding("UTF-8");
        //解决跨域问题
//        resp.setHeader("Access-Control-Allow-Origin", "*");
        //允许cookie
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        resp.setHeader("Cache-Control","no-cache");
        resp.setCharacterEncoding("UTF-8");

        //XXX 这里需要通过session进行判断，如果有则表示已经登录，而且没有过期，如果没有表示登录已经过期
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            JsonResponse json = new JsonResponse(10001);
            json.addAttribute("error","用户登录信息已过期");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
            return;
        }
        String type = req.getParameter("type");
        if(type.equals("1")){
            //添加城市
            PayLoadValue payLoad =new PayLoadValue(req);
            boolean create = DButil.CreateNewCity(payLoad.getResult());
            if(!create){
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","信息更新失败，请检查内容");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
                return;
            }
            else {
                JsonResponse json = new JsonResponse(200);
                //把更新后的数据传递给用户端
                json.addAttribute("info","城市添加成功");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
                return;
            }
        }
        else if(Objects.equals(type, "2")){
            //更新payload
            PayLoadValue payLoad =new PayLoadValue(req);
            boolean alter = DButil.UpdateInfoAllById(payLoad.getResult());

            //接收更新的Ajax请求
            if(!alter){
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","信息更新失败，请检查内容");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                int id = payLoad.getResult().getInteger("id");
                JsonResponse json = new JsonResponse(200);
                //把更新后的数据传递给用户端
                json.addAttribute("geojson", DButil.getInfoByGid(id));
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }

        }
        else if(Objects.equals(type, "3")){
            //更新payload
            PayLoadValue payLoad =new PayLoadValue(req);
            int id = payLoad.getResult().getInteger("id");
            boolean delete = DButil.DeleteCity(id);

            //接收更新的Ajax请求
            if(!delete){
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","信息更新失败，请检查内容");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                JsonResponse json = new JsonResponse(200);
                json.addAttribute("info","删除信息成功");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }

        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        //解决跨域问题
        resp.setHeader("access-control-allow-headers","Authorization, Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, X-Requested-By, If-Modified-Since, X-File-Name, X-File-Type, Cache-Control, Origin");
//        resp.setHeader("Access-Control-Allow-Origin", "*");

        //允许cookie
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        resp.setHeader("Cache-Control","no-cache");
        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        JsonResponse json = new JsonResponse(202);
        PrintWriter writer = resp.getWriter();
        writer.write(json.toString());
    }
}
