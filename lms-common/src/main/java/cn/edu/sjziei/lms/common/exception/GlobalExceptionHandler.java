package cn.edu.sjziei.lms.common.exception;

import cn.edu.sjziei.lms.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * 作用：
 * - 统一捕获并处理项目中的所有异常
 * - 将异常转换为统一的JSON响应格式
 * - 避免每个Controller单独处理异常
 *
 * 处理范围：
 * - BusinessException：业务异常，返回给用户明确提示
 * - Exception：所有其他异常，统一返回500错误
 *
 * 响应格式：
 * {
 *     "code": 400,
 *     "message": "用户名不能为空",
 *     "data": null
 * }
 *
 * 使用位置：
 * - 扫描cn.edu.sjziei.lms包下的所有Controller
 * - @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 *
 * 异常处理优先级：
 * - 如果抛出的是BusinessException，会被handleBusinessException处理
 * - 其他所有异常会被handleException处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * 当Controller或Service抛出BusinessException时调用
     * 将业务异常的信息提取出来，封装成Result返回
     *
     * @param e 业务异常实例
     * @return 统一格式的响应结果
     *
     * @处理流程
     * 1. 业务代码抛出 BusinessException("用户名已存在")
     * 2. Spring MVC捕获异常
     * 3. 进入此方法，e.getCode()=400, e.getMessage()="用户名已存在"
     * 4. 返回 Result{code=400, message="用户名已存在", data=null}
     *
     * @响应示例
     * {
     *     "code": 400,
     *     "message": "用户名已存在",
     *     "data": null
     * }
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        // 打印日志便于排查（生产环境可改为logger.warn）
        System.out.println("业务异常：" + e.getMessage());

        // 返回错误结果，使用异常中的code和message
        // e.getCode()默认是400，可以在抛出时指定其他值
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理所有其他异常
     *
     * 当抛出非BusinessException的异常时调用
     * 如：空指针异常、数据库异常、IO异常等
     *
     * @param e 异常实例
     * @return 统一格式的错误响应
     *
     * @处理流程
     * 1. 代码抛出 NullPointerException 或其他系统异常
     * 2. Spring MVC捕获异常
     * 3. 进入此方法
     * 4. 打印异常堆栈便于排查问题
     * 5. 返回 Result{code=500, message="服务器内部错误", data=null}
     *
     * @重要
     * 生产环境应该使用真实的日志框架（如logback）记录日志
     * 不应该直接e.printStackTrace()
     *
     * @响应示例
     * {
     *     "code": 500,
     *     "message": "服务器内部错误",
     *     "data": null
     * }
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 打印异常堆栈信息（生产环境应使用logger.error）
        e.printStackTrace();

        // 返回通用的服务器内部错误
        // 不返回具体的异常信息，避免泄露系统内部细节
        return Result.error(500, "服务器内部错误");
    }

    // ==================== 可扩展的其他异常处理方法 ====================

    /**
     * 处理参数校验异常（扩展用）
     *
     * 当使用@Valid注解进行参数校验失败时抛出
     * 需要配合 BindingResult 使用
     *
     * @示例使用
     * public Result<?> createUser(@Valid @RequestBody UserDTO dto, BindingResult result) {
     *     if (result.hasErrors()) {
     *         throw new BusinessException(400, result.getFieldError().getDefaultMessage());
     *     }
     * }
     *
     * @param e 异常实例
     * @return 错误响应
     */
    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public Result<?> handleValidException(MethodArgumentNotValidException e) {
    //     String message = e.getBindingResult().getFieldError().getDefaultMessage();
    //     return Result.error(400, message);
    // }

    /**
     * 处理找不到资源的异常（扩展用）
     *
     * 当访问不存在的资源时抛出
     *
     * @示例使用
     * public User getUser(Long id) {
     *     User user = userMapper.findById(id);
     *     if (user == null) {
     *         throw new BusinessException(404, "用户不存在");
     *     }
     *     return user;
     * }
     *
     * @param e 异常实例
     * @return 错误响应
     */
    // @ExceptionHandler(NoHandlerFoundException.class)
    // public Result<?> handleNotFoundException(NoHandlerFoundException e) {
    //     return Result.error(404, "接口不存在");
    // }
}
