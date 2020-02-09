package cn.hanquan.bootstrapcurd.controller;

import cn.hanquan.bootstrapcurd.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class FileController {

    /**
     * 在application.properties中配置uploadPath，部署的时候方便随时修改
     */
    @Value("${hanquan.uploadPath}")
    private String uploadPath;

    @GetMapping("/uploadfile")
    public String uploadfile() {
        System.out.println(" here uploadfile");
        return "uploadfile";
    }

    @GetMapping("/downloadfile")
    public String downloadfile() {
        System.out.println(" here downloadfile");
        return "downloadfile";
    }


    /**
     * 单文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    Object upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("status", 0);
        String ext = file.getOriginalFilename().split("\\.")[1];
        String fileName = file.getOriginalFilename().split("\\.")[0];
        fileName = fileName + UUID.randomUUID().toString().substring(0, 4) + "." + ext; //对文件名称重命名

        try {
            FileUtil.uploadFile(file.getBytes(), uploadPath, fileName);
            map.put("filename", fileName);
        } catch (Exception e) {
            map.put("status", -1);
            map.put("message", e.getMessage());

        }


        return map;
    }

    /**
     * 多文件上传
     */
    @PostMapping("/uploads")
    @ResponseBody
    Object uploads(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("status", 0);
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : files
        ) {
            String ext = file.getOriginalFilename().split("\\.")[1];
            System.out.println("ext = " + ext);
            String fileName = file.getOriginalFilename().split("\\.")[0];
            System.out.println("original filename = " + fileName);
            fileName = fileName + UUID.randomUUID().toString() + "." + ext; //对文件名称重命名

            try {
                FileUtil.uploadFile(file.getBytes(), uploadPath, fileName);
                filenames.add(fileName);
            } catch (Exception e) {
                map.put("status", -1);
                map.put("message", e.getMessage());
                return map;
            }
        }

        map.put("filename", filenames);
        return map;
    }

    /**
     * 文件下载
     */
    @PostMapping("/download")
    public void download(String filename, HttpServletResponse res, HttpServletRequest req) throws IOException {
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
        ServletOutputStream os = res.getOutputStream();
        String path = uploadPath;
        System.out.println("In FilesController, path = " + path);
        System.out.println("In FilesController, filename = " + filename);
        File file = new File(path, filename);
        os.write(FileUtils.readFileToByteArray(file));
        os.flush();
        os.close();
    }
}
