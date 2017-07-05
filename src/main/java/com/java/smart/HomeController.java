package com.java.smart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
	
	@RequestMapping(value = "/")
	public String main(Locale locale, Model model) {
		return "myEditor";
	}
	
	@RequestMapping(value = "/myEditor")
	public String myEditor(Locale locale, Model model) {
		return "myEditor";
	}
	
	@RequestMapping(value = "/sendText", method = RequestMethod.POST)
	public String sendText(Locale locale, Model model, @RequestParam("editor") String se) {
		//console에 출력
		System.out.println(se);
		return "myEditor";
	}

	@RequestMapping(value = "/file_uploader_html5", method=RequestMethod.POST)
	public void fileUploaderHtml5(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		try {
			System.out.println("file upload controller");
			System.out.println(request.getSession().getServletContext().getRealPath("/"));
	        String sFileInfo = "";

			// 파일명을 받는다 - 일반 원본파일명
			String oldName = request.getHeader("file-name");
			// 파일 기본경로 _ 상세경로
			
			String dftFilePath = request.getSession().getServletContext().getRealPath("/");
			String filePath = dftFilePath + "resources" + File.separator + "upload" + File.separator;
					
				//	"C:/Users/daou/workspace/smartEditorTest/src/main/webapp/resources/upload/";
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

	         sFileInfo += "&bNewLine=true";
	         // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위해
	         sFileInfo += "&sFileName="+ oldName;
	         sFileInfo += "&sFileURL="+"/smart/resources/upload/"+ saveName;

	         System.out.println("sfileInfo ::: " + sFileInfo);
	         PrintWriter print = response.getWriter();
	         print.print(sFileInfo);
	         print.flush();
	         print.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
