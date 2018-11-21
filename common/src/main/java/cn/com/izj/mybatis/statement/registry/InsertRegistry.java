package cn.com.izj.mybatis.statement.registry;

import cn.com.izj.mybatis.statement.RegistryArgs;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增语句
 *
 * <pre>
 * insert into [tableName]
 * (
 * 		&lt;if test=" someField1 != null"&gt;some_field1&lt;/if&gt;,
 * 		&lt;if test=" someField2 != null"&gt;some_field2&lt;/if&gt;,
 * 		&lt;if test=" someField3 != null"&gt;some_field3&lt;/if&gt;
 * )
 * values (
 * 		&lt;if test=" someField1 != null"&gt;#{someField1}&lt;/if&gt;,
 * 		&lt;if test=" someField2 != null"&gt;#{someField2}&lt;/if&gt;,
 * 		&lt;if test=" someField3 != null"&gt;#{someField3}&lt;/if&gt;
 * )
 * </pre>
 */
public class InsertRegistry extends GenericInsertRegistry {

    public InsertRegistry(RegistryArgs args) {
        super(args);
    }

    public String getStatementId() {
        return namespace + ".insert";
    }

    public SqlSource getSqlSource() {
        List<SqlNode> contents = new ArrayList<>();
        contents.add(new TextSqlNode("insert into " + tableName + " "));
        contents.add(getInsertColumnStatement());
        contents.add(getInsertFieldsStatement());

        return new DynamicSqlSource(configuration, new MixedSqlNode(contents));
    }
}
