package kr.co.shop.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kr.co.shop.board.dto.ArticleDTO;
import kr.co.shop.board.service.BoardService;
import kr.co.shop.member.dto.MemberDTO;

@Controller("boardController")
public class BoardControllerImpl implements BoardController {

	private static final String ARTICLE_IMAGE_REPO = "C:\\workspace-spring\\self_img";
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	ArticleDTO articleDTO;
	
	@Override
	@RequestMapping(value = "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		List articlesList = boardService.listArticles();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articlesList", articlesList);
		return mav;
	}
	
	@RequestMapping(value = "/board/addNewArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		String imageFileName = upload(multipartRequest);
		
		HttpSession session = multipartRequest.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
		
		String id = memberDTO.getId();
		articleMap.put("parentNO", 0);
		articleMap.put("id", id);
		articleMap.put("imageFileName", imageFileName);
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders reHttpHeaders = new HttpHeaders();
		reHttpHeaders.add("content-type", "text/html; charset=utf-8");
		
		try {
			int articleNO = boardService.addNewArticle(articleMap);
			if(imageFileName != null && imageFileName.length()!=0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
			}
			
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do';";
			message += "</script>";
			resEnt = new ResponseEntity(message, reHttpHeaders, HttpStatus.CREATED);
			
		} catch (Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			
			message = "<script>";
			message += " alert('오류가 발생했습니다. 다시 시도해주세요');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do';";
			message += "</script>";
			resEnt = new ResponseEntity(message, reHttpHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		
		String imageFileName = null;
		Map<String, String> articleMap = new HashMap<String, String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName = mFile.getOriginalFilename();
			
			File file = new File(ARTICLE_IMAGE_REPO+"\\"+fileName);
			
			if(mFile.getSize()!=0) {
				if(! file.exists()) {	// 경로상 파일이 존재하지 않는 경우
					if(file.getParentFile().mkdirs()) {	// 경로에 해당하는 디렉토리 생성
						file.createNewFile(); // 이후 파일 생성
					}
				}
				//임시로 저장된 file 새로운 위치로 이동
				mFile.transferTo(new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName));
			}
		}
		return imageFileName;
	}

	@RequestMapping(value = "/board/*Form.do", method = RequestMethod.GET)
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	
}
