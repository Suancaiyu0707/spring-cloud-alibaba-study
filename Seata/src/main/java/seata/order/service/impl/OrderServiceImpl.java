package seata.order.service.impl;

import org.springframework.core.annotation.Order;
import seata.account.service.AccountService;
import seata.dao.OrderDao;
import seata.order.service.OrderService;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/15
 * Time: 4:42 PM
 * Description: No Description
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDAO;

    private AccountService accountService;
    @Override
    public Order create(String userId, String commodityCode, int orderCount) {

        int orderMoney = 10;

        accountService.debit(userId, orderMoney);

        Order order = new Order();
        order.userId = userId;
        order.commodityCode = commodityCode;
        order.count = orderCount;
        order.money = orderMoney;

        // INSERT INTO orders ...
        return orderDAO.insert(order);
    }
}
