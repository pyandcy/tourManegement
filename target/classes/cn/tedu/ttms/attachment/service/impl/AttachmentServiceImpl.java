package cn.tedu.ttms.attachment.service.impl;

import cn.tedu.ttms.attachment.dao.AttachmentDao;
import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.service.AttachmentService;
import cn.tedu.ttms.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.invoke.empty.Empty;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Resource
    private AttachmentDao attachmentDao;
//    将文件上传到服务器
//    将文件上传到数据库
    @Override
    public void savaObject(String title, MultipartFile mFile) {
        if (title==null||title.trim().length()==0)
            throw new ServiceException("上传标题不能为空!");
        if (mFile==null||mFile.isEmpty())
            throw new ServiceException("上传文件不能为空!");
        byte[] bytes=null;
        String fileDigest;
        try {
            bytes = mFile.getBytes();
            fileDigest = DigestUtils.md5DigestAsHex(bytes);
            System.out.println(fileDigest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("文件摘要创建失败!");
        }
        if (attachmentDao.getRowCountByFileDigest(fileDigest)>0)
            throw new ServiceException("文件已经上传过了，不能再次上传！");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDir = sdf.format(new Date());
        String baseDir="d:/TmsTourUpload/";
        File upLoadDir = new File( baseDir+dateDir);
        if (!upLoadDir.exists()){
            upLoadDir.mkdir();
        }
        String filename = mFile.getOriginalFilename();
        File destFile = new File(upLoadDir,filename);
        try {
            mFile.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("文件上传失败");
        }
        System.out.println(destFile);
        Attachment attachment = new Attachment();
        attachment.setTitle(title);
        attachment.setFileName(filename);
        attachment.setFileDigest(fileDigest);
        attachment.setFilePath(destFile.getAbsolutePath());
        attachment.setContentType(mFile.getContentType());
        attachment.setAthType(1);
        attachment.setBelongId(1);
        int rows = attachmentDao.insertObject(attachment);
        if (rows<=0)
            throw new ServiceException("insert error");
    }

    @Override
    public List<Attachment> findObjects() {
        return attachmentDao.findObjects();
    }

    @Override
    public Attachment findObjectByid(Integer id) {
        if (id==null)
            throw new ServiceException("请输入要下载的文件id！");
        Attachment attachment = attachmentDao.findObjectById(id);
        if (attachment==null)
            throw new ServiceException("对象不存在！");
        return attachment;
    }
}
