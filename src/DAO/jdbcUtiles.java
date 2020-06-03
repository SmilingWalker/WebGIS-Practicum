package DAO;

import java.sql.*;
import java.util.ArrayList;

public class jdbcUtiles {

    //JDBC
    //相关类
    //建立连接返回connect

    // 读取记录resultset
    // 查询，更新，sql
    // 资源释放，
    // 静态转动态，
    // 读取数据库中的geom，转换成geojson

    //1.建立连接返回connect
    public static String url ="jdbc:postgresql://127.0.0.1:5432/pgdemo1"; ;
    private static String username ="postgres" ;
    private static String password = "1998+cq+*";

    public static ArrayList<Connection> arrayList = new ArrayList<Connection>();

    static {
        for (int j =0;j<10;j++){
            Connection con = CreateConnection();
            arrayList.add(con);
        }
    }
    private static Connection CreateConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(){
        if(!arrayList.isEmpty()){
            Connection con = arrayList.get(0);
            arrayList.remove(con);
            return con;
        }
        else {
            Connection con = CreateConnection();
            return con;
        }
    }
    public static void Close( PreparedStatement stmt, ResultSet resultSet,Connection con)
    {
        Closestmt(stmt);
        CloseResult(resultSet);
        CloseCon(con);
    }
    public static void Close(Connection con,PreparedStatement stmt1,PreparedStatement stmt2){

        Closestmt(stmt1);
        Closestmt(stmt2);
        CloseCon(con);
    }
    public static void Closestmt(PreparedStatement stmt){
        try {
            if(stmt!=null)
                stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void CloseCon(Connection con)
    {
//        try {
//            if(con!=null)
//                con.close();
//            }
//        catch (SQLException e) {
//            e.printStackTrace();
//            }
        arrayList.add(con);

    }
    public static void CloseResult(ResultSet resultSet)
    {
        try
        {
            if(resultSet!=null)
                resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args){

    }
}


