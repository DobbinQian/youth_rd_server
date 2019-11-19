package com.youth_rd.demo.tools;

import com.youth_rd.demo.domain.Comment;

import java.text.SimpleDateFormat;
import java.util.*;

public class CommentListFormat {
    public static List<Map<String,Object>> format(List<Comment> commentList, Integer commentId){
        List<Map<String,Object>> resultList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(commentList.size());
        Iterator<Comment> it = commentList.iterator();
        while (it.hasNext()){
            Map<String,Object> map = new HashMap<>();
            Comment c = it.next();
            if(c.getReplyId()==commentId){
                System.out.println("Id:"+c.getId());
                map.put("id",c.getId());
                map.put("cid",c.getReplyId());
                map.put("content",c.getContent());
                map.put("date",formatter.format(c.getTime()));
                resultList.add(map);
                it.remove();
            }
        }

        if(commentList.size()!=0){
            for(Map<String,Object> map :resultList){
                map.put("reply",format(commentList,(Integer)map.get("id")));
            }
        }
        return resultList;
    }
}
