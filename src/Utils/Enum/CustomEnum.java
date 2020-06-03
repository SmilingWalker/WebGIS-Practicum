package Utils.Enum;

public enum CustomEnum {

    //简单的一些枚举类型

    //服务器请求返回的错误详情

    USER_EXPIRED(10001,"用户登录信息已过期");

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public final Integer code;
    public final String msg;

    CustomEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
