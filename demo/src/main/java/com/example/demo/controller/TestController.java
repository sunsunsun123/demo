package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Controller
public class TestController {
    private static List<UserInfo> userInfoList = new ArrayList();

    static {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setName("Andy");
        userInfo.setLoginname("andy");
        userInfoList.add(userInfo);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(2);
        userInfo1.setName("Carl");
        userInfo1.setLoginname("carl");
        userInfoList.add(userInfo1);
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setId(3);
        userInfo2.setName("Bruce");
        userInfo2.setLoginname("bruce");
        userInfoList.add(userInfo2);
        UserInfo userInfo3 = new UserInfo();
        userInfo3.setId(4);
        userInfo3.setName("Dolly");
        userInfo3.setLoginname("dolly");
        userInfoList.add(userInfo3);
    }


    @GetMapping("/")
    public ModelAndView index1(ModelMap map) {
        map.addAttribute("allStudent", userInfoList);
        return new ModelAndView("index", "userInfoModel", map);
    }

    /**
     * @param id
     *
     * @return
     */
    @RequestMapping("delete/{id}")
    public ModelAndView deleteStudent(@PathVariable("id") Long id) {
        for (UserInfo userInfo : userInfoList) {
            if (userInfo.getId() == id) {
                userInfoList.remove(userInfo);
                break;
            }
        }
        return new ModelAndView("redirect:/");//重新定向到index页面
    }

    /***
     *
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addStudent(ModelMap model) {
        return new ModelAndView("add");
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editStudent(@PathVariable("id") Long id) {
        UserInfo userInfo = userInfoList.stream().filter(it -> id==it.getId()).findFirst().get();
        return new ModelAndView("edit","userInfo",userInfo);
    }

    /**
     * @param userInfo
     * @return
     */
    @RequestMapping("/addUserInfo")
    public ModelAndView addUserInfo(UserInfo userInfo) {
        if (!CollectionUtils.isEmpty(userInfoList)) {
            UserInfo lastUserInfo = userInfoList.get(userInfoList.size() - 1);
            userInfo.setId(lastUserInfo.getId() + 1);
        } else {
            userInfo.setId(1);
        }
        userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
        userInfoList.add(userInfo);
        return new ModelAndView("redirect:/");//重新定向到index页面
    }
    /**
     * @param userInfo
     * @return
     */
    @RequestMapping("/editUserInfo")
    public ModelAndView insertStudent(UserInfo userInfo) {
        UserInfo userInfoOld = userInfoList.stream().filter(it -> userInfo.getId()==it.getId()).findFirst().get();
        BeanUtils.copyProperties(userInfo,userInfoOld);
        return new ModelAndView("redirect:/");//重新定向到index页面
    }


    @RequestMapping("/getStudent")
    public ModelAndView getStudent(HttpServletRequest req, ModelMap map) throws IOException {
        String loginname = req.getParameter("loginname");
        List<UserInfo> returnList = userInfoList.stream().filter(it -> it.getLoginname().equals(loginname)).collect(toList());
        map.addAttribute("allStudent", returnList);
        return new ModelAndView("index", "userInfoModel", map);
    }
}
