package cn.edu.sjziei.lms.common.result;
import lombok.Data;

/**
 * 后端统一返回结果
 */
@Data
public class Result {

    private Integer code; //编码
    private String msg; //错误信息
    private Object data; //数据

    public static Result success(Integer code) {
        Result result = new Result();
        result.code = code;
        result.msg = "success";
        return result;
    }

    public static Result success(Integer code,Object object) {
        Result result = new Result();
        result.data = object;
        result.code = code;
        result.msg = "success";
        return result;
    }

    public static Result error(Integer code,String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 500;
        return result;
    }

}