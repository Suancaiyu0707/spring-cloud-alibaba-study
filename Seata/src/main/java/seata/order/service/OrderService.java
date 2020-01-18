package seata.order.service;

import org.springframework.core.annotation.Order;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/15
 * Time: 4:35 PM
 * Description: No Description
 */
public interface OrderService {
    /**
     * 创建订单
     */
    Order create( String userId, String commodityCode, int orderCount);
}
