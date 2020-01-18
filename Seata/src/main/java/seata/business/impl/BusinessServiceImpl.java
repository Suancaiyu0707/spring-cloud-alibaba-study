package seata.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import seata.business.BusinessService;
import seata.order.service.OrderService;
import seata.storage.service.StorageService;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/15
 * Time: 4:45 PM
 * Description: No Description
 */
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private StorageService storageService;
    @Autowired
    private OrderService orderService;

    /**
     * 采购
     */
    public void purchase(String userId, String commodityCode, int orderCount) {

        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);
    }
}