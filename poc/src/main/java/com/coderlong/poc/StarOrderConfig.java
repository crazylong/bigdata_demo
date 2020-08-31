package com.coderlong.poc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@ConfigurationProperties(prefix="poc")
public class StarOrderConfig {

    private List<StarOrderEntity> starOrderList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StarOrderEntity{
        private String cookie;
        private String bfcToken;
        private String secondPassword;
        private String buyNum;
    }


}
