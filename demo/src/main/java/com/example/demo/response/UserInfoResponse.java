package com.example.demo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户返回参数")
public class UserInfoResponse {

    @ApiModelProperty(value = "用户id",required = true,hidden = false)
    private Long id;

    @ApiModelProperty(value = "登录名",required = true,hidden = false)
    private String loginname;

    @ApiModelProperty(value = "密码",required = true,hidden = false)
    private String password;

    @ApiModelProperty(value = "用户名",required = true,hidden = false)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
