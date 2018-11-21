package cn.com.izj.mybatis.statement.registry;

import cn.com.izj.mybatis.statement.GenericMappedStatementRegistry;
import cn.com.izj.mybatis.statement.RegistryArgs;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据ID更新
 *
 * <pre>
 * update [tableName]
 * &lt;set&gt;
 * 		&lt;if test=" someField1 != null"&gt; some_field1 = #{someField1} ,&lt;/if&gt;
 * 		&lt;if test=" someField2 != null"&gt; some_field2 = #{someField2} ,&lt;/if&gt;
 * &lt;/set&gt;
 * where [id] = #{id}
 * </pre>
 */
public class UpdateByIdRegistry extends GenericMappedStatementRegistry {

    public UpdateByIdRegistry(RegistryArgs args) {
        super(args);
    }

    public String getStatementId() {
        return namespace + ".updateById";
    }

    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.INSERT;
    }

    public SqlSource getSqlSource() {

        List<SqlNode> contents = new ArrayList<>();

        contents.add(new TextSqlNode(" update " + tableName));
        contents.add(getUpdateSetStatement());
        contents.add(new TextSqlNode(" where " + getColumnNameByField(idField) + " = #{" + idField.getName() + "}"));

        return new DynamicSqlSource(configuration, new MixedSqlNode(contents));
    }

    public KeyGenerator getKeyGenerator() {
        return new NoKeyGenerator();
    }

    public Class<?> getResultType() {
        return int.class;
    }
}
