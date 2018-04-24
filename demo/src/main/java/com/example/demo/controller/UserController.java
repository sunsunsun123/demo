package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.request.UserInfoAddRequest;
import com.example.demo.request.UserInfoUpdateRequest;
import com.example.demo.response.UserInfoResponse;
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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
public class UserController extends BaseController {
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
    public ModelAndView index(HttpServletRequest request) {
        List<UserInfoResponse> userInfoResponses = new ArrayList<>();
        //返回参数对象和持久化对象分开
        if (!CollectionUtils.isEmpty(userInfoList)) {
            userInfoResponses = userInfoList.stream().map(
                    it -> {
                        UserInfoResponse userInfoResponse = new UserInfoResponse();
                        BeanUtils.copyProperties(it, userInfoResponse);
                        return userInfoResponse;
                    }
            ).collect(Collectors.toList());
        }
        ModelMap map = new ModelMap();
        map.addAttribute("allUser", userInfoResponses);
        return new ModelAndView("index", "userModel", map);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ModelAndView deleteStudent(@PathVariable("id") Long id, HttpServletRequest request) {
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
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addStudent(HttpServletRequest request) {
        return new ModelAndView("add");
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editStudent(@PathVariable("id") Long id, HttpServletRequest request) {
        UserInfo userInfo = userInfoList.stream().filter(it -> id == it.getId()).findFirst().get();
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtils.copyProperties(userInfo, userInfoResponse);
        return new ModelAndView("edit", "userInfo", userInfoResponse);
    }

    /**
     * @param
     * @return
     */
    @RequestMapping("/addUser")
    public ModelAndView addUserInfo(UserInfoAddRequest userInfoAddRequest, HttpServletRequest request) {
        try {
            validateModel(userInfoAddRequest);
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfoAddRequest, userInfo);
            if (!CollectionUtils.isEmpty(userInfoList)) {
                UserInfo lastUserInfo = userInfoList.get(userInfoList.size() - 1);
                userInfo.setId(lastUserInfo.getId() + 1);
            } else {
                userInfo.setId(1);
            }
            userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
            userInfoList.add(userInfo);
        } catch (Exception e) {
            return new ModelAndView("error");
        }

        return new ModelAndView("redirect:/");//重新定向到index页面
    }

    /**
     * @param userInfoUpdateRequest
     * @return
     */
    @RequestMapping("/editUser")
    public ModelAndView insertStudent(UserInfoUpdateRequest userInfoUpdateRequest, HttpServletRequest request) {
        try {
            validateModel(userInfoUpdateRequest);
            UserInfo userInfoOld = userInfoList.stream().filter(it -> userInfoUpdateRequest.getId() == it.getId()).findFirst().get();
            BeanUtils.copyProperties(userInfoUpdateRequest, userInfoOld);
        } catch (Exception e) {
            return new ModelAndView("error");
        }
        return new ModelAndView("redirect:/");//重新定向到index页面

    }


    @RequestMapping("/getUser")
    public ModelAndView getStudent(HttpServletRequest req, ModelMap map) throws IOException {
        String loginname = req.getParameter("loginname");
        List<UserInfo> returnList = userInfoList.stream().filter(it -> it.getLoginname().equals(loginname)).collect(toList());
        //返回参数对象和持久化对象分开
        List<UserInfoResponse> userInfoResponses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(returnList)) {
            userInfoResponses = returnList.stream().map(
                    it -> {
                        UserInfoResponse userInfoResponse = new UserInfoResponse();
                        BeanUtils.copyProperties(it, userInfoResponse);
                        return userInfoResponse;
                    }
            ).collect(Collectors.toList());
        }
        map.addAttribute("allUser", userInfoResponses);
        return new ModelAndView("index", "userModel", map);
    }
}
