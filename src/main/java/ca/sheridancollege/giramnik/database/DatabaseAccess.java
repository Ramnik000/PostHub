package ca.sheridancollege.giramnik.database;

import java.awt.print.Printable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.giramnik.beans.Post;
import ca.sheridancollege.giramnik.beans.Thread;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	public void addThread(Thread thread) {
		String query = "INSERT INTO thread (username, title, date, time) VALUES (:username, :title, :date, :time)";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		namedParameters.addValue("username", loggedInUsername);

		namedParameters.addValue("title", thread.getTitle());
		namedParameters.addValue("date", thread.getDate());
		namedParameters.addValue("time", thread.getTime());
		jdbc.update(query, namedParameters);
	}

	public ArrayList<Thread> getThreadsForUser(String loggedInUsername) {
		loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		String query = "SELECT thread_id ,username, title, date, time FROM thread WHERE username = :username";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("username", loggedInUsername);

		ArrayList<Thread> list = new ArrayList<>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, namedParameters);

		for (Map<String, Object> row : rows) {
			Thread thread = new Thread();
			thread.setUsername((String) row.get("username"));
			thread.setThread_id((Long) row.get("thread_id"));
			thread.setTitle((String) row.get("title"));
			thread.setDate(((java.sql.Date) row.get("date")).toLocalDate());
			thread.setTime(((java.sql.Time) row.get("time")).toLocalTime());

			list.add(thread);
		}

		return list;
	}
	public ArrayList<Thread> getAllThreads() {
		
		String query = "SELECT thread_id ,username, title, date, time FROM thread";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		ArrayList<Thread> list = new ArrayList<>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, namedParameters);

		for (Map<String, Object> row : rows) {
			Thread thread = new Thread();
			thread.setUsername((String) row.get("username"));
			thread.setThread_id((Long) row.get("thread_id"));
			thread.setTitle((String) row.get("title"));
			thread.setDate(((java.sql.Date) row.get("date")).toLocalDate());
			thread.setTime(((java.sql.Time) row.get("time")).toLocalTime());

			list.add(thread);
		}

		return list;
	}

	public void addPost(Post post) {
        String query = "INSERT INTO post (thread_id, username, content) VALUES (:thread_id, :username, :content)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        namedParameters.addValue("username", loggedInUsername);
        namedParameters.addValue("thread_id", post.getThread_id());
        namedParameters.addValue("content", post.getContent());
        jdbc.update(query, namedParameters);
    }

    public List<Post> getPostsForUser() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String query = "SELECT thread_id, username, content FROM post WHERE username = :username";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("username", loggedInUsername);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Post.class));
    }

    public List<Post> getPostsForThread(Long id) {
        String query = "SELECT username, content FROM post WHERE thread_id = :thread_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("thread_id", id);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Post.class));
    }
    public int deleteThreadByID(Long thread_id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("thread_id", thread_id);
		String q1 = "DELETE FROM post where thread_id = :thread_id";
		String q2 = "DELETE FROM thread where thread_id = :thread_id";
	    jdbc.update(q1, namedParameters);
		return jdbc.update(q2, namedParameters);
	}
    public String getthreadNameByID(Long id) {
 	   String query = "SELECT title FROM thread WHERE thread_id = :thread_id";
       MapSqlParameterSource namedParameters = new MapSqlParameterSource();
       namedParameters.addValue("thread_id", id);
       Thread thread = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Thread.class)).get(0);
       return thread.getTitle();
}
}
