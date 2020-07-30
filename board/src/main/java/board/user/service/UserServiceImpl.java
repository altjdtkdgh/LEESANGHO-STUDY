package board.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.board.mapper.BoardMapper;
import board.user.dto.UserDto;
import board.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void insertUser(UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		userMapper.insertUser(userDto);
	}

	@Override
	public UserDto selectUser(UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.selectUser(userDto);
	}
}
