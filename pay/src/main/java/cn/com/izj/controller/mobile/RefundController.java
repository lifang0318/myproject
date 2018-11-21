package cn.com.izj.controller.mobile;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lifang
 * @description:退款-controller
 * @date: Created in 2018/8/24 23:32
 * @version:
 */
@RequestMapping("/mobile/refund")
@RestController
public class RefundController extends BasePayController {

    @Autowired
    private RefundService refundService;

    /**
     * 押金退还
     *
     * @param orderNum
     * @return
     * @throws Exception
     */
    @PostMapping("/{orderNum}")
    public ApiResult depositRefund(@PathVariable("orderNum") String orderNum) throws Exception {
        return this.refundService.depositRefund(orderNum);
    }
}
