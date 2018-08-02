package springframework.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LabelRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public DataSource dataSource() {
        String neo4jUrl = "jdbc:neo4j:http://localhost:7474";
        return new DriverManagerDataSource(neo4jUrl);
    }
}
