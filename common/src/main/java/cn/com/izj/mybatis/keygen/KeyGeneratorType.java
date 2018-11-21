package cn.com.izj.mybatis.keygen;

/**
 * Created by GouBo on 2018/6/6 22:46.
 *
 * 主键生成策略
 * Mybatis自带的 SelectKeyGenerator可以使用SelectKey注解自动完成,不在此注解进行配置
 */
public enum KeyGeneratorType {

    /**
     * 数据库自动生成,此用类是使用 Jdbc3KeyGenerator
     */
    AUTO,

    /**
     * 无操作
     */
    NONE
}