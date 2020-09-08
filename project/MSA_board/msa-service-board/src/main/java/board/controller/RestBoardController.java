package board.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import ch.qos.logback.classic.Logger;

@RefreshScope
@RestController
@RequestMapping(value="board")
public class RestBoardController {
	private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Value("${msaconfig.greeting}")
	private String greeting;
	
	@HystrixCommand
	@GetMapping("/")
	public String openBoardList() throws Exception{
		log.debug("/board 목록 출력=================");
		System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeee : "+ "board2");

		return "성공 /board==============================================="+ greeting;
	}
	@RequestMapping(value="/{firstName}" , method=RequestMethod.GET)
	public String openBoard(@PathVariable("firstName") String firstName) throws Exception{
		log.debug("/board 목록 출력=================");
		System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeee : "+ "board2");

		return String.format("{\"message\"; \"Hello %s\"}", firstName);
	}
}
