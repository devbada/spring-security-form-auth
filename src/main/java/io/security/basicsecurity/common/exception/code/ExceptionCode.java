package io.security.basicsecurity.common.exception.code;

/**
 * @since       2022.04.07
 * @author      minam
 * @description exception code
 **********************************************************************************************************************/
public enum ExceptionCode {

	/**
	 * E0001xxxx : 공통
	 ******************************************************************************************************************/
	 E00010001  // 유효성 검사에 실패하였습니다.
	,E00010002  // 해당 정보가 없습니다.
	,E00010003  // 해당 요청은 더이상 불가능합니다.
	,E00010004  // 해당 요청을 수락 할 수 없습니다.
	,E00010005  // 이미 사용되고 있는 정보입니다.
	,E00010006  // 예상하지 못한 에러입니다.
	,E00010007  // 구현되지 않은 기능입니다.
	,E00010101  // 비정상적인 접근입니다.
	,E00010201  // Crypto 예외가 발생하였습니다.
	,E00010202  // 암호화 중 예외가 발생하였습니다.
	,E00010203  // 복호화 중 예외가 발생하였습니다.
}