package com.example.mapper.dao;

import com.example.mapper.baseDao.IBaseDao;
import com.example.mapper.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UsersDao extends IBaseDao<Users> {

    long countByExample();

    @Select("select count(*) as c from users where name=#{name}")
    Boolean userExists(@Param("name") String name);

    @Select("select count(*) as c from users where name=#{name} and password=#{password}")
    Boolean userAndPasswordExists(@Param("name") String name, @Param("password") String password);

}