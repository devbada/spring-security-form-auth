package io.security.basicsecurity.security.factory.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @since       2022.04.13
 * @author      minam
 * @description resource type
 **********************************************************************************************************************/

@Getter
@AllArgsConstructor
public enum ResourceType {

     METHOD   ("method")
    ,POINT_CUT("pointcut")
    ;

    private String value;
}