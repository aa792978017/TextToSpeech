/*
 * Copyright 2019-2022 the original author or authors.
 */
package com.ai.dao;
import com.ai.domain.entity.JobHistory;
import org.apache.ibatis.annotations.*;

/**
 * . 文字转语音任务信息持久层
 *
 * @author XiaoWangNoBug
 * @since 2022-12-22-9:05
 */
@Mapper
public interface JobHistoryDAO {

  @Insert("insert into tb_job_history"
      + "(ip, language, voice_name, role, style, rate, pitch, voice_quality, textarea, file_name, time, job_status) "
      + "values"
      + "(#{job.ip}, #{job.language}, #{job.voiceName}, #{job.role}, #{job.style}, #{job.rate}, #{job.pitch}, " +
          "#{job.voiceQuality}, #{job.textarea}, #{job.fileName}, #{job.time}, #{job.jobStatus})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insertJobHistory(@Param("job") JobHistory jobHistory);

  @Select("SELECT * FROM tb_job_history"
          + " WHERE language=#{job.language} AND voice_name=#{job.voiceName} AND role=#{job.role} AND style=#{job.style}"
          + " AND rate=#{job.rate} AND pitch=#{job.pitch} AND voice_quality=#{job.voiceQuality} AND textarea=#{job.textarea} "
          + " AND job_status = true" +
          " ORDER BY `time` DESC LIMIT 1")
  JobHistory querySameConfigJobHistory(@Param("job")JobHistory jobHistory);


  @Update("UPDATE tb_job_history "
          + "SET file_name = #{job.fileName}, job_status = #{job.jobStatus} "
          + "WHERE id = #{job.id}")
  int updateJobHistory(@Param("job") JobHistory jobHistory);










}
