// src/test/java/ConversionOracleTest.java
import com.example.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConversionOracleTest {

    private final Conversion conv = new Conversion();

    // ----------------------------
    // Oráculo derivado: decoder (roman -> int)
    // ----------------------------
    private static int decodeRoman(String s) {
        int total = 0;
        int i = 0;
        while (i < s.length()) {
            int v1 = valueOf(s.charAt(i));
            if (i + 1 < s.length()) {
                int v2 = valueOf(s.charAt(i + 1));
                if (v1 < v2) {
                    total += (v2 - v1);
                    i += 2;
                    continue;
                }
            }
            total += v1;
            i++;
        }
        return total;
    }

    // Java 11: switch "clássico"
    private static int valueOf(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new IllegalArgumentException("Invalid roman char: " + c);
        }
    }

    // ----------------------------
    // Round-trip (decode(encode(n)) == n) — 1..3999
    // ----------------------------
    @Test
    void roundTrip_wholeDomain() {
        for (int n = 1; n <= 3999; n++) {
            String r = conv.solution(n);
            assertEquals(n, decodeRoman(r), "Round-trip falhou: n=" + n + " r=" + r);
        }
    }

    // ----------------------------
    // Metamorphic: concatenação segura (decode(encode(a)+encode(b)) == a+b)
    // Obs.: mesmo que a concatenação não seja canônica, o decoder soma corretamente.
    // ----------------------------
    @Test
    void metamorphic_decodeConcatEqualsSum_onSafePairs() {
        int[][] pairs = {
                {10, 3},    // 23 => "XX"+"III" -> "XXIII"
                {20, 8},    // 28
                {30, 2},    // 32
                {200, 50},  // 250
                {300, 30},  // 330
                {1000, 900},// 1900
                {1900, 99}, // 1999
                {2000, 34}, // 2034
        };
        for (int[] p : pairs) {
            int a = p[0], b = p[1];
            String ra = conv.solution(a);
            String rb = conv.solution(b);
            int decoded = decodeRoman(ra + rb);
            assertEquals(a + b, decoded, "Concat-decoding falhou para a=" + a + " b=" + b + " ra=" + ra + " rb=" + rb);
        }
    }

    // ----------------------------
    // Pequena tabela ouro (sanity check)
    // ----------------------------
    @Test
    void goldTable_smallSet() {
        int[] inputs   = {1, 2, 3, 4, 9, 19, 40, 44, 49, 58, 90, 99, 400, 444, 944, 1994, 2421, 3999};
        String[] golds = {"I","II","III","IV","IX","XIX","XL","XLIV","XLIX","LVIII","XC","XCIX","CD","CDXLIV","CMXLIV","MCMXCIV","MMCDXXI","MMMCMXCIX"};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(golds[i], conv.solution(inputs[i]), "Gold pair divergiu em n=" + inputs[i]);
        }
    }
}
