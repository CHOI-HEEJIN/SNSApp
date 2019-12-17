package co.kr.newp.core;

import java.util.Set;

/**
 * 공유 될 리소스 파일 및 속성을
 * 추출 하여 해당 클래스로 정릐 된 값을 주입
 * @Auth Justin-Lee
 */
public interface ShareablePreferences {

    String $PREF_NAME = "co.kr.apphow.bohum";

    String $PREF_INTRO_USER_AGREEMENT = "PREF_USER_AGREEMENT";
    String $PREF_MAIN_VALUE = "PREF_MAIN_VALUE";

    void put(String key, String value) ;

    void put(String key, Set<String> value) ;

    void put(String key, boolean value) ;

    void put(String key, int value) ;

    String getValue(String key);

    String getValue(String key, String dftValue) ;

    Set<String> getValue(String key, Set<String> dftValue) ;

    int getValue(String key, int dftValue);

    boolean getValue(String key, boolean dftValue);

    void remove(String key);
}
