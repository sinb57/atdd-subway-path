package wooteco.subway.infrastructure.jdbc.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import wooteco.subway.infrastructure.jdbc.dao.entity.StationEntity;

@Component
public class StationDao {

    private static final RowMapper<StationEntity> ROW_MAPPER =
            (resultSet, rowNum) -> new StationEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name")
            );

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public StationDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Station")
                .usingGeneratedKeyColumns("id");
    }

    public long save(StationEntity stationEntity) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(stationEntity);
        return jdbcInsert.executeAndReturnKey(parameters)
                .longValue();
    }

    public List<StationEntity> findAll() {
        String query = "SELECT id, name from Station";
        return jdbcTemplate.query(query, ROW_MAPPER);
    }

    public Optional<StationEntity> findById(long id) {
        String query = "SELECT id, name from Station WHERE id=(:id)";
        try {
            SqlParameterSource parameters = new MapSqlParameterSource("id", id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, parameters, ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existsById(long id) {
        String query = "SELECT EXISTS(SELECT id FROM Station WHERE id=(:id)) as existable";
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameters,
                (resultSet, rowNum) -> resultSet.getBoolean("existable")));
    }

    public boolean existsByName(String name) {
        String query = "SELECT EXISTS(SELECT id FROM Station WHERE name=(:name)) as existable";
        SqlParameterSource parameters = new MapSqlParameterSource("name", name);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameters,
                (resultSet, rowNum) -> resultSet.getBoolean("existable")));
    }

    public void remove(long id) {
        String query = "DELETE FROM Station WHERE id=(:id)";
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(query, parameters);
    }
}
