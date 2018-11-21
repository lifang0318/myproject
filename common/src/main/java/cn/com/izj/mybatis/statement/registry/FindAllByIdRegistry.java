package cn.com.izj.mybatis.statement.registry;

import cn.com.izj.mybatis.statement.GenericMappedStatementRegistry;
import cn.com.izj.mybatis.statement.RegistryArgs;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据ID列表查询记录
 * <pre>
 * select [all fields] from [tableName]
 * where [id] in
 * &lt;foreach collection="list" item="item" index="index" open="(" close=")" seperate="," &gt;
 * 	#{item}
 * &lt;/foreach&gt;
 * </pre>
 */
public class FindAllByIdRegistry extends GenericMappedStatementRegistry {

    public FindAllByIdRegistry(RegistryArgs args) {
        super(args);
    }

    public String getStatementId() {
        return namespace + ".findAllById";
    }

    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.SELECT;
    }

    public SqlSource getSqlSource() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ").append(getColumnNameByField(idField)).append(" ").append(idField.getName());
        for (Field field : columnFields) {
            sql.append(" , ").append(getColumnNameByField(field)).append(" ").append(field.getName());
        }
        sql.append(" from ").append(tableName);

        List<SqlNode> contents = new ArrayList<>();
        contents.add(new TextSqlNode(sql.toString()));

        List<SqlNode> whereStatement = new ArrayList<>();

        whereStatement.add(new TextSqlNode(" where " + getColumnNameByField(idField) + " in "));
        whereStatement.add(new ForEachSqlNode(configuration, new TextSqlNode("#{item}"), "list", "index", "item", "(", ")", ","));

        contents.add(new IfSqlNode(new MixedSqlNode(whereStatement), "list != null "));

        return new DynamicSqlSource(configuration, new MixedSqlNode(contents));
    }

    public KeyGenerator getKeyGenerator() {
        return new NoKeyGenerator();
    }

    public Class<?> getResultType() {
        return entityClass;
    }
}
