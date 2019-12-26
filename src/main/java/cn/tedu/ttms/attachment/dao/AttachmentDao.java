package cn.tedu.ttms.attachment.dao;

import cn.tedu.ttms.attachment.entity.Attachment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentDao {
    int getRowCountByFileDigest(@Param("fileDigest") String fileDigest);
    int insertObject(Attachment attachment);
    List<Attachment> findObjects();
    Attachment findObjectById(Integer id);
}
