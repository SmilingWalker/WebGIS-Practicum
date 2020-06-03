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

//处理用户登录请求
public class LoginServlet extends HttpServlet {
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

        System.out.println(username+ "++++" +password);

        //执行查询的框架
        UserService userService = new UserService();
        User user = userService.login(username,password);
        // TODO 这里需要将这个user保存到session内
        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        if(user!=null){
            // XXX 这里需要进行优化
            //登录成功，需要通过session来保存登录对象,之后可以通过session来直接或取
            req.getSession().setAttribute("user",user);

            JsonResponse json = new JsonResponse(200);
            json.addAttribute("name","用户登录成功");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
        }
        else {
            JsonResponse json = new JsonResponse(202);
            json.addAttribute("error","用户名或密码错误");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
        }
    }
}
