package cn.com.izj.dto;

/**
 * @author GouBo
 * @date 2018/8/19
 */
public class UserIsExist {
    private boolean exist;

    public UserIsExist(boolean exist) {
        this.exist = exist;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
