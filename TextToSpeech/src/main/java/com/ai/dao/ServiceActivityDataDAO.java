package com.ai.dao;

import com.ai.domain.entity.ServiceActivityData;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface ServiceActivityDataDAO {

    @Insert("INSERT INTO tb_service_activity_data"
            + "(id, date, web_visit_count, audition_count, download_count, make_voice_count, word_to_speech_count) "
            + "values"
            + "(#{data.id}, #{data.date}, #{data.webVisitCount}, #{data.auditionCount}, #{data.downloadCount}, " +
            " #{data.makeVoiceCount}, #{data.workToSpeechCount})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = int.class,
            before = false, statement = "select last_insert_id()")
    int insertServiceActivityData(@Param("data") ServiceActivityData data);

    @Update("UPDATE tb_service_activity_data"
            + "SET web_visit_count = #{data.webVisitCount}, audition_count = #{data.auditionCount}, " +
            " download_count = #{data.downloadCount}, make_voice_count = #{data.makeVoiceCount}, " +
            " word_to_speech_count = #{data.workToSpeechCount})" +
            "WHERE id = #{data.id}")
    int updateServiceActivityData(@Param("data") ServiceActivityData data);

    @Select("SELECT * FROM tb_service_activity_data WHERE date > #{date}")
    ServiceActivityData queryServiceActivityDataAfter(@Param("date")Date date);

}
