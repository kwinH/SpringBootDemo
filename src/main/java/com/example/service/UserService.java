package com.example.service;


import com.example.mapper.pojo.Users;
import com.github.pagehelper.PageInfo;

public interface UserService {

    boolean register(Users users);

    boolean login(Users users);

    PageInfo<Users> findItemByPage(int currentPage, int pageSize);
}
