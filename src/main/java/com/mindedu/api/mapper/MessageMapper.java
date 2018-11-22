package com.mindedu.api.mapper;

import com.mindedu.api.dto.SmsSendListDto;
import com.mindedu.api.vo.SmsSendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {

    void insertSmsSendList(@Param("smsList")List<SmsSendVO>smsSendVOList);

    List<SmsSendListDto>selectSmsSendList(@Param("start")int start, @Param("limit")int limit, @Param("yyyyMM")String yyyyMM);

}
