package com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;

/**
 * 图片信息
 *
 * @author WuJiaJun
 */
@Data
public class ImageInfo {
    @JsonProperty("Format")
    @JsonDeserialize(using = ValueWrapperDeserializer.class)
    private String format;

    @JsonProperty("ImageWidth")
    @JsonDeserialize(using = ValueWrapperDeserializer.class)
    private String imageWidth;

    @JsonProperty("ImageHeight")
    @JsonDeserialize(using = ValueWrapperDeserializer.class)
    private String imageHeight;

    @JsonProperty("FileSize")
    @JsonDeserialize(using = ValueWrapperDeserializer.class)
    private String fileSize;
}

class ValueWrapperDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isObject() && node.has("value")) {
            return node.get("value").asText();
        }
        return node.asText(); // 兜底
    }
}