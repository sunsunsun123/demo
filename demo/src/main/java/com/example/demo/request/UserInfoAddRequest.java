package com.example.demo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel("添加用户参数")
public class UserInfoAddRequest {
    @ApiModelProperty(value = "登录名",required = true,hidden = false)
    @NotEmpty(message = "登录名必填")
    private String loginname;

    @ApiModelProperty(value = "密码",required = true,hidden = false)
    @NotEmpty(message = "密码必输")
    private String password;

    @ApiModelProperty(value = "用户名",required = true,hidden = false)
    @NotEmpty(message = "用户名必输")
    private String name;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
