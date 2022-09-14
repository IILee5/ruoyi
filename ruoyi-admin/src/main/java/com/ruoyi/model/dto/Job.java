package com.ruoyi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ruoyi.common.constant.MQQueueConstants;
import com.ruoyi.framework.api.annotations.MQTopic;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @author iilee
 */
@MQTopic(queueKey = MQQueueConstants.STREAM_KEY)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job implements Serializable {
    private Integer id;

    private Map<String,Object> param;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;
}
