package cn.edu.sjziei.lms.common.exception;

/**
 * 业务异常类
 *
 * 用于在业务逻辑中抛出明确的业务错误
 *
 * 使用场景：
 * - 用户名已存在：throw new BusinessException("用户名已存在");
 * - 余额不足：throw new BusinessException("账户余额不足");
 * - 订单无法取消：throw new BusinessException(400, "订单已发货，无法取消");
 *
 * 与普通Exception的区别：
 * - BusinessException：业务逻辑错误，预期之内的错误，需要给用户明确提示
 * - 普通Exception：系统错误，预期之外的错误，如数据库连接失败
 *
 * 在GlobalExceptionHandler中的处理：
 * - BusinessException会被捕获并返回对应的code和message
 * - 未捕获的普通Exception会统一返回500服务器内部错误
 *
 * 错误码约定：
 * - 400：请求参数错误、业务校验失败
 * - 401：未登录或Token过期
 * - 403：无权限访问
 * - 404：资源不存在
 * - 500：服务器内部错误
 */
public class BusinessException extends RuntimeException {

    /**
     * HTTP状态码
     *
     * 用于标识错误的类型，便于前端或网关进行差异化处理
     * 默认为400（请求参数错误）
     *
     * 常用错误码：
     * - 400：业务校验失败、参数错误
     * - 401：未认证
     * - 403：无权限
     * - 404：资源不存在
     * - 500：服务器内部错误
     */
    private int code = 400;

    /**
     * 无参构造方法
     *
     * 创建一个业务异常，错误码默认为400，消息为空
     */
    public BusinessException() {
        super();
    }

    /**
     * 带错误消息的构造方法
     *
     * @param message 错误消息，用于展示给用户
     *
     * @示例
     * throw new BusinessException("用户名不能为空");
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 带错误码和错误消息的构造方法
     *
     * @param code HTTP状态码
     * @param message 错误消息
     *
     * @示例
     * throw new BusinessException(401, "登录已过期，请重新登录");
     *
     * @示例
     * throw new BusinessException(404, "用户不存在");
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 获取错误码
     *
     * @return HTTP状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置错误码
     *
     * @param code HTTP状态码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取错误消息（快捷方法）
     *
     * 等同于 getMessage()
     *
     * @return 错误消息字符串
     */
    public String getErrorMessage() {
        return getMessage();
    }

    /**
     * 创建并返回一个BusinessException（静态工厂方法）
     *
     * @param message 错误消息
     * @return BusinessException实例
     *
     * @示例
     * return BusinessException.of("参数错误");
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

    /**
     * 创建并返回一个带错误码的BusinessException（静态工厂方法）
     *
     * @param code 错误码
     * @param message 错误消息
     * @return BusinessException实例
     *
     * @示例
     * return BusinessException.of(404, "资源不存在");
     */
    public static BusinessException of(int code, String message) {
        return new BusinessException(code, message);
    }
}
