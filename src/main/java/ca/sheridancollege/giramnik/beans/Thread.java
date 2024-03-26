package ca.sheridancollege.giramnik.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thread implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private Long thread_id;
	private String username;
	private String title;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime time;

}
