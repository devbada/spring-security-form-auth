package io.security.basicsecurity.admin.domain.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @since       2022.04.07
 * @author      minam
 * @description resource dto
 **********************************************************************************************************************/
 
public class ResourceDto {

    public static class Request {

        @Setter
        @Getter
        @ToString
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Find {
            String roleName;
        }

        @Setter
        @Getter
        @ToString
        @Builder(toBuilder = true)
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Add {
            private String resourceName;
            private String httpMethod;
            private int    orderNum;
            private String resourceType;
            private List<String> roleName;
        }
    }

    public static class Response {

        @Data
        public static class FindAll {
            private Long id;
            private String resourceName;
            private String httpMethod;
            private int orderNum;
            private String resourceType;
        }

        @Data
        public static class FindOne {
            private Long id;
            private String resourceName;
            private String httpMethod;
            private int orderNum;
            private String resourceType;
            private Set<Role> roleSet;

            @Data
            public static class Role {
                private Long id;
                private String roleName;
                private String roleDesc;
            }
        }
    }
}