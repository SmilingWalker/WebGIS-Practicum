package Utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PayLoadValue {
    HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    JSONObject result;
    public PayLoadValue(HttpServletRequest request) throws IOException {
        this.request =request;
        if("POST".equalsIgnoreCase(request.getMethod())){
            String result = IOUtils.toString(request.getInputStream());
            //FIXME 这里是要对数据进行重新编码的，
            String encode = new String(result.getBytes(), StandardCharsets.UTF_8);
            this.result = JSONObject.parseObject(encode);
//            BufferedReader reader = req.getReader();
//            String s = reader.readLine();
//            System.out.println(s);
            //可以获得所有的数据，但是不是，键值对，而是字符串数据
        }
    }
    public String getString(String key){
        return result.getString(key);
    }
    public Object getObject(String key){
        return this.result.get(key);
    }
}
