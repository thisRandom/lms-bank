package cn.edu.sjziei.lms.common.result;

import lombok.Data;
import java.util.List;

/**
 * 分页查询结果封装类
 *
 * @param <T> 列表中每条记录的数据类型
 *
 * 使用方式：
 * - 封装分页查询结果：return Result.success(pageResult);
 * - 前端直接使用 pageResult 的各个属性进行分页渲染
 *
 * 响应格式：
 * {
 *     "code": 200,
 *     "message": "success",
 *     "data": {
 *         "total": 100,
 *         "pages": 10,
 *         "current": 1,
 *         "size": 10,
 *         "records": [...]
 *     }
 * }
 *
 * 注意事项：
 * - total: 总记录数，用于计算总页数和显示"共X条"
 * - pages: 总页数，total / size（向上取整）
 * - current: 当前页码，从1开始
 * - size: 每页显示的记录数
 * - records: 当前页的数据列表，可能为空数组[]
 */
@Data
public class PageResult<T> {

    /**
     * 总记录数
     *
     * 表示符合条件的总数据条数，不是当前页的数据条数
     *
     * 示例：查询第1页，每页10条，总共85条数据
     * total = 85
     */
    private long total;

    /**
     * 总页数
     *
     * 根据total和size计算得出
     * 计算公式：pages = (total + size - 1) / size
     *
     * 示例：total=85, size=10 → pages=9
     * 示例：total=100, size=10 → pages=10
     */
    private int pages;

    /**
     * 当前页码
     *
     * 从1开始，不是从0开始
     * 前端传入的page参数对应此字段
     *
     * 示例：page=1 表示第一页
     */
    private int current;

    /**
     * 每页显示的记录数
     *
     * 前端传入的size参数对应此字段
     * 默认值通常为10
     *
     * 示例：size=10 表示每页显示10条
     */
    private int size;

    /**
     * 当前页的数据列表
     *
     * 包含当前页的所有记录
     * 类型为泛型List，可以是User、Vehicle、Order等任意类型
     * 如果当前页没有数据，则为空数组[]
     */
    private List<T> records;

    /**
     * 无参构造方法
     *
     * 使用MyBatis或Jackson反序列化时需要
     */
    public PageResult() {
    }

    /**
     * 全参构造方法
     *
     * @param total 总记录数
     * @param pages 总页数
     * @param current 当前页码
     * @param size 每页记录数
     * @param records 数据列表
     */
    public PageResult(long total, int pages, int current, int size, List<T> records) {
        this.total = total;
        this.pages = pages;
        this.current = current;
        this.size = size;
        this.records = records;
    }

    // ==================== 便捷静态方法 ====================

    /**
     * 创建分页结果（推荐使用）
     *
     * @param total 总记录数
     * @param records 当前页数据列表
     * @param current 当前页码
     * @param size 每页记录数
     * @param <T> 数据类型
     * @return PageResult对象
     *
     * @示例
     * List<User> users = userMapper.selectPage(page, size);
     * long total = userMapper.selectCount();
     * return PageResult.of(total, users, page, size);
     */
    public static <T> PageResult<T> of(long total, List<T> records, int current, int size) {
        // 计算总页数（向上取整）
        int pages = (int) Math.ceil((double) total / size);

        PageResult<T> result = new PageResult<>();
        result.setTotal(total);
        result.setPages(pages);
        result.setCurrent(current);
        result.setSize(size);
        result.setRecords(records);
        return result;
    }

    // ==================== 便捷判断方法 ====================

    /**
     * 判断是否有下一页
     *
     * @return true表示还有下一页，false表示当前是最后一页
     *
     * @示例
     * if (pageResult.hasNext()) {
     *     // 显示"下一页"按钮
     * }
     */
    public boolean hasNext() {
        return this.current < this.pages;
    }

    /**
     * 判断是否有上一页
     *
     * @return true表示还有上一页，false表示当前是第一页
     *
     * @示例
     * if (pageResult.hasPrevious()) {
     *     // 显示"上一页"按钮
     * }
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 判断是否是第一页
     *
     * @return true表示当前是第一页，false表示不是第一页
     */
    public boolean isFirst() {
        return this.current == 1;
    }

    /**
     * 判断是否是最后一页
     *
     * @return true表示当前是最后一页，false表示不是最后一页
     */
    public boolean isLast() {
        return this.current >= this.pages;
    }

    /**
     * 判断是否为空（无任何数据）
     *
     * @return true表示没有任何数据，false表示有数据
     */
    public boolean isEmpty() {
        return this.records == null || this.records.isEmpty();
    }

    // ==================== 其他便捷方法 ====================

    /**
     * 获取当前页的记录数
     *
     * @return 当前页的实际记录数，不是每页的记录数
     */
    public int getCurrentSize() {
        return this.records != null ? this.records.size() : 0;
    }

    /**
     * 获取下一页页码
     *
     * @return 下一页的页码，如果已经是最后一页则返回当前页码
     */
    public int getNextPage() {
        return hasNext() ? this.current + 1 : this.current;
    }

    /**
     * 获取上一页页码
     *
     * @return 上一页的页码，如果已经是第一页则返回1
     */
    public int getPreviousPage() {
        return hasPrevious() ? this.current - 1 : 1;
    }

    /**
     * 获取开始行号（用于显示"第X到Y条"）
     *
     * @return 当前页的第一条记录在整个结果集中的位置
     *
     * @示例
     * // total=85, current=2, size=10
     * // startRow = (2-1)*10 + 1 = 11
     * // endRow = min(11+10-1, 85) = 20
     * // 显示"第11到20条，共85条"
     */
    public int getStartRow() {
        return (int) ((this.current - 1) * this.size + 1);
    }

    /**
     * 获取结束行号
     *
     * @return 当前页的最后一条记录在整个结果集中的位置
     */
    public int getEndRow() {
        return (int) Math.min(this.current * this.size, this.total);
    }
}
