package com.mindedu.api.mapper;

import org.apache.ibatis.annotations.Param;

public interface AuthMapper {

    Integer selectAuthKey(@Param("authKey") String authKey);

}
