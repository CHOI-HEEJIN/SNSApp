package co.kr.newp.core;

/**
 * 속성이 정의 된 공통 엑티비티
 */
public interface ConfigurationActivity {
    /**
     * 속성을 추출한다.
     *
     * @param P 추출 할 대상 속성 명칭
     * @return java.lang.Object 추출 된 속성
     */
    Object getProperty(String P);

    /**
     * 타입이 변환 된 속성을 반환한다.
     *
     * @param P java.lang.Object 추출 된 속성
     * @param R java.lang.Class 속성을 변환 할 타입
     * @return
     */
    Object getProperty(String P, Class<?> R);
}
