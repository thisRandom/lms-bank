package cn.edu.sjziei.lms.common.result;

import lombok.Data;

/**
 * 统一响应结果封装类
 *
 * @param <T> data数据类型
 *
 * 使用方式：
 * - 返回成功数据：Result.success(data)
 * - 返回成功消息：Result.success("操作成功", data)
 * - 返回错误：Result.error(400, "参数错误")
 * - 返回错误：Result.error("服务器内部错误")
 *
 * 响应格式：
 * {
 *     "code": 200,
 *     "message": "success",
 *     "data": null
 * }
 */
@Data
public class Result<T> {

    /**
     * 状态码
     *
     * 200     - 成功
     * 400     - 请求参数错误
     * 401     - 未登录或Token过期
     * 403     - 无权限访问
     * 404     - 资源不存在
     * 500     - 服务器内部错误
     */
    private int code;

    /**
     * 响应消息
     *
     * 成功时默认为 "success"
     * 失败时为具体的错误描述
     */
    private String message;

    /**
     * 响应数据
     *
     * 可以是任意类型：
     * - 单个对象（如 User、Vehicle）
     * - 集合（如 List<User>）
     * - 分页结果（如 PageResult<User>）
     * - 简单类型（如 String、Long）
     * - null（无数据返回时）
     */
    private T data;

    /**
     * 无参构造方法
     *
     * 默认值：
     * - code: 200
     * - message: "success"
     * - data: null
     */
    public Result() {
    }

    // ==================== 成功响应方法 ====================

    /**
     * 返回成功结果（无数据）
     *
     * 使用场景：
     * - 删除操作成功
     * - 更新操作成功
     * - 不需要返回数据的操作
     *
     * @return Result对象，code=200, message="success", data=null
     *
     * @示例
     * return Result.success();
     *
     * @响应结果
     * {
     *     "code": 200,
     *     "message": "success",
     *     "data": null
     * }
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 返回成功结果（带数据）
     *
     * @param data 要返回的数据，可以为null
     * @param <T> 数据类型
     * @return Result对象，code=200, message="success"
     *
     * @示例
     * User user = userService.findById(1L);
     * return Result.success(user);
     *
     * @响应结果
     * {
     *     "code": 200,
     *     "message": "success",
     *     "data": { "id": 1, "username": "admin", ... }
     * }
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    /**
     * 返回成功结果（带自定义消息和数据）
     *
     * @param message 自定义成功消息
     * @param data 要返回的数据
     * @param <T> 数据类型
     * @return Result对象
     *
     * @示例
     * return Result.success("用户创建成功", newUserId);
     *
     * @响应结果
     * {
     *     "code": 200,
     *     "message": "用户创建成功",
     *     "data": 1001
     * }
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    // ==================== 错误响应方法 ====================

    /**
     * 返回错误结果（带状态码和消息）
     *
     * @param code HTTP状态码（如400、401、403、404、500）
     * @param message 错误消息
     * @param <T> 数据类型（固定为?，因为错误响应通常无数据）
     * @return Result对象
     *
     * @示例
     * return Result.error(400, "用户名不能为空");
     *
     * @响应结果
     * {
     *     "code": 400,
     *     "message": "用户名不能为空",
     *     "data": null
     * }
     */
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 返回错误结果（默认500状态码）
     *
     * 使用场景：
     * - 服务器内部错误
     * - 未明确分类的错误
     *
     * @param message 错误消息
     * @param <T> 数据类型
     * @return Result对象，code=500
     *
     * @示例
     * return Result.error("系统繁忙，请稍后重试");
     *
     * @响应结果
     * {
     *     "code": 500,
     *     "message": "系统繁忙，请稍后重试",
     *     "data": null
     * }
     */
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }

    // ==================== 便捷判断方法 ====================

    /**
     * 判断响应是否成功
     *
     * @return true表示code=200，false表示其他
     *
     * @示例
     * if (result.isSuccess()) {
     *     // 处理成功逻辑
     * }
     */
    public boolean isSuccess() {
        return this.code == 200;
    }

    /**
     * 判断响应是否有数据
     *
     * @return true表示data不为null，false表示data为null
     */
    public boolean hasData() {
        return this.data != null;
    }
}
