package kr.co.shop.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileDownloadController {
	//파일 저장 위치 지정
	private static final String CURR_IMAGE_PEPO_PATH = "C:\\workspace-spring\\self_img";
	
	@RequestMapping("/download")	//다운로드할 이미지 파일 이름을 전달
	public void download(@RequestParam("imageFileName") String imageFileName, 
						@RequestParam("articleNO") String articleNO,
						HttpServletResponse response) throws Exception {
		
		OutputStream out = response.getOutputStream();
		
		String downFile = CURR_IMAGE_PEPO_PATH+"\\"+articleNO+"\\"+imageFileName;
		
		//다운로드 될 파일 객체 생성
		File file = new File(downFile);
		
		response.setHeader("Cache-Control", "no-cache");
		//헤더에 파일 이름 설정
		response.addHeader("Content-disposition", "attachment; fileName="+imageFileName);
		
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024*8];	//버퍼를 이용해 한꺼번에 8kb씩 브라우저에 전송됨
		while(true) {
			int count = in.read(buffer);
			if(count == -1) break;
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}
}
