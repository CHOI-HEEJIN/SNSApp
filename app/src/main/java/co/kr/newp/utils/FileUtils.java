package co.kr.newp.utils;

import java.util.UUID;

import co.kr.newp.SharedPreferencesUtil;
import co.kr.newp.Write;

public class FileUtils {

    /* **********************************************
     * 자음 모음 분리
     * **********************************************/
    /** 초성 */
    public static char[] arrChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
            0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
            0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
    /** 중성 */
    public static char[] arrJungSung = { 0x314f, 0x3150, 0x3151, 0x3152,
            0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a,
            0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162,
            0x3163 };
    /** 종성 */
    public static char[] arrJongSung = { 0x0000, 0x3131, 0x3132, 0x3133,
            0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c,
            0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145,
            0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

    /* **********************************************
     * 알파벳으로 변환
     * 설연수 -> tjfdustn, 멍충 -> ajdcnd
     * **********************************************/
    /** 초성 */
    public static String[] arrChoSungEng = { "r", "R", "s", "e", "E",
            "f", "a", "q", "Q", "t", "T", "d", "w",
            "W", "c", "z", "x", "v", "g" };

    /** 중성 */
    public static String[] arrJungSungEng = { "k", "o", "i", "O",
            "j", "p", "u", "P", "h", "hk", "ho", "hl",
            "y", "n", "nj", "np", "nl", "b", "m", "ml",
            "l" };

    /** 종성 */
    public static String[] arrJongSungEng = { "", "r", "R", "rt",
            "s", "sw", "sg", "e", "f", "fr", "fa", "fq",
            "ft", "fx", "fv", "fg", "a", "q", "qt", "t",
            "T", "d", "w", "c", "z", "x", "v", "g" };

    /** 단일 자음 */
    public static String[] arrSingleJaumEng = { "r", "R", "rt",
            "s", "sw", "sg", "e","E" ,"f", "fr", "fa", "fq",
            "ft", "fx", "fv", "fg", "a", "q","Q", "qt", "t",
            "T", "d", "w", "W", "c", "z", "x", "v", "g" };

    public static String updateEnglish(String word) {

        String result		= "";									// 결과 저장할 변수
        String resultEng	= "";

        for (int i = 0; i < word.length(); i++) {

            /*  read one char */
            char chars = (char) (word.charAt(i) - 0xAC00);

            if (chars >= 0 && chars <= 11172) {

                int chosung 	= chars / (21 * 28);
                int jungsung 	= chars % (21 * 28) / 28;
                int jongsung 	= chars % (21 * 28) % 28;

                result = result + arrChoSung[chosung] + arrJungSung[jungsung];


                /* seperate single head */
                if (jongsung != 0x0000) {

                    result =  result + arrJongSung[jongsung];
                }

                resultEng = resultEng + arrChoSungEng[chosung] + arrJungSungEng[jungsung];
                if (jongsung != 0x0000) {

                    resultEng =  resultEng + arrJongSungEng[jongsung];
                }

            } else {
                /* not exist english */

                result = result + ((char)(chars + 0xAC00));

                if( chars>=34097 && chars<=34126){

                    int jaum 	= (chars-34097);
                    resultEng = resultEng + arrSingleJaumEng[jaum];
                } else if( chars>=34127 && chars<=34147) {

                    int moum 	= (chars-34127);
                    resultEng = resultEng + arrJungSungEng[moum];
                } else {

                    resultEng = resultEng + ((char)(chars + 0xAC00));
                }
            }
        }

        return resultEng;
    }

    public static String getImageNameFormat(String imgName, Write properties) {

        return UUID.randomUUID().toString().substring(32)+"_"+properties.getValue("id", "") + updateEnglish(imgName) ;
    }

}
