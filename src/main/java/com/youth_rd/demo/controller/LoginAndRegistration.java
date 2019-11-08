package com.youth_rd.demo.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.youth_rd.demo.domain.User;
import com.youth_rd.demo.service.EmailService;
import com.youth_rd.demo.service.UserService;
import com.youth_rd.demo.tools.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Map;

@RestController
public class LoginAndRegistration {

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    //获取登录验证码
    @RequestMapping("/getValidCode")
    public ServerResponse getValidCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    //登录
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ServerResponse login(@RequestBody Map<String,String> jsonObj,
                                HttpServletRequest request){

        String validCode = jsonObj.get("validCode");
        String email = jsonObj.get("email");
        String pwd = jsonObj.get("pwd");

        String key = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(key!=validCode){
            return ServerResponse.createByError("验证码错误");
        }
        User user = userService.getUserByEmailAndPwd(email,pwd);

        if(user==null){
            return ServerResponse.createByError("用户名或密码错误");
        }

        request.getSession().setAttribute("user",user);

        return ServerResponse.createByCheckSuccess();
    }

    //发送注册邮箱验证码
    @RequestMapping(value = "/sendRegisterEmail",method = RequestMethod.POST)
    public ServerResponse sendRegisterEmail(@RequestBody Map<String,String> jsonObj,
                                            HttpServletRequest request){
        String email = jsonObj.get("email");
        User user = userService.getUserByEmail(email);
        if(user!=null){
            return ServerResponse.createByError("该邮箱已经注册");
        }
        String capText = captchaProducer.createText();
        request.getSession().setAttribute("registerCode",capText);
        emailService.sendSimpleMail(email,"信息青年注册验证码","你的验证码是:"+capText);
        return ServerResponse.createByCheckSuccess();
    }

    //注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ServerResponse register(@RequestBody Map<String,String> jsonObj,
                                   HttpServletRequest request){
        String validCode = jsonObj.get("validCode");
        String email = jsonObj.get("email");
        String pwd = jsonObj.get("pwd");
        String rePwd = jsonObj.get("rePwd");

        if(!pwd.equals(rePwd)){
            return ServerResponse.createByError("重复密码不正确");
        }
        String key = (String) request.getSession().getAttribute("registerCode");
        if(!key.equals(validCode)){
            return ServerResponse.createByError("邮箱验证码不正确");
        }
        request.getSession().setAttribute("registerCode",null);
        int result = userService.addUserByEmailAndPwd(email,pwd);
        if(result == 0){
            return ServerResponse.createByError("数据库异常,创建账户失败");
        }
        return ServerResponse.createByCheckSuccess();
    }

    //发送修改密码邮箱验证码
    @RequestMapping(value = "/sendUpdatePwdEmail",method = RequestMethod.POST)
    public ServerResponse sendUpdatePwdEmail(@RequestBody Map<String,String> jsonObj,
                                             HttpServletRequest request){
        String email = jsonObj.get("email");
        User user = userService.getUserByEmail(email);
        if(user==null){
            return ServerResponse.createByError("该邮箱未注册账号");
        }
        String capText = captchaProducer.createText();
        request.getSession().setAttribute("updateCode",capText);
        emailService.sendSimpleMail(email,"信息青年改密验证码","你的验证码是:"+capText);
        return ServerResponse.createByCheckSuccess();
    }

    //验证邮箱
    @RequestMapping(value = "/provingValidCode",method = RequestMethod.POST)
    public ServerResponse provingValidCode(@RequestBody Map<String,String> jsonObj,
                                           HttpServletRequest request){
        String email = jsonObj.get("email");
        String validCode = jsonObj.get("validCode");
        String key = (String) request.getSession().getAttribute("updateCode");
        if(key!=validCode){
            return ServerResponse.createByError("邮箱验证码不正确");
        }
        request.getSession().setAttribute("updateCode",null);
        request.getSession().setAttribute("updateEmail",email);
        return ServerResponse.createByCheckSuccess();
    }

    //修改密码
    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    public ServerResponse updatePwd(@RequestBody Map<String,String> jsonObj,
                                    HttpServletRequest request){
        String email = jsonObj.get("email");
        String pwd = jsonObj.get("pwd");
        String rePwd = jsonObj.get("rePwd");
        if(pwd!=rePwd){
            return ServerResponse.createByError("重复密码不正确");
        }
        String key = (String) request.getSession().getAttribute("updateEmail");
        if(key!=email){
            return ServerResponse.createByError("非法操作");
        }

        request.getSession().setAttribute("updateEmail",null);
        int result = userService.updatePwdByEmail(email,pwd);
        if(result==0){
            return ServerResponse.createByError("数据库异常，密码修改失败");
        }
        return ServerResponse.createByCheckSuccess();
    }
}
