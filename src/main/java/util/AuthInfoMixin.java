package util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.UUID;
//这个MixIn用于json读入AuthInfo类
public abstract class AuthInfoMixin {
    @JsonCreator
    public AuthInfoMixin(@JsonProperty("username") String username, @JsonProperty("token") String token, @JsonProperty("uuid") UUID uuid, @JsonProperty("properties") Map<String, String> properties, @JsonProperty("userType") String userType, @JsonProperty("xboxUserId") String xboxUserId){

    }
}
