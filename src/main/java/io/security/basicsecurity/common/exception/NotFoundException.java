package io.security.basicsecurity.common.exception;

import io.security.basicsecurity.common.exception.code.ExceptionCode;
import lombok.Getter;

/**
 * @since       2022.04.07
 * @author      minam
 * @description not found exception
 **********************************************************************************************************************/
@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

    @Getter
    public ExceptionCode code;

    public NotFoundException(){
        super(ExceptionCode.E00010002.name());
        code = ExceptionCode.E00010002;
    }

    public NotFoundException(ExceptionCode exceptionCode){
        super(exceptionCode.name());
        code = exceptionCode;
    }

    public NotFoundException(ExceptionCode exceptionCode, Exception exception){
        super(exceptionCode.name(), exception);
        code = exceptionCode;
    }
}
