//package ru.hogwarts.school.sql_requests;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Component;
//import ru.hogwarts.school.model.Faculty;
//
//import javax.sql.DataSource;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.List;
//
//@Component
//public class MySql {
//    @Autowired
//    private DataSource datasource;
//
//    public Collection<Faculty> getObjectsByCustomQuery(String customQuery) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
//        List<Faculty> objects = jdbcTemplate.query(customQuery, new RowMapper<Faculty>() {
//            @Override
//            public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Faculty object = new Faculty();
//                object.setId(rs.getInt("id"));
//                object.setName(rs.getString("name"));
//                // map other columns to object properties
//                return object;
//            }
//        });
//        return objects;
//    }
//}
