package cn.com.izj.mybatis.statement;

import cn.com.izj.mybatis.annotation.Id;
import cn.com.izj.mybatis.annotation.Table;
import cn.com.izj.mybatis.annotation.Transient;
import cn.com.izj.mybatis.statement.registry.*;
import cn.com.izj.utils.ReflectionUtil;
import cn.com.izj.utils.StringUtil;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GouBo on 2018/6/6 22:46.
 * SQL注册器聚合
 */
public class MappedStatementRegistryComposite implements MappedStatementRegistry {

    private Configuration configuration;

    private MapperBuilderAssistant assistant;

    private String namespace;

    private String tableName;

    private Class<?> entityClass;

    private Field idField;

    private List<Field> columnFields = new ArrayList<>();

    private List<MappedStatementRegistry> mappedStatementRegistrys;

    public MappedStatementRegistryComposite(Configuration configuration, String namespace, Class<?> entityClass) {
        this.configuration = configuration;
        this.entityClass = entityClass;
        this.namespace = namespace;

        Table table = entityClass.getAnnotation(Table.class);

        String resource = namespace.replace(".", "/") + ".java";

        assistant = new MapperBuilderAssistant(configuration, resource);
        assistant.setCurrentNamespace(namespace);

        if (org.apache.commons.lang3.StringUtils.isEmpty(table.value())) {
            this.tableName = StringUtil.camelToUnderScore(entityClass.getSimpleName());
        } else {
            this.tableName = table.value();
        }

        this.idField = ReflectionUtil.findFieldWithAnnotation(entityClass, Id.class);

        ReflectionUtils.doWithFields(entityClass, field -> columnFields.add(field), field -> {
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                return false;
            }

            for (Annotation annotation : field.getAnnotations()) {
                if (Transient.class.isAssignableFrom(annotation.getClass())
                        || Id.class.isAssignableFrom(annotation.getClass())) {
                    return false;
                }
            }
            return true;
        });
        mappedStatementRegistrys = getDefaultMappedStatementRegistrys();
    }

    private List<MappedStatementRegistry> getDefaultMappedStatementRegistrys() {
        RegistryArgs registryArgs = buildRegistryArgs();

        List<MappedStatementRegistry> mappedStatementRegistrys = new ArrayList<>();

        mappedStatementRegistrys.add(new CountRegistry(registryArgs));
        mappedStatementRegistrys.add(new DeleteAllByIdRegistry(registryArgs));
        mappedStatementRegistrys.add(new DeleteByIdRegistry(registryArgs));
        mappedStatementRegistrys.add(new DeleteRegistry(registryArgs));
        mappedStatementRegistrys.add(new ExistsByIdRegistry(registryArgs));
        mappedStatementRegistrys.add(new FindAllByIdRegistry(registryArgs));
        mappedStatementRegistrys.add(new FindAllRegistry(registryArgs));
        mappedStatementRegistrys.add(new FindByIdRegistry(registryArgs));
        mappedStatementRegistrys.add(new InsertRegistry(registryArgs));
        mappedStatementRegistrys.add(new UpdateByIdRegistry(registryArgs));

        return mappedStatementRegistrys;
    }

    private RegistryArgs buildRegistryArgs() {
        RegistryArgs args = new RegistryArgs();

        args.setAssistant(assistant);
        args.setColumnFields(columnFields);
        args.setConfiguration(configuration);
        args.setEntityClass(entityClass);
        args.setIdField(idField);
        args.setNamespace(namespace);
        args.setTableName(tableName);

        return args;
    }

    public void registerMappedStatement() {
        for (MappedStatementRegistry registry : mappedStatementRegistrys) {
            registry.registerMappedStatement();
        }
    }
}
