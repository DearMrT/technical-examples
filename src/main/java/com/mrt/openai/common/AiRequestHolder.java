package com.mrt.openai.common;

import com.mrt.openai.bean.BaseChatCompletion;
import com.mrt.sse.listener.AbstractStreamEventSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Mr.T
 * @Date: 2024/5/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiRequestHolder {

    private BaseChatCompletion chatCompletion;

    private AbstractStreamEventSource streamEventSource;

    private String key;


}
