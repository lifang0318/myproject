package cn.com.izj.mybatis.statement.registry;

import cn.com.izj.mybatis.statement.GenericMappedStatementRegistry;
import cn.com.izj.mybatis.statement.RegistryArgs;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据记录ID批量删除
 * <pre>
 * delete from [tableName]
 * where [id] in
 * &lt;foreach collection="list" item="item" index="index" open="(" close=")" seperate=","&gt;
 * 	#{item}
 * &lt;/foreach&gt;
 * </pre>
 */
public class DeleteAllByIdRegistry extends GenericMappedStatementRegistry {

    public DeleteAllByIdRegistry(RegistryArgs args) {
        super(args);
    }

    public String getStatementId() {
        return namespace + ".deleteAllById";
    }

    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.DELETE;
    }

    public SqlSource getSqlSource() {

        List<SqlNode> contents = new ArrayList<>();
        contents.add(new TextSqlNode(" delete from " + tableName));

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
        return Integer.class;
    }
}
