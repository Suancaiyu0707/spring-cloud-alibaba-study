package seata.business;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/15
 * Time: 4:45 PM
 * Description: No Description
 */
public interface BusinessService {
    void purchase(String userId, String commodityCode, int orderCount);
}
