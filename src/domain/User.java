package domain;

import com.alibaba.fastjson.JSONObject;

import javax.xml.crypto.Data;
import java.util.Date;

public class User {

    //FIXME 还应该进行大致的判断

    private String username;
    private String password;
    private int age;
    private int sex;
    private Date LoginTime;
    private Date RegisterTime;

    //XXX:数据库内保存的时间数据为一个固定格式的字符串

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(Date loginTime) {
        LoginTime = loginTime;
    }

    public Date getRegisterTime() {
        return RegisterTime;
    }

    public void setRegisterTime(Date registerTime) {
        RegisterTime = registerTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", LoginTime=" + LoginTime +
                ", RegisterTime=" + RegisterTime +
                '}';
    }

    public String toJsonStr(){
        return JSONObject.toJSONString(this);
    }
}
