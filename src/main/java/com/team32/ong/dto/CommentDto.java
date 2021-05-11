package com.team32.ong.dto;

import com.team32.ong.model.News;
import com.team32.ong.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD

=======
>>>>>>> 69689d3f802e979a07c7560b01995bba9b6d064f
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
public class CommentDto{
	
	private long id;
	private String body;
	private UserDTOResponse user;
	private News news;

=======
public class CommentDto {

    private long id;
    private String body;
    private User user;
    private News news;
>>>>>>> 69689d3f802e979a07c7560b01995bba9b6d064f
}
