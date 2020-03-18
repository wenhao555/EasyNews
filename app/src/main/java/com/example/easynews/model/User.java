package com.example.easynews.model;

public class User
{
    private Integer id;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getAppUserId()
    {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId)
    {
        this.appUserId = appUserId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public Integer getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getStuName()
    {
        return stuName;
    }

    public void setStuName(String stuName)
    {
        this.stuName = stuName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getUserRole()
    {
        return userRole;
    }

    public void setUserRole(Integer userRole)
    {
        this.userRole = userRole;
    }

    private Integer appUserId;
    private String name;
    private String realName;
    private Integer teacherId;
    private String stuName;
    private String className;
    private String password;
    private Integer userRole;
}
