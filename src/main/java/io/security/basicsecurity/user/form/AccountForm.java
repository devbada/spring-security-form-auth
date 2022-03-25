package io.security.basicsecurity.user.form;

import lombok.*;

/**
 * @since       2022.03.24
 * @author      minam
 * @description account form
 **********************************************************************************************************************/
public class AccountForm {

    public static class Request {

        @Setter
        @Getter
        @ToString
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Add {

            private String  name;
            private String  password;
            private String  email;
            private Integer age;
            private String  role;
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
            private String  role;
        }
    }

}