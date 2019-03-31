package com.example.service.impl;

import com.example.exception.ResultException;
import com.example.mapper.dao.UsersDao;
import com.example.mapper.pojo.Users;
import com.example.service.UserService;
import com.example.utils.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.example.enums.ResultEnum.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    private final UsersDao usersDao;
    private final HttpServletRequest request;

    @Autowired
    public UserServiceImpl(UsersDao usersDao, HttpServletRequest request) {
        this.usersDao = usersDao;
        this.request = request;
    }


    public boolean register(Users users) throws ResultException {
        log.info("UserRepository.register");

        if (usersDao.userExists(users.getName())) {
            throw new ResultException(USER_REGISTER_EXISTS_ERROR);
        }
        int res = usersDao.insert(users);
        if (res == 0) {
            throw new ResultException(UNKONW_ERROR);
        }

        return login(users);

    }


    public boolean login(Users users) throws ResultException {
        log.info("UserRepository.login");

        if (usersDao.userAndPasswordExists(users.getName(), users.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", users.getName());
            return true;

        } else {
            throw new ResultException(USER_LOGIN_ERROR);
        }
    }


    @Override
    public PageInfo<Users> findItemByPage(int currentPage, int pageSize) {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);

        //1、设置分页信息，包括当前页数和每页显示的总计数
        PageHelper.startPage(currentPage, pageSize);
        //2、执行查询
        List<Users> list = usersDao.selectAll();        //全部

        //3、获取分页查询后的数据
        return new PageInfo<>(list);

        //4、封装需要返回的分页实体
        //  int countNums = (int) pageInfo.getTotal();
        //  PageBean<Users> pageData = new PageBean<>(currentPage, pageSize, countNums);
        //   pageData.setItems(list);
        //return pageData.getItems();

    }
}
