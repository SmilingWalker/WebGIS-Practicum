package Utils;

import com.alibaba.fastjson.JSONObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class JsonResponse extends JSONObject {

    //封装返回字符串
    //包含 status参数，表示请求情况
    //200 表示请求成功 202 表示请求失败（之后可以设置）
    //data 表示返回内容
    public int status;
    public JSONObject data;

    /**
     *
     */
    public JsonResponse(int Status){
        this.status = Status;
        data = new JSONObject();

    }

    public void addAttribute(String name,String Attribute){
        data.put(name,Attribute);
    }

    @Override
    public String toString() {
        this.put("status",status);
        this.put("data",data);
        return super.toString();
    }
}
