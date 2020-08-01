package board.user.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import board.user.dto.UserDto;
import board.user.service.UserService;
import ch.qos.logback.classic.Logger;

@RestController
public class UserController {
	private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private UserService userService;
	
	@GetMapping("/login")
	public ModelAndView login() throws Exception{
		log.debug("로그인 화면 출력=================");
		
		ModelAndView mv = new ModelAndView("/user/login");
		
		return mv;
	}
	
	@PostMapping("/user/login")
	public RedirectView userLogin(HttpSession session, UserDto userDto) throws Exception{
		log.debug("로그인 =========================");
		String returnURL = "";
        if ( session.getAttribute("login") != null ){
            // 기존에 login이란 세션 값이 존재한다면
            session.removeAttribute("login"); // 기존값을 제거해 준다.
        }
          
        // 로그인이 성공하면 UsersVO 객체를 반환함.
        UserDto vo = userService.selectUser(userDto);
          
        if ( vo != null ){ // 로그인 성공
            session.setAttribute("login", vo); // 세션에 login인이란 이름으로 UsersVO 객체를 저장해 놈.
            returnURL = "/board"; // 로그인 성공시 메인페이지로 이동하고
        }else { // 로그인에 실패한 경우
            returnURL = "/login"; // 로그인 폼으로 다시 가도록 함
        }

		return new RedirectView(returnURL);
	}
	
	@GetMapping("/user/write")
	public ModelAndView userWrite() throws Exception{
		ModelAndView mv = new ModelAndView("/user/write");
		return mv;
	}
	
	@PostMapping("/user/write")
	public RedirectView userWrite(UserDto userDto) throws Exception{
		log.debug("회원가입=========================");
		userService.insertUser(userDto);
		
		return new RedirectView("/login");
	}
	
	// 로그아웃 하는 부분
	@GetMapping("/user/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate(); // 세션 초기화
        ModelAndView mv = new ModelAndView("/login");
		return mv;
    }

	/*
	 * @GetMapping("/board/write") public ModelAndView openBoardWrite() throws
	 * Exception{ ModelAndView mv = new ModelAndView("/board/boardWrite"); return
	 * mv; }
	 * 
	 * @PostMapping("/board/write") public RedirectView insertBoard(BoardDto board)
	 * throws Exception{
	 * 
	 * boardService.insertBoard(board); return new RedirectView("/board"); }
	 * 
	 * @GetMapping("/board/{boardIdx}") public ModelAndView
	 * openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
	 * 
	 * ModelAndView mv = new ModelAndView("/board/boardDetail");
	 * 
	 * BoardDto board = boardService.selectBoardDetail(boardIdx);
	 * 
	 * mv.addObject("board", board); return mv; }
	 * 
	 * @PutMapping("/board/{boardIdx}") public RedirectView updateBoard(BoardDto
	 * board) throws Exception{ boardService.updateBoard(board); return new
	 * RedirectView("/board"); }
	 * 
	 * @DeleteMapping("/board/{boardIdx}") public RedirectView
	 * deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
	 * boardService.deleteBoard(boardIdx); return new RedirectView("/board"); }
	 */
	
	
	
	
	
	
	
}
