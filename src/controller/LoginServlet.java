package controller;

import Service.UserService;
import Utils.JsonResponse;
import Utils.PayLoadValue;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.util.StringUtils;
import domain.User;
import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;


//处理用户登录请求
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    /**
     * session处理，进行预处理
     * 发送两种请求，一种登录请求
     * 一种书查询是否已经登录请求，就行从session内取得用户的登录信息
     * 一种是，登出请求，从当前的会话内清除当前的user对象
     * LoginQuery : 1 进行登录查询
     * LoginQuery ：2 进行用户登录
     * LoginQuery ：3 清除用户信息
     */

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

        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        String LoginQuery = req.getParameter("LoginQuery");
        if(Objects.equals(LoginQuery, "1")){
            User user = (User) req.getSession().getAttribute("user");
            if(user==null){
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","当前用户未登录");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }else {
                JsonResponse json = new JsonResponse(200);
                json.addAttribute("info","用户登录成功");
                json.addAttribute("username",user.getUsername());
                json.addAttribute("LoginTime",user.getLoginTime().getTime()+"");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
        }
        else if(Objects.equals(LoginQuery, "2")){
            //登录请求
            req.getSession().setAttribute("user",null);
            PayLoadValue payLoad =new PayLoadValue(req);
            String username = payLoad.getString("username");
            String password  = payLoad.getString("password");

            //执行查询的框架
            UserService userService = new UserService();
            User user = userService.login(username,password);
            // TODO 这里需要将这个user保存到session内

            if(user!=null){
                // XXX 这里需要进行优化
                //登录成功，需要通过session来保存登录对象,之后可以通过session来直接或取
                req.getSession().setAttribute("user",user);

                Cookie cookie = new Cookie("JSESSIONID",req.getSession().getId());
                cookie.setMaxAge(30*60*24*60);
                resp.addCookie(cookie);

                JsonResponse json = new JsonResponse(200);
                json.addAttribute("info","用户登录成功");
                json.addAttribute("username",username);
                json.addAttribute("LoginTime",user.getLoginTime().getTime()+"");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","用户名或密码错误");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
        }else if(Objects.equals(LoginQuery, "3")){
            //登出请求
            req.getSession().setAttribute("user",null);
            JsonResponse json = new JsonResponse(200);
            json.addAttribute("success","登出成功");
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
