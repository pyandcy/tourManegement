package cn.tedu.ttms.attachment.service;

import cn.tedu.ttms.attachment.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    void savaObject(String title, MultipartFile mFile);
    List<Attachment> findObjects();
    Attachment findObjectByid(Integer id);
}
