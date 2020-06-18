package DAO;

import Utils.GeoJson;
import com.alibaba.fastjson.JSONObject;
import com.oracle.deploy.update.UpdateInfo;
import domain.User;

import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//增删改查等工具
public class DButil {

    static Connection con = null;
    static PreparedStatement stmt = null;
    static ResultSet resultSet =null;

    public static ResultSet getResultSet(String SQLString){

        try {
            con = jdbcUtiles.getConnection();
            stmt = con.prepareStatement(SQLString);
            resultSet = stmt.executeQuery();
            if(resultSet.next()){

                return resultSet;
            }
            else {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String SelectAll(String SelectStr){
        System.out.println("测试");
        //构建GeoJSON字段
        GeoJson geoJson = new GeoJson();
        resultSet =  getResultSet(SelectStr);
        while (true){
            try {
                if (!resultSet.next()) break;
                //属性数据
                String name = resultSet.getString("name");
                //几何字段
                String geom = resultSet.getString("geom");

//                JSONObject geojson = JSON.parseObject(geom);
//                //插入数据，整合为一个对象
//
                //构造属性数据
                HashMap<String,Object> properties = new HashMap<>();
                properties.put("name",name);
//
//                //构造单个Feature对象
//                HashMap<Object, Object> feature = new HashMap<>();
//                feature.put("type","Feature");
//                feature.put("geometry",geojson);
//                feature.put("properties",properties);
//                featureList.add(feature);
                geoJson.AddFeature(geom,properties);

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
        return geoJson.toString();
    }

    public void PG_Update(String UpdateStr){
        //更新数据库,日志文件进行记录
    }

    public void PG_Delete(String DeleteStr){
        //删除数据
    }

    /**
     *
     * @param name 查询字段，模糊查询
     */
    public static String  SelectByName(String name){
        PreparedStatement stmt=null;
        Connection con =null;
        GeoJson geoJson = new GeoJson();
        try {
            con = jdbcUtiles.getConnection();
            String SelectSQL = "SELECT  *,ST_AsGeoJson(geom) as geom2 from res2_4m where name like ?";
            stmt = con.prepareStatement(SelectSQL);
            stmt.setString(1,'%'+name+'%');
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                String com_name = resultSet.getString("name");
                //几何字段
                String geom = resultSet.getString("geom2");

                String city_pinyin = resultSet.getString("pinyin");
                String city_code = resultSet.getString("adcode99");
                String id = resultSet.getString("gid");
                System.out.println(com_name);
                System.out.println(id);
                //传入了数据
                HashMap<String,Object> properties = new HashMap<>();
                properties.put("name",com_name);
//                properties.put("pinyin",city_pinyin);
                properties.put("id", id);
//                properties.put("adcode99",city_code);

                geoJson.AddFeature(geom,properties);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jdbcUtiles.Close(con,stmt,null);
        if(!geoJson.isNull()){
            return geoJson.toString();
        }
        else {
            return null;
        }

    }

    public static String getInfoByGid(int id){
        PreparedStatement stmt=null;
        Connection con =null;
        GeoJson geoJson = new GeoJson();
        try {
            con = jdbcUtiles.getConnection();
            String SelectSQL = "SELECT  *,ST_AsGeoJson(geom) as geom2 from res2_4m where gid=?";
            stmt = con.prepareStatement(SelectSQL);
            stmt.setInt(1,id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                String com_name = resultSet.getString("name");
                //几何字段
                String geom = resultSet.getString("geom2");
                //拼音
                String city_pinyin = resultSet.getString("pinyin");
                //城市编码
                String city_code = resultSet.getString("adcode99");
                //详情
                String intro = resultSet.getString("intro");
                //图片二进制
                String image = resultSet.getString("image");
                //传入了数据
                String adclass = resultSet.getString("adclass");
                HashMap<String,Object> properties = new HashMap<>();
                properties.put("name",com_name);
                properties.put("pinyin",city_pinyin);
                properties.put("id",id);
                properties.put("adcode99",city_code);
                properties.put("adclass",adclass);

                //这两个可能不存在
                properties.put("intro",intro);
                properties.put("image",image);


                geoJson.AddFeature(geom,properties);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jdbcUtiles.Close(con,stmt,null);
        if(!geoJson.isNull()){
            return geoJson.toString();
        }
        else {
            return null;
        }

    }
    public static void UpdateNameByName(String old_name,String new_name){
        //根据地区的名字来更改地区名字
        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String updateSQL = "Update res2_4m set name = ? where name = ?";
            stmt = con.prepareStatement(updateSQL);;
            stmt.setString(1,new_name);
            stmt.setString(2,old_name);
            int status = stmt.executeUpdate();
            System.out.println(status);
            if (status>0){
                System.out.println("修改成功");
            }
            else {
                System.out.println("修改失败");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static boolean UpdateInfoByFid(String modify_type,String modify_value,int fid){
        //通过外键来改变 要素的信息
        //根据地区的名字来更改地区名字
        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String updateSQL = "Update res2_4m set "+modify_type+"= ? where gid = ?";
            stmt = con.prepareStatement(updateSQL);;
            stmt.setString(1,modify_value);
            stmt.setInt(2,fid);
            int status = stmt.executeUpdate();
            jdbcUtiles.Close(con,stmt,null);
            if (status>0){
                System.out.println("修改成功");
                return true;
            }
            else {
                System.out.println("修改失败");
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public static boolean UpdateInfoAllById(JSONObject json) {

        int id  ;
        String image;
        String adcode99;
        String pinyin;
        int adclass;
        Object[] coordinates ;
        //中文需要解码
        String name;
        String intro ;

        try {
             id = json.getInteger("id");
            image = json.getString("image");
            adcode99 = json.getString("adcode99");
            pinyin = json.getString("pinyin");
            adclass = json.getInteger("adclass");
             coordinates = json.getJSONArray("coordinates").toArray();
             System.out.println(coordinates[0]);
            //中文需要解码
            name = URLDecoder.decode(json.getString("name"),"utf-8");
            intro = URLDecoder.decode(json.getString("intro"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String updateSQL = "Update res2_4m set adcode99 = ? , adclass = ? ," +
                    "pinyin = ?, intro = ? , image = ?, name = ? ,geom = st_geomfromtext(?) where gid = ?";
            stmt = con.prepareStatement(updateSQL);;
            stmt.setInt(1,Integer.parseInt(adcode99));
            stmt.setInt(2,adclass);
            stmt.setString(3,pinyin);
            stmt.setString(4,intro);
            stmt.setString(5,image);
            stmt.setString(6,name);
            System.out.println("'point("+ coordinates[0] +" "+ coordinates[1] +")'");
            stmt.setString(7,"point("+ coordinates[0] +" "+ coordinates[1] +")");
            stmt.setInt(8,id);
            int status = stmt.executeUpdate();
            jdbcUtiles.Close(con,stmt,null);
            if (status>0){
                System.out.println("修改成功");
                return true;
            }
            else {
                System.out.println("修改失败");
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }

    public static boolean CreateNewCity(JSONObject json){

        String image;
        String adcode99;

        String pinyin;
        int adclass;
        Object[] coordinates ;
        //中文需要解码
        String name;
        String intro ;

        try {
            image = json.getString("image");
            adcode99 = json.getString("adcode99");

            pinyin = json.getString("pinyin");
            adclass = json.getInteger("adclass");
            coordinates = json.getJSONArray("coordinates").toArray();
            //中文需要解码
            name = URLDecoder.decode(json.getString("name"),"utf-8");
            intro = URLDecoder.decode(json.getString("intro"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String insertSQL = "INSERT INTO res2_4m (adcode99,adclass,pinyin,intro,image,name,geom) values (?,?,?,?," +
                    "?,?,st_geomfromtext(?))";

            stmt = con.prepareStatement(insertSQL);
            if(adcode99==null|| adcode99.equals("")){
                //默认城市
                stmt.setInt(1,3);
            }
            else {

                stmt.setInt(1,Integer.parseInt(adcode99));
            }
            stmt.setInt(2,adclass);
            stmt.setString(3,pinyin);
            stmt.setString(4,intro);
            stmt.setString(5,image);
            stmt.setString(6,name);
            stmt.setString(7,"point("+ coordinates[0] +" "+ coordinates[1] +")");
            System.out.println(stmt);
            int status = stmt.executeUpdate();
            jdbcUtiles.Close(con,stmt,null);
            if (status>0){
                System.out.println("添加成功");
                return true;
            }
            else {
                System.out.println("添加失败");
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }
    public static boolean DeleteCity(int fid){
        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String insertSQL = "DELETE FROM res2_4m where gid = ?";

            stmt = con.prepareStatement(insertSQL);
            stmt.setInt(1,fid);
            System.out.println(stmt);
            int status = stmt.executeUpdate();
            jdbcUtiles.Close(con,stmt,null);
            if (status>0){
                System.out.println("删除成功");
                return true;
            }
            else {
                System.out.println("删除失败");
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }

//    ------------------------------- 用户验证，注册 相关----------------------------------------------------------//

    public static User getUserInfoByUserName(String username,String password){
        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String querySQL = "select * from webgis_user  where username = ? and password = ? ";
            stmt = con.prepareStatement(querySQL);;
            stmt.setString(1,username);
            stmt.setString(2,password);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                System.out.println("存在用户");
                User user = new User();


                Date date = new Date();
                String updateSQL = "update webgis_user set LoginTime = ? where username = ?";
                stmt = con.prepareStatement(updateSQL);
                stmt.setTimestamp(1,new Timestamp(date.getTime()));
                stmt.setString(2,username);
                stmt.executeUpdate();

                user.setPassword(password);
                user.setUsername(username);
                user.setLoginTime(date);
                jdbcUtiles.Close(con,stmt,null);
                return user;
            }
            else {
                System.out.println("不存在");
                return null;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    public static User SelectUserByUserName(String username){
        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String querySQL = "select * from webgis_user  where username = ? ";
            stmt = con.prepareStatement(querySQL);;
            stmt.setString(1,username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                //需要对用户表进行修改，添加本次登录时间
                System.out.println("存在用户");

                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                jdbcUtiles.Close(con,stmt,null);
                return user;
            }
            else {
                System.out.println("不存在");
                jdbcUtiles.Close(con,stmt,null);
                return null;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /***
     * 这个函数不再对用户名重复进行判断
     * @param username
     * @param password
     * @return
     */
    public static User insertNewUser(String username,String password){

        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String updateSQL = "insert into  webgis_user (username,password,registertime) values (?,?,?)";
            stmt = con.prepareStatement(updateSQL);;
            stmt.setString(1,username);
            stmt.setString(2,password);
            Date date = new Date();
            stmt.setTimestamp(3,new Timestamp(date.getTime()));
            int status = stmt.executeUpdate();
            if (status!=0){
                System.out.println("注册新用户成功");
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setRegisterTime(date);
                return user;
            }
            else {
                System.out.println("注册新用户成功失败");
                return null;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


//-----------------------------------------   查询统计部分     ---------------------------------------------------//
    public static  String queryCityClass(){
        PreparedStatement stmt=null;
        Connection con =null;
        try {
            con = jdbcUtiles.getConnection();
            String querySQL = "SELECT array_to_json(\"array_agg\"(row_to_json(t)))\n" +
                    " FROM (SELECT adclass,count(name) as citycount FROM res2_4m GROUP BY adclass) as t;";
            stmt = con.prepareStatement(querySQL);;
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static String queryAllCity(){
        PreparedStatement stmt=null;
        Connection con =null;
        GeoJson geoJson = new GeoJson();
        try {
            con = jdbcUtiles.getConnection();
            String SelectSQL = "SELECT  *,ST_AsGeoJson(geom) as geom2 from res2_4m";
            stmt = con.prepareStatement(SelectSQL);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                String com_name = resultSet.getString("name");
                //几何字段
                String geom = resultSet.getString("geom2");

                String cityclass = resultSet.getString("adclass");

                //传入了数据
                HashMap<String,Object> properties = new HashMap<>();
                properties.put("name",com_name);
                properties.put("cityclass",cityclass);

                geoJson.AddFeature(geom,properties);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jdbcUtiles.Close(con,stmt,null);
        if(!geoJson.isNull()){
            return geoJson.toString();
        }
        else {
            return null;
        }
    }

    public static String queryProvince(){
        PreparedStatement stmt=null;
        Connection con =null;
        GeoJson geoJson = new GeoJson();
        try {
            con = jdbcUtiles.getConnection();
            String SelectSQL = "SELECT b.\"name\",a.weight,ST_AsGeoJson(b.geom) as geom FROM (\n" +
                    "(SELECT \"count\"(gid) as weight,adcode99 / 10000 as cityloc FROM res2_4m GROUP BY adcode99 / 10000) as a LEFT JOIN (SELECT name,adcode99 / 10000 as proloc,geom  from bou2_4p) as b on a.cityloc = b. proloc )";
            stmt = con.prepareStatement(SelectSQL);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                String proName = resultSet.getString("name");
                //几何字段
                String geom = resultSet.getString("geom");

                int weight = resultSet.getInt("weight");

                HashMap<String,Object> properties = new HashMap<>();
                properties.put("name",proName);
                properties.put("weight",weight);

                geoJson.AddFeature(geom,properties);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jdbcUtiles.Close(con,stmt,null);
        if(!geoJson.isNull()){
            return geoJson.toString();
        }
        else {
            return null;
        }
    }

    public static String RouteQuery(JSONObject json){

        Object[] startPoint ;
        Object[] endPoint ;

        try {
            startPoint = json.getJSONArray("startPoint").toArray();
            endPoint = json.getJSONArray("endPoint").toArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        PreparedStatement stmt=null;
        Connection con =null;

        try {
            System.out.println(startPoint[0]);
            System.out.println(endPoint[1]);
            con = jdbcUtiles.getConnection();
            String querySQL = "SELECT * FROM ST_AsGeoJson(pgr_fromatob('route',?,?,?,?));";
            stmt = con.prepareStatement(querySQL);
            stmt.setFloat(1, Float.parseFloat(startPoint[0].toString()));
            stmt.setFloat(2, Float.parseFloat(startPoint[1].toString()));
            stmt.setFloat(3, Float.parseFloat(endPoint[0].toString()));
            stmt.setFloat(4, Float.parseFloat(endPoint[1].toString()));
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                String route = resultSet.getString(1);
                if (route==null){
                    System.out.println("没有查询到结果");
                    return null;
                }else {
                    return route;
                }
            }
            else {
                jdbcUtiles.Close(con,stmt,null);
                System.out.println("修改失败");
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
    //-----------------------------疫情数据相关的查询-------------------------------------//
    public static String queryHistoryDataProCode(int adcode99){
        PreparedStatement stmt=null;
        Connection con =null;
        GeoJson geoJson = new GeoJson();
        try {
            con = jdbcUtiles.getConnection();
            String SelectSQL = "SELECT array_to_json(\"array_agg\"(row_to_json(t))) FROM (SELECT * FROM pro_virus " +
                    "WHERE adcode99 = ? ORDER BY date) as t";
            stmt = con.prepareStatement(SelectSQL);
            stmt.setInt(1,adcode99);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                return resultSet.getString(1);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jdbcUtiles.Close(con,stmt,null);
        if(!geoJson.isNull()){
            return geoJson.toString();
        }
        else {
            return null;
        }
    }

    public static String queryTodayEpidemicInformation(){
        PreparedStatement stmt=null;
        Connection con =null;
        GeoJson geoJson = new GeoJson();
        try {
            con = jdbcUtiles.getConnection();
            Date today = new Date();
            Date yestoday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
            int hours = today.getHours();
            String timeStr;
            if(hours>=12){
                timeStr = format.format(today);
            }else {
                timeStr = format.format(yestoday);
            }
            String SelectSQL = "SELECT * FROM((SELECT * FROM pro_virus where date >= '"+timeStr+"') AS a RIGHT JOIN " +
                    "(SELECT ST_AsGeoJson(geom) as geom,population,adcode99 FROM bou2_4p) AS b on a.adcode99 = b" +
                    ".adcode99)";
            stmt = con.prepareStatement(SelectSQL);

            System.out.println(stmt);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                String proName = resultSet.getString("province");
                //几何字段
                String geom = resultSet.getString("geom");

                int confirm = resultSet.getInt("confirm");
                int newConfirm = resultSet.getInt("newconfirm");
                int dead = resultSet.getInt("dead");
                int newDead = resultSet.getInt("newdead");
                int heal = resultSet.getInt("heal");
                int newHead = resultSet.getInt("newheal");
                int adcode99  = resultSet.getInt("adcode99");
                double population = resultSet.getDouble("population");

                HashMap<String,Object> properties = new HashMap<>();
                properties.put("name",proName);
                properties.put("confirm",confirm);
                properties.put("newConfirm",newConfirm);
                properties.put("dead",dead);
                properties.put("newDead",newDead);
                properties.put("heal",heal);
                properties.put("newHead",newHead);
                properties.put("adcode99",adcode99);
                properties.put("population",population);

                geoJson.AddFeature(geom,properties);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jdbcUtiles.Close(con,stmt,null);
        if(!geoJson.isNull()){
            return geoJson.toString();
        }
        else {
            return null;
        }
    }

    public static void main(String []args) throws UnsupportedEncodingException {
        //连接
//        String SelectStr = "SELECT name as name,ST_AsGeoJson(geom) as geom  from res2_4m ";
//        String json = SelectAll(SelectStr);
//        System.out.println(json);
//        UpdateNameByName("武汉","武汉市");
//        System.out.println(SelectByName("南"));
//          System.out.println(getInfoByGid(753));
//          User user = SelectUserByUserName("newuser");
//          System.out.println(user.toJsonStr());
//        insertNewUser("newuser","pass");
//        System.out.println(getUserInfoByUserName("newuser","pass").toString());
//        System.out.println(insertNewUser("5555","5555").toString());
//        Date data = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Timestamp timestamp = new Timestamp(data.getTime());
//        //timeStamp是可以直接保存到数据库内的数据类型
//        System.out.println(timestamp);
//        System.out.println(dateFormat.format(data));
//        System.out.println(URLDecoder.decode("%E6%AD%A6%E6%B1%89%E5%B8%82","utf-8"));
        String  str = "{\"image\":\"aa\",\"pinyin\":\"Wuhan\",\"adclass\":3," +
                "\"intro\":\"%E6%AD%A6%E6%B1%89%EF%BC%8C%E7%AE%80%E7%A7%B0" +
                "%E2%80%9C%E6%B1%89%E2%80%9D%EF%BC%8C%E5%88%AB%E7%A7%B0%E6%B1%9F%E5%9F%8E%EF%BC%8C%E6%98%AF%E6%B9%96" +
                "%E5%8C%97%E7%9C%81%E7%9C%81%E4%BC%9A%EF%BC%8C%E4%B8%AD%E9%83%A8%E5%85%AD%E7%9C%81%E5%94%AF%E4%B8%80" +
                "%E7%9A%84%E5%89%AF%E7%9C%81%E7%BA%A7%E5%B8%82%EF%BC%8C%E7%89%B9%E5%A4%A7%E5%9F%8E%E5%B8%82%EF%BC%8C" +
                "%E5%9B%BD%E5%8A%A1%E9%99%A2%E6%89%B9%E5%A4%8D%E7%A1%AE%E5%AE%9A%E7%9A%84%E4%B8%AD%E5%9B%BD%E4%B8%AD" +
                "%E9%83%A8%E5%9C%B0%E5%8C%BA%E7%9A%84%E4%B8%AD%E5%BF%83%E5%9F%8E%E5%B8%82%EF%BC%8C%E5%85%A8%E5%9B%BD" +
                "%E9%87%8D%E8%A6%81%E7%9A%84%E5%B7%A5%E4%B8%9A%E5%9F%BA%E5%9C%B0%E3%80%81%E7%A7%91%E6%95%99%E5%9F%BA" +
                "%E5%9C%B0%E5%92%8C%E7%BB%BC%E5%90%88%E4%BA%A4%E9%80%9A%E6%9E%A2%E7%BA%BD%20%5B1%5D%20%20%E3%80%82%E6" +
                "%88%AA%E8%87%B32019%E5%B9%B4%E6%9C%AB%EF%BC%8C%E5%85%A8%E5%B8%82%E4%B8%8B%E8%BE%9613%E4%B8%AA%E5%8C" +
                "%BA%EF%BC%8C%E6%80%BB%E9%9D%A2%E7%A7%AF8569" +
                ".15%E5%B9%B3%E6%96%B9%E5%8D%83%E7%B1%B3%EF%BC%8C%E5%BB%BA%E6%88%90%E5%8C%BA%E9%9D%A2%E7%A7%AF812" +
                ".39%E5%B9%B3%E6%96%B9%E5%8D%83%E7%B1%B3%EF%BC%8C%E5%B8%B8%E4%BD%8F%E4%BA%BA%E5%8F%A31121" +
                ".2%E4%B8%87%E4%BA%BA%EF%BC%8C%E5%9C%B0%E5%8C%BA%E7%94%9F%E4%BA%A7%E6%80%BB%E5%80%BC1" +
                ".62%E4%B8%87%E4%BA%BF%E5%85%83\",\"name\":\"%E6%AD%A6%E6%B1%89%E5%B8%82\",\"coordinates\":[114" +
                ".291938781738,30.5675144195557],\"adcode99\":\"420101\"}" ;
//        JSONObject jsonObject = JSONObject.parseObject(str);
////        UpdateInfoAllById(jsonObject);
//        CreateNewCity(jsonObject);
//        System.out.println(queryCityClass());
        String routeQuery = "{\"startPoint\":[116.423241,39.903866],\"endPoint\":[106.599084,29.565579]}";
//        JSONObject route = JSONObject.parseObject(routeQuery);
//        System.out.println(queryProvince());
//        System.out.println(queryTodayEpidemicInformation());
//        Date today = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
//        System.out.println(format.format(today));
//        System.out.println(queryHistoryDataProCode(230000));
        Date date = new Date();
        Date yestoday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        System.out.println(date.getHours());
        System.out.println(new SimpleDateFormat("YYYY-MM-dd").format(yestoday));

    }
}
