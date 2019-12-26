package cn.tedu.ttms.attachment.controller;

import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.service.AttachmentService;
import cn.tedu.ttms.common.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/attachment/")
public class AttachmentController {
    @Autowired
    @Qualifier("attachmentServiceImpl")
    private AttachmentService attachmentService;
    @RequestMapping("attachmentUI")
    public String attachmentUI(){
        return "attachment/attachment";
    }
    @RequestMapping("doUpload")
    public JsonResult doUpload(String title, MultipartFile mFile) throws IOException {
        attachmentService.savaObject(title,mFile);
        return new JsonResult("upLoad ok");
    }
    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        List<Attachment> list = attachmentService.findObjects();
        return new JsonResult(list);
    }
    @RequestMapping("doDownload")
    @ResponseBody
    public byte[] doDownload(Integer id, HttpServletResponse response) throws IOException {
        Attachment attachment = attachmentService.findObjectByid(id);
        response.setContentType("application/octet-stream");
        String fileName=URLEncoder.encode(attachment.getFileName(),"utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename="+fileName);
        Path path = Paths.get(attachment.getFilePath());
        return Files.readAllBytes(path);
    }
}
