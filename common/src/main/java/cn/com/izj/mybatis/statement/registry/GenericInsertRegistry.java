package cn.com.izj.mybatis.statement.registry;

import cn.com.izj.mybatis.annotation.Id;
import cn.com.izj.mybatis.keygen.KeyGeneratorType;
import cn.com.izj.mybatis.statement.GenericMappedStatementRegistry;
import cn.com.izj.mybatis.statement.RegistryArgs;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;

public abstract class GenericInsertRegistry extends GenericMappedStatementRegistry {

    public GenericInsertRegistry(RegistryArgs args) {
        super(args);
    }

    public KeyGenerator getKeyGenerator() {
        //默认使用SelectKey
        String keyStatementId = getStatementId() + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        if (configuration.hasKeyGenerator(keyStatementId)) {
            return configuration.getKeyGenerator(keyStatementId);
        }

        //不存在SelectKey 则使用ID注解规则
        Id idAnnotation = idField.getAnnotation(Id.class);
        if (idAnnotation.value() == KeyGeneratorType.AUTO) {
            return new Jdbc3KeyGenerator();
        } else {
            return new NoKeyGenerator();
        }
    }

    public Class<?> getResultType() {
        return int.class;
    }

    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.INSERT;
    }

}
