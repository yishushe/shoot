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
 */
@Component("isPath")
public class IsPath {

    @Value("${file.resourceHandler}")
    private String resourceHandler;

    @Value("${file.commonPath}")
    private String commonPath;

    @Value("${file.userPath}")
    private String userPath;

    @Value("${file.infoPath}")
    private String infoPath;

    public String[] upload(MultipartFile[] multipartFile, HttpServletRequest request,
                           HttpSession session) {

        if (multipartFile == null || multipartFile.length == 0) {
            return null;
        }
        String[] name1 = new String[multipartFile.length];
        for (int i = 0; i < multipartFile.length; i++) {
            // 检查服务器下是否有上传文件夹 http://192.168.1.27:8088
            String path = request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath();
            System.out.println("http:" + path);

            // 获取唯一文件名
            String name = multipartFile[i].getOriginalFilename();
            String type = name.substring(name.lastIndexOf(".")); // 获取后缀
            String uuid = UUID.randomUUID().toString(); // 创建随机名
            System.out.println("uuid:" + uuid);
            uuid = uuid.replace("-", "");
            String newName = uuid + type;

            name1[i] = newName;

            //需要访问的路劲
            String fileServerPath = path + resourceHandler.substring(0, resourceHandler.lastIndexOf("/") + 1) + newName;
            System.out.println("fileServerPath  " + fileServerPath);
            session.setAttribute("path", fileServerPath);

            try {
                //multipartFile.transferTo(new File(commonPath, newName));
                switch (session.getAttribute("temp").toString()) {
                    case "user":
                        multipartFile[i].transferTo(new File(userPath, newName));
                        break;
                    case "common":
                        multipartFile[i].transferTo(new File(commonPath, newName));
                        break;
                    case "info":
                        multipartFile[i].transferTo(new File(infoPath, newName));
                        break;
                    default:
                        System.out.println("出现错误!");
                        break;
                }
                //session.invalidate();  //session失效
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return name1;
    }

}
