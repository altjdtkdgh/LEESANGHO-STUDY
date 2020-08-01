package board.user.mapper;


import org.apache.ibatis.annotations.Mapper;

import board.user.dto.UserDto;

@Mapper
public interface UserMapper {

	void insertUser(UserDto userDto) throws Exception;

	UserDto selectUser(UserDto userDto) throws Exception;

}
