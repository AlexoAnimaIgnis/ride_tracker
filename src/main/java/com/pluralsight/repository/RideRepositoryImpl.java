package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("select * from ride", new RowMapper<Ride>() {
			@Override
			public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
				Ride ride = new Ride();
				ride.setId(resultSet.getInt("id"));
				ride.setName(resultSet.getString("name"));
				ride.setDuration(resultSet.getInt("duration"));
				return ride;
			}
		});
		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
		//jdbcTemplate.update("insert into ride (NAME, DURATION) values(?,?)",ride.getName(), ride.getDuration());

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

		// Set Columns
		List<String> columns = new ArrayList<>();
		columns.add("name");
		columns.add("duration");

		insert.setTableName("ride");
		insert.setColumnNames(columns);

		Map<String, Object> data = new HashMap<>();
		data.put("name", ride.getName());
		data.put("duration", ride.getDuration());

		insert.setGeneratedKeyName("id"); // autoincremented id
		return null;
	}

}
