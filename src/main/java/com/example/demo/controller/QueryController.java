package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description
 * @create 2018-07-01
 **/
@Controller
public class QueryController {
    //1.获取当前上下文对应的request请求
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
    //2.获取request请求中包含的session对象
    public static HttpSession getSession(){
        HttpSession session = null;
        try{
            session = getRequest().getSession();
        }catch (Exception e){
        }
        return session;
    }

    @Autowired
    private Service service;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String query(HttpServletRequest request, Model model){
        User user = (User)getSession().getAttribute("usersession");
        if (user == null) {
            model.addAttribute("message", "登陆已过期，请重新登陆");
            model.addAttribute("user",new User());
            return "Login";
        }
        request.setAttribute("user", user);
        List<Proposal> list = service.getAllProposal();
        request.setAttribute("proposals",list);
        return "query";
    }

}
