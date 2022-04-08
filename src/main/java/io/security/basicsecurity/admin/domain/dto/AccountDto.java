package io.security.basicsecurity.admin.domain.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @since       2022.04.07
 * @author      minam
 * @description account dto
 **********************************************************************************************************************/
 
public class AccountDto {
    public static class Request {

        @Setter
        @Getter
        @ToString
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Login {

            private String name;
            private String password;
        }

        @Setter
        @Getter
        @ToString
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Add {

            private String       name;
            private String       password;
            private String       email;
            private Integer      age;
            private List<String> roles;
        }
    }

    public static class Response {

        @Data
        public static class FindOne {

            private Long    id;
            private String  name;
            private String  password;
            private String  email;
            private Integer age;
            private List<Role>  roles;

            @Data
            public static class Role {
                private String roleName;
                private String roleDesc;
            }
        }

        @Data
        public static class FindAll {

            private Long      id;
            private String    name;
            private String    password;
            private String    email;
            private Integer   age;
            private Set<Role> roles;

            @Data
            public static class Role {
                private String roleName;
                private String roleDesc;
            }
        }
    }
}