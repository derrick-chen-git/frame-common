package com.frame.common.shardingDatasource;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.Data;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * mybatis-plus配置文件
 */
@Configuration
@EnableTransactionManagement
@Data
public class MybatisPlusConfig {
    @Value("${mybatis-plus.mapper-locations}")
    private org.springframework.core.io.Resource[] mapperLocations;
    @Value("${mybatis-plus.type-aliases-package}")
    private String typeAliasesPackage;
    @Value("${mybatis-plus.configuration.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;
    @Value("${mybatis-plus.configuration.cache-enabled}")
    private boolean cacheEnabled;
    @Value("${mybatis-plus.global-config.db-config.column-like}")
    private boolean columnLike;
    @Value("${mybatis-plus.global-config.db-config.db-type}")
    private String dbType;
    @Value("${mybatis-plus.global-config.db-config.logic-delete-value}")
    private String logicDeleteValue;
    @Value("${mybatis-plus.global-config.db-config.logic-not-delete-value}")
    private String logicNotDeleteValue;
    @Resource(name = "dataSource")
    private DataSource dataSource;


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
        return paginationInterceptor;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setMapperLocations(mapperLocations);
        sqlSessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        configuration.setCacheEnabled(cacheEnabled);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(new Interceptor[]{ //PerformanceInterceptor(),OptimisticLockerInterceptor()
                paginationInterceptor(),optimisticLockerInterceptor() //添加分页功能
        });
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setSqlInjector(new LogicSqlInjector());
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setColumnLike(columnLike);
        dbConfig.setIdType(IdType.INPUT);
        dbConfig.setLogicDeleteValue(logicDeleteValue);
        dbConfig.setLogicNotDeleteValue(logicNotDeleteValue);
        dbConfig.setDbType(DbType.getDbType(dbType));
        globalConfig.setDbConfig(dbConfig);
        sqlSessionFactory.setGlobalConfig(globalConfig);
        //sqlSessionFactory.setGlobalConfig();
        //sqlSessionFactory.setGlobalConfig(globalConfiguration());
        return sqlSessionFactory.getObject();
    }


    /**
     * 性能分析插件
     *
     * @return
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * 乐观锁插件
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


}
