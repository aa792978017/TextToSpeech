/*
 * Copyright 2019-2022 the original author or authors.
 */
package com.ai.dao;
import com.ai.pojo.JobInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * . 文字转语音任务信息持久层
 *
 * @author XiaoWangNoBug
 * @since 2022-12-22-9:05
 */
@Mapper
public interface JobDAO {

  @Insert("insert into job_info"
      + "(ip, textarea, file_name, time) "
      + "values"
      + "(#{jobInfo.ip}, #{jobInfo.textarea}, #{jobInfo.fileName}, #{jobInfo.time})")
  @SelectKey(keyColumn = "id", keyProperty = "id", resultType = int.class,
      before = false, statement = "select last_insert_id()")
  int insertJobInfo(@Param("jobInfo") JobInfo jobInfo);

}
