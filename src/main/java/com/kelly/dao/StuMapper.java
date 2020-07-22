package com.kelly.dao;

import com.kelly.model.Stu;

public interface StuMapper {
    int insert(Stu record);

    int insertSelective(Stu record);

    int selectCount();
}