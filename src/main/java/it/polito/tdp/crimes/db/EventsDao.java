package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.crimes.model.Event;
import it.polito.tdp.crimes.model.Vertice;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	public List<Vertice> getAllDistricts() {
		
		final String sql = "SELECT DISTINCT district_id AS d FROM `events` ORDER BY district_id";
		List<Vertice> vertici = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Vertice v = new Vertice(rs.getInt("d"));
				vertici.add(v);
			}
			conn.close();
			return vertici;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}
	public List<Event> getEvents(Vertice v, int anno) {
		final String sql = "SELECT * FROM `events` WHERE district_id = ? AND YEAR(reported_date) = ?";
		List<Event> eventi = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, v.getId());
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				eventi.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
			}
			conn.close();
			return eventi;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	public List<Integer> getYears() {
		
		final String sql = "SELECT DISTINCT YEAR(reported_date) AS y FROM `events` ORDER BY y";
		List<Integer> anni = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				anni.add(rs.getInt("y"));
			}
			
			conn.close();
			return anni;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}
	public List<Event> getEventsSimulator(int giorno, int mese, int anno) {
		
		final String sql = "SELECT * FROM `events` WHERE YEAR(reported_date) = ? AND MONTH(reported_date) = ? AND DAY(reported_date) = ?";
		List<Event> lista = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				lista.add(new Event(res.getLong("incident_id"),
						res.getInt("offense_code"),
						res.getInt("offense_code_extension"), 
						res.getString("offense_type_id"), 
						res.getString("offense_category_id"),
						res.getTimestamp("reported_date").toLocalDateTime(),
						res.getString("incident_address"),
						res.getDouble("geo_lon"),
						res.getDouble("geo_lat"),
						res.getInt("district_id"),
						res.getInt("precinct_id"), 
						res.getString("neighborhood_id"),
						res.getInt("is_crime"),
						res.getInt("is_traffic")));
			}
			conn.close();
			return lista;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}

}
