package com.example.controller;

import com.example.enums.ResultEnum;
import com.example.exception.ResultException;
import com.example.mapper.dao.UsersDao;
import com.example.mapper.pojo.Users;
import com.example.response.Result;
import com.example.service.UserService;
import com.example.utils.PageBean;
import com.example.utils.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.example.enums.ResultEnum.UNKONW_ERROR;


@Slf4j
@Controller
public class UserInfoController {


    private final UserService userService;
    private final UsersDao usersDao;

    @Autowired
    public UserInfoController(UserService userServicer, UsersDao usersDao) {
        this.userService = userServicer;
        this.usersDao = usersDao;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@Valid Users users, BindingResult bindingResult) throws ResultException {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getFieldError().getDefaultMessage());
            return bindingResult.getFieldError().getDefaultMessage();
        }

        userService.register(users);

        return new ModelAndView("redirect:/user");

    }

    @RequestMapping("/login")
    @ResponseBody
    public Object login(@Valid Users users, BindingResult bindingResult) throws ResultException {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        userService.login(users);

        return ResultUtil.success();
        // return new ModelAndView("redirect:/users");
    }

    @GetMapping("/users")
    public String index(HttpServletRequest request, Model model, @RequestParam(value = "page", defaultValue = "0", required = false) int currentPage, @RequestParam(value = "size", defaultValue = "2", required = false) int pageSize) {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        model.addAttribute("user", user);

        PageInfo<Users> users = userService.findItemByPage(currentPage, pageSize);
        //= usersDao.selectAll();
        model.addAttribute("users", users);


        return "user";

        //return "redirect:/";

    }

    @DeleteMapping("users/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {

        int res = usersDao.deleteByPrimaryKey(id);
        log.info("delete res ={}", res);
        if (res == 0) {
            throw new ResultException(ResultEnum.UNKONW_ERROR);
        }
        return ResultUtil.success();
    }

    @GetMapping("users/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Users user = usersDao.selectByPrimaryKey(id);
        model.addAttribute("user", user);
        return "user_edit";
    }

    @PostMapping("users/{id}/edit")
    @ResponseBody
    public Result update(@PathVariable("id") Integer id, @Valid Users users, BindingResult bindingResult) throws ResultException {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        int res = usersDao.updateByPrimaryKeySelective(users);

        if (res == 0) {
            throw new ResultException(UNKONW_ERROR);
        }

        return ResultUtil.success();
    }


    /**
     * 分页功能(集成mybatis的分页插件pageHelper实现)
     *
     * @param currentPage :当前页数
     * @param pageSize    :每页显示的总记录数
     * @return
     */
    @RequestMapping("/itemsPage")
    @ResponseBody
    public PageInfo<Users> itemsPage(int currentPage, int pageSize) {
        log.info("currentPage={},pageSize={}", currentPage, pageSize);

        //1、设置分页信息，包括当前页数和每页显示的总计数
        PageHelper.startPage(currentPage, pageSize);
        //2、执行查询
        List<Users> list = usersDao.selectAll();        //全部

        //3、获取分页查询后的数据
        PageInfo<Users> pageInfo = new PageInfo<>(list);
        //4、封装需要返回的分页实体
        int countNums = (int) pageInfo.getTotal();
        PageBean<Users> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(list);

        return pageInfo;

        // return userService.findItemByPage(currentPage, pageSize);
    }
}
