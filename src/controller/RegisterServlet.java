package controller;

import Service.UserService;
import Utils.JsonResponse;
import Utils.PayLoadValue;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class RegisterServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决中文乱码问题
        req.setCharacterEncoding("UTF-8");

        //解决跨域问题
//        resp.setHeader("Access-Control-Allow-Origin", "*");
        //允许cookie
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        resp.setHeader("Cache-Control","no-cache");

        //XXX 这里需要通过session进行判断，如果有则表示已经登录，而且没有过期，如果没有表示登录已经过期
        User olduser = (User) req.getSession().getAttribute("user");
        if(olduser!=null){
            JsonResponse json = new JsonResponse(10001);
            json.addAttribute("error","当前用户已登录，请注销后操作");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
            return;
        }

        //接收查询的Ajax请求 username password
        PayLoadValue payLoad =new PayLoadValue(req);
        String username = payLoad.getString("username");
        String password  = payLoad.getString("password");

        // TODO  这里还可以有更多的属性，比如说性别，年龄，等等，等待完善

        System.out.println(username+"++++" +password);

        //执行查询的框架
        UserService userService = new UserService();
        User user = userService.register(username,password);
        //这里需要将这个user保存到session内
        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        if(user!=null){
            JsonResponse json = new JsonResponse(200);
            json.addAttribute("info","用户登录成功");
            json.addAttribute("username",username);
            json.addAttribute("LoginTime",user.getLoginTime().getTime()+"");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
        }
        else {
            JsonResponse json = new JsonResponse(202);
            json.addAttribute("error","用户名已存在");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
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
