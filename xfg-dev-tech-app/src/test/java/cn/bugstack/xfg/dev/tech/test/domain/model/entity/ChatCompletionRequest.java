package cn.bugstack.xfg.dev.tech.test.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionRequest {

    private String model;
    @JsonProperty("top_p")
    private Double topP = 1d;
    @JsonProperty("max_tokens")
    private Integer maxTokens = 2048;

}
