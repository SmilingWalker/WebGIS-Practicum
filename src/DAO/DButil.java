package DAO;

import Utils.GeoJson;
import domain.User;

import java.sql.*;
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
                String id = resultSet.getString("res2_4m_id");
                System.out.println(com_name);
                System.out.println(id);
                //传入了数据
                HashMap<String,Object> properties = new HashMap<>();
                properties.put("name",com_name);
                properties.put("pinyin",city_pinyin);
                properties.put("id", id);
                properties.put("adcode99",city_code);

                geoJson.AddFeature(geom,properties);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
            String SelectSQL = "SELECT  *,ST_AsGeoJson(geom) as geom2 from res2_4m where res2_4m_id=?";
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
                HashMap<String,Object> properties = new HashMap<>();
                properties.put("name",com_name);
                properties.put("pinyin",city_pinyin);
                properties.put("id",id);
                properties.put("adcode99",city_code);

                //这两个可能不存在
                properties.put("intro",intro);
                properties.put("image",image);


                geoJson.AddFeature(geom,properties);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
            String updateSQL = "Update res2_4m set "+modify_type+"= ? where res2_4m_id = ?";
            stmt = con.prepareStatement(updateSQL);;
            stmt.setString(1,modify_value);
            stmt.setInt(2,fid);
            int status = stmt.executeUpdate();
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

//    ------------------------------- 用户验证，注册 相关----------------------------------------------------------//

    public static User getUserInfoByUserName(String username,String password){
        PreparedStatement stmt=null;
        Connection con =null;

        try {
            con = jdbcUtiles.getConnection();
            String updateSQL = "select * from webgis_user  where username = ? and password = ? ";
            stmt = con.prepareStatement(updateSQL);;
            stmt.setString(1,username);
            stmt.setString(2,password);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                System.out.println("存在用户");
                User user = new User();
                user.setPassword(password);
                user.setUsername(username);
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
            String updateSQL = "select * from webgis_user  where username = ? ";
            stmt = con.prepareStatement(updateSQL);;
            stmt.setString(1,username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                System.out.println("存在用户");
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
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
            String updateSQL = "insert into  webgis_user (username,password) values (?,?)";
            stmt = con.prepareStatement(updateSQL);;
            stmt.setString(1,username);
            stmt.setString(2,password);
            int status = stmt.executeUpdate();
            if (status!=0){
                System.out.println("注册新用户成功");
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
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

    public static void main(String []args){
        //连接
//        String SelectStr = "SELECT name as name,ST_AsGeoJson(geom) as geom  from res2_4m ";
//        String json = SelectAll(SelectStr);
//        System.out.println(json);
//        UpdateNameByName("武汉","武汉市");
//        System.out.println(SelectByName("南"));
          System.out.println(getInfoByGid(753));
          User user = SelectUserByUserName("newuser");
          System.out.println(user.toJsonStr());
//        insertNewUser("newuser","pass");
        System.out.println(getUserInfoByUserName("newuser","pass").toString());

    }
}
