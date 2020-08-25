package board.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.board.dto.BoardDto;
import board.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		/*
		 * List<BoardDto> list = new ArrayList<BoardDto>();
		 * 
		 * BoardDto boardDto = new BoardDto(); boardDto.setBoardIdx(1);
		 * boardDto.setContents("ssssssss"); boardDto.setCreatorId("sss");
		 * boardDto.setTitle("제목 입니다."); list.add(0, boardDto);
		 */
		return  boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto board) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.insertBoard(board);
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		// TODO Auto-generated method stub
		
		boardMapper.updateHitCount(boardIdx);
		//int i = 10/0;
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		return board;
	}

	@Override
	public void updateBoard(BoardDto board) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.deleteBoard(boardIdx);
	}
}
