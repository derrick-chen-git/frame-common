package com.frame.common.datasource;

import com.baomidou.mybatisplus.entity.DefaultMetaObjectHandler;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
@ConfigurationProperties(prefix="mybatis-plus.global-config")
@Data
public class GlobalConfig {
    /**
     * 逻辑删除全局值
     */
    private String logicDeleteValue = null;
    /**
     * 逻辑未删除全局值
     */
    private String logicNotDeleteValue = null;
    /**
     * 表前缀
     */
    private String tablePrefix;
    /**
     * 数据库类型
     */
    private DBType dbType;
    /**
     * 主键类型（默认 ID_WORKER）
     */
    private IdType idType = IdType.ID_WORKER;
    /**
     * 表名、字段名、是否使用下划线命名（默认 true: 数据库下划线命名）
     */
    private boolean dbColumnUnderline = true;
    /**
     * SQL注入器
     */
    private ISqlInjector sqlInjector;
    /**
     * 表关键词 key 生成器
     */
    private IKeyGenerator keyGenerator;
    /**
     * 元对象字段填充控制器
     */
    private MetaObjectHandler metaObjectHandler = new DefaultMetaObjectHandler();
    /**
     * 字段验证策略
     */
    private FieldStrategy fieldStrategy = FieldStrategy.NOT_NULL;
    /**
     * 是否刷新mapper
     */
    private boolean isRefresh = false;
    /**
     * 是否大写命名
     */
    private boolean isCapitalMode = false;
    /**
     * 标识符
     */
    private String identifierQuote;
    /**
     * 缓存当前Configuration的SqlSessionFactory
     */
    private SqlSessionFactory sqlSessionFactory;
    /**
     * 缓存已注入CRUD的Mapper信息
     */
    private Set<String> mapperRegistryCache = new ConcurrentSkipListSet<>();
    /**
     * 单例重用SqlSession
     */
    private SqlSession sqlSession;
    /**
     * 缓存 Sql 解析初始化
     */
    private boolean sqlParserCache = false;

}
