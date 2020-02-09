package cn.hanquan.bootstrapcurd.controller;

import cn.hanquan.bootstrapcurd.mapper.FileMapper;
import cn.hanquan.bootstrapcurd.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FileController {

    /**
     * 在application.properties中配置uploadPath，部署的时候方便随时修改
     */
    @Value("${hanquan.uploadPath}")
    private String uploadPath;

    @Autowired
    FileMapper fileMapper;

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
     * 查询所有File
     */
    @GetMapping("/files")
    public String list(Model model) {
        Collection<cn.hanquan.bootstrapcurd.entities.File> files = fileMapper.getFile();
        //放在请求域中
        model.addAttribute("files", files);
        System.out.println("In list, files = " + files);

        // thymeleaf默认就会拼串
        // classpath:/templates/xxx.html
        return "file/list";
    }


    /**
     * 单文件上传
     */
    @PostMapping("/upload")
//    @ResponseBody
//    Object upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("status", 0);
        String ext = file.getOriginalFilename().split("\\.")[1];
        String fileName = file.getOriginalFilename().split("\\.")[0];
        fileName = fileName + "-" + UUID.randomUUID().toString().substring(0, 4) + "." + ext; //对文件名称重命名

        cn.hanquan.bootstrapcurd.entities.File fileFromUser = new cn.hanquan.bootstrapcurd.entities.File();

        // 获取当前用户username
        String curUserName = (String) request.getSession().getAttribute("loginUser");
        System.out.println("In upload, curUserName = " + curUserName);

        //获取时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
        String curTime = dateFormat.format(calendar.getTime());
        System.out.println("In upload, current time is: " + curTime);

        fileFromUser.setName(fileName);
        fileFromUser.setUploadTime(curTime);
        fileFromUser.setUserName(curUserName);

        //数据库存储文件信息
        System.out.println("In upload, fileFromUser = " + fileFromUser);
        fileMapper.insertFile(fileFromUser);


        try {
            FileUtil.uploadFile(file.getBytes(), uploadPath, fileName);
            map.put("filename", fileName);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("status", -1);
            map.put("message", e.getMessage());
        }

        if (map.get("status") == (Object) 0) {
//            return "上传成功！添加UUID后的文件名：" + map.get("filename");
            return "redirect:/files";
        } else {
            return "上传失败！" + map;
        }
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
     * 根据filename下载文件
     */
    @PostMapping("/download")
    public void downloadExcel(HttpServletResponse res, HttpServletRequest req) throws IOException {

        String filename = "" + req.getAttribute("filename");
        System.out.println("In downloadExcel, filename = " + filename);
        String fullFileName = "workbook" + filename + ".xls";
        // 设置文件的显示名称
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fullFileName.getBytes("UTF-8"), "ISO8859-1"));
        ServletOutputStream os = res.getOutputStream();
        String path = uploadPath;

        System.out.println("In downloadExcel, path = " + path);
        System.out.println("In downloadExcel, fullFileName = " + fullFileName);

        File file = new File(path, fullFileName);
        os.write(FileUtils.readFileToByteArray(file));
        os.flush();
        os.close();
    }


    /**
     * 根据id下载文件
     */
    @PostMapping("/downloadFileById/{id}")
    public void downloadFileById(@PathVariable("id") Integer id, HttpServletResponse res, HttpServletRequest req) throws IOException {
        System.out.println("In downloadFileById, id = " + id);
        cn.hanquan.bootstrapcurd.entities.File file = fileMapper.getFileById(id);
        System.out.println("In downloadFileById, file = " + file);

        String filename = file.getName();
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
        ServletOutputStream os = res.getOutputStream();
        String path = uploadPath;

        System.out.println("In downloadFileById, path = " + path);
        System.out.println("In downloadFileById, filename = " + filename);

        File fileToUser = new File(path, filename);
        os.write(FileUtils.readFileToByteArray(fileToUser));
        os.flush();
        os.close();
    }

    /**
     * 删除一个 file
     */
    @DeleteMapping("/file/{id}")
    public String deleteFile(@PathVariable("id") Integer id) {
        System.out.println("In deleteFile, id = " + id);
        fileMapper.deleteFileById(id);
        return "redirect:/files";
    }
}
