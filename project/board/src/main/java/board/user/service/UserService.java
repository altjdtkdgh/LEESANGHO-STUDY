package board.user.service;

import board.user.dto.UserDto;

public interface UserService {

	void insertUser(UserDto userDto) throws Exception;

	UserDto selectUser(UserDto userDto) throws Exception;

}
