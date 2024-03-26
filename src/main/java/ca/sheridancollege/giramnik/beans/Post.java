package ca.sheridancollege.giramnik.beans;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long post_id;
    private Long thread_id;
    private String username;
    private String content;

}
