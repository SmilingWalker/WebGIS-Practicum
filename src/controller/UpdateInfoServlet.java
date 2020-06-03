package controller;

import DAO.DButil;
import Utils.JsonResponse;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决中文乱码问题
        req.setCharacterEncoding("UTF-8");

        //XXX 这里需要通过session进行判断，如果有则表示已经登录，而且没有过期，如果没有表示登录已经过期

        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            JsonResponse json = new JsonResponse(10001);
            json.addAttribute("error","用户登录信息已过期");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
        }

        //接收更新的Ajax请求
        String alterType = req.getParameter("type");
        String alterValue = req.getParameter("content");
        String alterFid = req.getParameter("fid");
        if(alterFid.isEmpty()||alterType.isEmpty()||alterValue.isEmpty()||alterFid==null||alterType==null||alterValue==null){
            JsonResponse json = new JsonResponse(202);
            json.addAttribute("error","信息更新失败，请检查内容");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
            return;
        }
        boolean alter = DButil.UpdateInfoByFid(alterType,alterValue,Integer.parseInt(alterFid));

        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        if(alter){
            JsonResponse json = new JsonResponse(200);
            //把更新后的数据传递给用户端
            json.addAttribute("geojson",DButil.getInfoByGid(Integer.parseInt(alterFid)));
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
        }
        else {
            JsonResponse json = new JsonResponse(202);
            json.addAttribute("error","信息更新失败，请检查内容");
            PrintWriter writer = resp.getWriter();
            writer.write(json.toString());
        }
    }
}
