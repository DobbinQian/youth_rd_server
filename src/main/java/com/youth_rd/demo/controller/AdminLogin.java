package com.youth_rd.demo.controller;

import com.google.code.kaptcha.Producer;
import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.PowerService;
import com.youth_rd.demo.service.UserService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminLogin {

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private UserService userService;

    @Autowired
    private PowerService powerService;

    //管理员登录验证码
//    @RequestMapping("/getAdmValidCode")
//    public ServerResponse getValidCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        response.setDateHeader("Expires", 0);
//        // Set standard HTTP/1.1 no-cache headers.
//        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
//        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
//        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
//        // Set standard HTTP/1.0 no-cache header.
//        response.setHeader("Pragma", "no-cache");
//        response.setContentType("image/jpeg");
//        // create the text for the image
//        String capText = captchaProducer.createText();
//        // store the text in the session
//        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY+"_adm", capText);
//        // create the image with the text
//        BufferedImage bi = captchaProducer.createImage(capText);
//        ServletOutputStream out = response.getOutputStream();
//        // write the data out
//        ImageIO.write(bi, "jpg", out);
//        try {
//            out.flush();
//        } finally {
//            out.close();
//        }
//        return null;
//    }

    //登录
    @RequestMapping(value = "/adminLogin",method = RequestMethod.POST)
    public ServerResponse adminLogin(@RequestBody Map<String,String> jsonObj){

        String username = jsonObj.get("username");
        String pwd = jsonObj.get("pwd");

        String errStr = "";
        if(username==null){
            errStr+="username为空";
        }
        if(pwd==null){
            errStr+="pwd为空";
        }
        if(errStr.length()>0){
            return ServerResponse.createByError(errStr);
        }

        System.out.println(username+":"+pwd);
        User user = userService.getUserByEmailAndPwd(username,pwd);
        if(user==null){
            return ServerResponse.createByError("账号或密码错误");
        }
        if(!powerService.isExistByUserId(user.getId())){
            return ServerResponse.createByError("权限不足");
        }

        if(user==null){
            return ServerResponse.createByError("用户名或密码错误");
        }

        return ServerResponse.createByCheckSuccess();
    }

}
