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
 * 根据条件删除
 *
 * <pre>
 * delete from [tableName]
 * &lt;where&gt;
 * 	&lt;if test="a != null"&gt; and a = #{a} &lt;/if&gt;
 * 	&lt;if test="b != null"&gt; and b = #{b} &lt;/if&gt;
 * &lt;/where&gt;
 * </pre>
 */
public class DeleteRegistry extends GenericMappedStatementRegistry {

    public DeleteRegistry(RegistryArgs args) {
        super(args);
    }

    public String getStatementId() {
        return namespace + ".delete";
    }

    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.DELETE;
    }

    public SqlSource getSqlSource() {
        List<SqlNode> contents = new ArrayList<>();
        contents.add(new TextSqlNode("delete from " + tableName));
        contents.add(getWhereStatement());

        return new DynamicSqlSource(configuration, new MixedSqlNode(contents));
    }

    public KeyGenerator getKeyGenerator() {
        return new NoKeyGenerator();
    }

    public Class<?> getResultType() {
        return int.class;
    }
}
