package controller;

import Service.UserService;
import Utils.JsonResponse;
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
        //接收查询的Ajax请求 username password
        String username = req.getParameter("username");
        String password = req.getParameter("password");

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
            json.addAttribute("LoginTime",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(user.getRegisterTime()));
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
}
