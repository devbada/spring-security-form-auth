package io.security.basicsecurity.admin.domain.dto;

import io.security.basicsecurity.admin.domain.entity.Account;
import io.security.basicsecurity.security.entity.Resources;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @since       2022.04.07
 * @author      minam
 * @description role dto
 **********************************************************************************************************************/
 
public class RoleDto {

    public static class Request {
    
//        @Setter
//        @Getter
//        @ToString
//        @Builder
//        @AllArgsConstructor
//        @NoArgsConstructor
//        public static class Find {
//
//        }
//
//        @Setter
//        @Getter
//        @ToString
//        @Builder(toBuilder = true)
//        @AllArgsConstructor
//        @NoArgsConstructor
//        public static class Add {
//
//        }
//
//        @Setter
//        @Getter
//        @ToString
//        @Builder(toBuilder = true)
//        @AllArgsConstructor
//        @NoArgsConstructor
//        public static class Modify {
//
//        }
    }
    
    public static class Response {
    
        @Data
        public static class FindAll {
            private Long id;
            private String roleName;
            private String roleDesc;
            private Set<Resources> resourcesSet;
            private Set<Account> accounts;
        }

        @Data
        public static class FindOne {
            private Long id;
            private String roleName;
            private String roleDesc;
            private Set<Resources> resourcesSet;
            private Set<Account> accounts;
        }
    }
}