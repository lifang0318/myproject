package cn.com.izj.form;

/**
 * @author GouBo
 * @date 2018/8/19
 */
public class BatchSmsForm {
    private String[] mobiles;
    private String content;

    public String[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
