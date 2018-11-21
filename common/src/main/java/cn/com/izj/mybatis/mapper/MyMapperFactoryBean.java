package cn.com.izj.mybatis.mapper;

import cn.com.izj.mybatis.statement.MappedStatementRegistryComposite;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.springframework.util.Assert.notNull;

/**
 * Created by GouBo on 2018/6/6 22:46.
 *
 * MapperFactoryBean 扩展类,重新定义mapper接口的代理类
 *
 * 1. 注册接口类
 * 2. 注解接口代理类对应的SQL,即BaseMapper中的方法
 */
public class MyMapperFactoryBean<T> extends MapperFactoryBean<T> {

    public MyMapperFactoryBean() {
        super();
    }

    public MyMapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    protected void checkDaoConfig() {
        notNull(getMapperInterface(), "Property 'mapperInterface' is required");

        Configuration configuration = getSqlSession().getConfiguration();
        if (!configuration.hasMapper(this.getMapperInterface())) {
            try {
                configuration.addMapper(this.getMapperInterface());
            } catch (Exception e) {
                logger.error("Error while adding the mapper '" + this.getMapperInterface() + "' to configuration.", e);
                throw new IllegalArgumentException(e);
            } finally {
                ErrorContext.instance().reset();
            }
        }

        String interfaceName = getMapperInterface().getName();
        if (!configuration.hasStatement(interfaceName)) {
            new MappedStatementRegistryComposite(configuration, interfaceName, getEntityClass())
                    .registerMappedStatement();
        }
    }

    private Class<?> getEntityClass() {
        Type[] types = getMapperInterface().getGenericInterfaces();
        ParameterizedType type = (ParameterizedType) types[0];
        Type[] actualTypes = type.getActualTypeArguments();
        return (Class<?>) actualTypes[0];
    }
}
