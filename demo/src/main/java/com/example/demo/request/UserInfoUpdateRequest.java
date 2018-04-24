package com.example.demo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("用户更新参数")
public class UserInfoUpdateRequest {
    @ApiModelProperty(value = "用户id",required = true,hidden = false)
    @NotNull(message = "用户id必输")
    private Long id;

    @ApiModelProperty(value = "登录名",required = true,hidden = false)
    @NotEmpty(message = "登录名必输")
    private String loginname;


    @ApiModelProperty(value = "用户名",required = true,hidden = false)
    @NotEmpty(message = "用户名必输")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
