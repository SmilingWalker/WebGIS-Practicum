package Service;


//和用户相关的一些服务项目，包括注册，登录，等等

import DAO.DButil;
import domain.User;

public class UserService {
    /**
     * 判断用户是否已经存在
     * @param username 用户名
     * @return 存在与否
     */
    public boolean isExist(String username){
        return (DButil.SelectUserByUserName(username)!=null);
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 返回为空表示用户名或者密码错误
     */
    public User login(String username,String password){

        return DButil.getUserInfoByUserName(username,password);
    }

    /**
     *
     * @param username
     * @param password
     * @return 返回为空表示用户名已经存在
     */
    public User register(String username,String password){
        if(isExist(username)){
            return null;
        }
        else {
            return DButil.insertNewUser(username,password);
        }
    }
}
