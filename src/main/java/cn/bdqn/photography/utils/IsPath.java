package cn.bdqn.photography.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传图片处理类
 * 
 * @author Administrator
 * 
 */
@Component("isPath")
public class IsPath {

	@Value("${file.resourceHandler}")
	private String resourceHandler;

	@Value("${file.commonPath}")
	private String commonPath;

	@Value("${file.userPath}")
	private String userPath;

	public String upload(MultipartFile multipartFile, HttpServletRequest request,
						 HttpSession session) {

		if(multipartFile==null || multipartFile.isEmpty()){
			return null;
		}

		// 检查服务器下是否有上传文件夹 http://192.168.1.27:8088
		String path = request.getScheme()+"://"+request.getServerName()+":"+
				request.getServerPort()+request.getContextPath();
		System.out.println("http:"+path);

		// 获取唯一文件名
		String name = multipartFile.getOriginalFilename();
		String type = name.substring(name.lastIndexOf(".")); // 获取后缀
		String uuid = UUID.randomUUID().toString(); // 创建随机名
		System.out.println("uuid:" + uuid);
		uuid = uuid.replace("-", "");
		String newName = uuid + type;

		//需要访问的路劲
		String fileServerPath=path+resourceHandler.substring(0,resourceHandler.lastIndexOf("/")+1)+newName;
        System.out.println("fileServerPath  "+fileServerPath);
        session.setAttribute("path",fileServerPath);


		try {
			switch (session.getAttribute("temp").toString()){
				case "user" :
					multipartFile.transferTo(new File(userPath, newName));
					break;
				case "common" :
					multipartFile.transferTo(new File(commonPath, newName));
					break;
				default:
                    System.out.println("出现错误!");
					break;
			}
			session.invalidate();  //session失效
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newName;

	}
}