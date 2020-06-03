package domain;

import com.alibaba.fastjson.JSONObject;

public class User {

    //FIXME 还应该进行大致的判断

    private String username;
    private String password;
    private int age;
    private int sex;

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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    public String toJsonStr(){
        return JSONObject.toJSONString(this);
    }
}
