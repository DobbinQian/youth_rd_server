package com.youth_rd.demo.service;

import java.util.List;
import java.util.Map;

public interface InformationService {

    //管理员查看所有未回复的反馈信息
    List<Map<String,Object>> getFeedbackList(Integer curr,Integer limit);

    //反馈投诉指定文章
    int feedbackByNewsId(Integer newsId,Integer userId,String content);

    //管理员回复指定投诉信息
    int replyFeedbackById(Integer infoId,Integer userId,Integer admId,String content);
}
