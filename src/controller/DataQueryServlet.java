package controller;

import DAO.DButil;
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
import java.util.Objects;

public class DataQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    /**
     *所有的数据查询请求都会到这里来，整个是一个汇总性的查询，根据查询字符串内的QueryType
     * 来判断到底是请求的何种数据
     * QueryType = 1 根据城市类型来查找每一种类型的城市数量
     *
     * QueryType = 2 热力图查询数据 返回空间字段，类型，数据
     *
     * QueryType = 3 路径查询接口 返回一个linestring ，空间字段only
     *
     * QueryType = 4 疫情信息查询接口，主要查询当天的疫情信息
     *
     * QueryType = 5 疫情信息查询接口，查询某个省份的疫情历史信息
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
        String QueryType = req.getParameter("QueryType");

        // TODO 这里需要将这个user保存到session内 目前没有判断权限，这里所有的用户都能够查询统计图表

        if(Objects.equals(QueryType, "1")){
            String  queryResult = DButil.queryCityClass();
            if(queryResult!=null){
                //查询成功
                JsonResponse json = new JsonResponse(200);
                json.addAttribute("result",queryResult);
                json.addAttribute("info","信息查询成功");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","数据获取失败，请检查信息");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
        }else if(Objects.equals(QueryType, "2")){
            String  queryResult = DButil.queryAllCity();
            if(queryResult!=null){
                //查询成功
                JsonResponse json = new JsonResponse(200);
                json.addAttribute("geojson",queryResult);
                json.addAttribute("info","信息查询成功");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","数据获取失败，请检查信息");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
        }
        else if(Objects.equals(QueryType, "3")){
            PayLoadValue payLoadValue = new PayLoadValue(req);
            String routeQuery = DButil.RouteQuery(payLoadValue.getResult());
            if(routeQuery!=null){
                //查询成功
                JsonResponse json = new JsonResponse(200);
                json.addAttribute("geojson",routeQuery);
                json.addAttribute("info","信息查询成功");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","路径查询失败，当前的起始点旁没有相对较近的道路，请检查信息");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
        }
        else if(Objects.equals(QueryType, "4")){
            String  queryResult = DButil.queryProvince();
            if(queryResult!=null){
                //查询成功
                JsonResponse json = new JsonResponse(200);
                json.addAttribute("geojson",queryResult);
                json.addAttribute("info","信息查询成功");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","数据获取失败，请检查信息");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
        }
        else if(Objects.equals(QueryType, "5")){
            String  queryResult = DButil.queryTodayEpidemicInformation();
            if(queryResult!=null){
                //查询成功
                JsonResponse json = new JsonResponse(200);
                json.addAttribute("geojson",queryResult);
                json.addAttribute("info","信息查询成功");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","数据获取失败，请检查信息");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
        }
        else if(Objects.equals(QueryType, "6")){
            int adcode99 = Integer.parseInt(req.getParameter("adcode99"));
            String  queryResult = DButil.queryHistoryDataProCode(adcode99);
            if(queryResult!=null){
                //查询成功
                JsonResponse json = new JsonResponse(200);
                json.addAttribute("historyData",queryResult);
                json.addAttribute("info","信息查询成功");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
            else {
                JsonResponse json = new JsonResponse(202);
                json.addAttribute("error","数据获取失败，请检查信息");
                PrintWriter writer = resp.getWriter();
                writer.write(json.toString());
            }
        }
    }

    /**
     * 写这个函数主要是相应第一次的跨域请求，跨域时会首先发送option请求，查询是否允许跨域
     */
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
