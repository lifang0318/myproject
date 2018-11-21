package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/7/6 20:15
 */
public class AuditInfo {
    private Long auditorId;//审核人id
    private String tradeNumber;//提现订单号
    private String cardNumber;//银行卡号码
    private Integer auditState;//审核状态 1通过 2不通过

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }
}
