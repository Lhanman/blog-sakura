package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.pojo.Message;
import com.Lhan.personal_blog.util.DataMap;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {
    void addMessage(Message message);

    DataMap findAllMessageByPage(int pageNum,int pageSize);

    String updateMessageCoverUrl(MultipartFile file);

    void replyMessage(int id);

    int findMessageNum();
}
