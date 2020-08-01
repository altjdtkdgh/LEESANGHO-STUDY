package board.user.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
	private String id;
	private String pw;
	private String userName;
	private LocalDateTime createdDatetime;
	private LocalDateTime updateDatetime;
	private String userYn;
	private String groupId;
}
