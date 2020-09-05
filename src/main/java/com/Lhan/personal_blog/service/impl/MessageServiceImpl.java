package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.dto.MessageDto;
import com.Lhan.personal_blog.mapper.MessageMapper;
import com.Lhan.personal_blog.pojo.Message;
import com.Lhan.personal_blog.pojo.MessageExample;
import com.Lhan.personal_blog.service.MessageService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.DateFormatUtil;
import com.Lhan.personal_blog.util.FileUtil;
import com.Lhan.personal_blog.util.JsonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    MessageMapper messageMapper;

    @Override
    @Transactional
    public void addMessage(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public DataMap findAllMessageByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<MessageDto> messageDtoList = new ArrayList<>();

        MessageExample example = new MessageExample();
        example.or();
        List<Message> messageList = messageMapper.selectByExample(example);
        PageInfo<Message> pageInfo = new PageInfo<>(messageList);
        for (Message message : messageList)
        {
            MessageDto messageDto = new MessageDto();
            messageDto.setId(message.getId());
            messageDto.setContent(message.getContent());
            messageDto.setCreateBy(DateFormatUtil.getDateString(message.getCreateBy()));
            messageDto.setEmail(message.getEmail());
            messageDto.setIp(message.getIp());
            messageDto.setName(message.getName());
            messageDto.setIs_replied(message.getIsReplied());
            messageDtoList.add(messageDto);
        }
        JSONArray messageJsonArray = JSONArray.fromObject(messageDtoList);

        return DataMap.success().setData(messageJsonArray);
    }

    @Override
    public String updateMessageCoverUrl(MultipartFile file) {
        DateFormatUtil dateFormatUtil = new DateFormatUtil();
        if (file.isEmpty())
        {
            return null;
        }
        FileUtil fileUtil = new FileUtil();
//        String filePath = this.getClass().getResource("/").getPath().substring(1) + "message/";
        String fileContentType = file.getContentType();
        String fileExtension = fileContentType.substring(fileContentType.indexOf("/") +1);
        String fileName = dateFormatUtil.getLongTime()+"."+fileExtension;
        String subCatalog = "message/"+DateFormatUtil.getDateString(new Date());

        String cover_url = fileUtil.uploadFile(file,subCatalog,fileName);

        return cover_url;
    }

    @Override
    @Transactional
    public void replyMessage(int id) {
        Message message = messageMapper.selectByPrimaryKey(id);
        message.setIsReplied(true);
        messageMapper.updateByPrimaryKeySelective(message);
    }

    @Override
    public int findMessageNum() {
        MessageExample example = new MessageExample();
        example.or();
        List<Message> messageList = messageMapper.selectByExample(example);
        return messageList.size();
    }


}
