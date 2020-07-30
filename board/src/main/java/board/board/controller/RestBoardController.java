package board.board.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import board.board.dto.BoardDto;
import board.board.service.BoardService;
import ch.qos.logback.classic.Logger;

@RestController
public class RestBoardController {
	private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board")
	public ModelAndView openBoardList() throws Exception{
		log.debug("/board 목록 출력=================");
		
		ModelAndView mv = new ModelAndView("/board/boardList");
		//int i = 10/0;
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	@GetMapping("/board/write")
	public ModelAndView openBoardWrite() throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardWrite");
		return mv;
	}
	
	@PostMapping("/board/write")
	public RedirectView insertBoard(BoardDto board) throws Exception{
		
		boardService.insertBoard(board);
		return new RedirectView("/board");
	}
	
	@GetMapping("/board/{boardIdx}")
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		
		mv.addObject("board", board);
		return mv;
	}
	
	@PutMapping("/board/{boardIdx}")
	public RedirectView updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return new RedirectView("/board");
	}
	
	@DeleteMapping("/board/{boardIdx}")
	public RedirectView deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return new RedirectView("/board");
	}
	
	
	
	
	
	
	
	
}
