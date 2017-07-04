package com.java.smart;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	@RequestMapping(value = "/SmartEditor2Skin")
	public String SmartEditor2Skin(Locale locale, Model model) {
		return "SmartEditor2Skin";
	}
	
	@RequestMapping(value = "/SmartEditor2")
	public String SmartEditor2(Locale locale, Model model) {
		return "SmartEditor2";
	}
	
	@RequestMapping(value = "/myEditor")
	public String myEditor(Locale locale, Model model) {
		return "myEditor";
	}
	
	@RequestMapping(value = "/sendText", method = RequestMethod.POST)
	public String sendText(Locale locale, Model model, @RequestParam("editor") String se) {
		
		System.out.println(se);
		return "myEditor";
	}


	@RequestMapping(value = "/photoUploader")
	public String photoUploader(Locale locale, Model model) {
		return "photo_uploader";
	}
	
	@RequestMapping(value = "/file_uploader_html5", method=RequestMethod.POST)
	public String fileUploaderHtml5(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		try {
			// 파일명을 받는다 - 일반 원본파일명
			String oldName = request.getHeader("file-name");
			// 파일 기본경로 _ 상세경로
			String filePath = "C:/Users/가을/workspace/smartEditorTest/src/main/webapp/resources/upload/";
			String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss")
                          .format(System.currentTimeMillis()))
                          .append(UUID.randomUUID().toString())
                          .append(oldName.substring(oldName.lastIndexOf("."))).toString();
			InputStream is = request.getInputStream();
			OutputStream os = new FileOutputStream(filePath + saveName);
			int numRead;
			byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
			while ((numRead = is.read(b, 0, b.length)) != -1) {
				os.write(b, 0, numRead);
			}
			os.flush();
			os.close();
			// 정보 출력
			sb = new StringBuffer();
			sb.append("&bNewLine=true")
			  .append("&sFileName=").append(oldName)
			  .append("&sFileURL=").append("http://localhost:8181/seTest/resources/upload/")
        .append(saveName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	

	}
}
