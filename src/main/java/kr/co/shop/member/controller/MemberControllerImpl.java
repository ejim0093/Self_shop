package kr.co.shop.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.shop.member.dto.MemberDTO;
import kr.co.shop.member.service.MemberService;

/*
 * @Controller를 이용해 MemberControllerImpl 클레스에 id가 memberController 인 빈을 자동 생성
 */
@Controller("memberController")
@EnableAspectJAutoProxy	//AOP 기능 활성화
public class MemberControllerImpl extends MultiActionController implements MemberController {
	
	//private static final Logger logger = LoggerFactory.getLogger("MemberControllerImpl");
	
	// @Autowired를 이용해 id가 memberService인 빈을 자동 주입
	@Autowired
	private MemberService memberService;
	// @Autowired를 이용해 id가 MemberDTO 빈을 자동 주입
	@Autowired
	private MemberDTO memberDTO;

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	// 두 단계로 요청시 바로 해당 메서드를 호출하도록 매핑함
	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* 브라우저에서의 요청명에서 확장명 .do를 제외한 뷰이름을 가져옴 */
		//String viewName = getViewName(request);
		String viewName = (String) request.getAttribute("viewName");
		
		//logger.debug("debug 레벨 : viewName = " + viewName);
		
		List<MemberDTO> membersList = memberService.listMembers();
				
		ModelAndView mav = new ModelAndView(viewName);	// viewName이 definition에 설정한 뷰 이름과 일치함
		// 조회한 회원 정보를 ModelAndView의 addObject() 이용해 바인딩함
		mav.addObject("membersList", membersList);
		return mav;		// ModelAndView 객체에 설정한 뷰 이름을 타일즈 뷰리졸버로 반환
	}

	@RequestMapping(value = "/member/*Form.do", method = RequestMethod.GET)
	public ModelAndView form(@RequestParam(value = "result",  required = false) String result,
							 @RequestParam(value = "action",  required = false) String action,
					HttpServletRequest request, HttpServletResponse response) throws Exception {

		String viewName = (String) request.getAttribute("viewName");
		
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName(viewName);
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberDTO memberDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		int result = memberService.addMember(memberDTO);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	//전송된 id를 변수 id에 설정함
	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//String id = request.getParameter("id");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member")MemberDTO member, RedirectAttributes rAttributes, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		memberDTO = memberService.login(member);
		
		if(memberDTO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", memberDTO);	// 세션에 회원 정보를 저장함
			session.setAttribute("isLogOn", true);		// 세션에 로그인 상태를 true로 설정함
			
			String action = (String) session.getAttribute("action");
			session.removeAttribute("action");
			
			if (action != null) {
				mav.setViewName("redirect:"+action);
			} else {
				mav.setViewName("redirect:/member/listMembers.do");
			}
		} else {
			rAttributes.addAttribute("result", "loginFailed");	// 로그인 실패 시 실패 메시지를 로그인창 전달
			mav.setViewName("redirect:/member/loginForm.do");	// 로그인 실패 시 다시 로그인창으로 리다이렉트
		}
		
		return mav;
	}

	@Override
	@RequestMapping(value = "member/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		//로그아웃 요청 시 세션에 저장된 로그인 정보와 회원 정보 삭제 
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/loginForm.do");
		
		return mav;
	}
	
	
}
