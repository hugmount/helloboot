package com.hugmount.helloboot.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author: Li Huiming
 * @Date: 2019/3/13
 */
@Configuration
@MapperScan(basePackages = "com.hugmount.helloboot.test.mapper", sqlSessionTemplateRef  = "slaverSqlSessionTemplate")
public class SlaverDataSourceConfiguration {

    @Value("${spring.datasource.slaver.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.slaver.url}")
    private String url;

    @Value("${spring.datasource.slaver.username}")
    private String username;

    @Value("${spring.datasource.slaver.password}")
    private String password;


    @Bean(name = "slaverDataSource")
    public DataSource dataSource() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        DruidXADataSource dataSource = new DruidXADataSource ();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        atomikosDataSourceBean.setXaDataSource(dataSource);

        return atomikosDataSourceBean;
    }

    @Bean(name = "slaverSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("slaverDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/hugmount/helloboot/test/mapper/**/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "slaverSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("slaverSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
