
import org.junit.jupiter.api.Test;
import com.example.*;
import static org.junit.jupiter.api.Assertions.*;

class ConversionContractTest {

    private final Conversion conv = new Conversion();

    // Pós-condição (alfabeto romano)
    private static boolean isRomanAlphabet(String s) {
        return s.matches("^[MDCLXVI]+$");
    }

    // Pós-condição (forma canônica 1..3999)
    private static final String ROMAN_CANONICAL_REGEX =
            "^M{0,3}(CM|CD|D?C{0,3})"
          + "(XC|XL|L?X{0,3})"
          + "(IX|IV|V?I{0,3})$";

    private static boolean isCanonicalRoman(String s) {
        return s.matches(ROMAN_CANONICAL_REGEX);
    }

    // ----------------------------
    // CONTRATO: Pré-condição
    // ----------------------------
    @Test
    void precondition_rejectsOutOfRange() {
        // O encoder foi implementado para 1..3999; fora disso estoura índice
       // assertThrows(ArrayIndexOutOfBoundsException.class, () -> conv.solution(0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> conv.solution(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> conv.solution(4000));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> conv.solution(Integer.MIN_VALUE));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> conv.solution(Integer.MAX_VALUE));
    }

    // ----------------------------
    // CONTRATO: Pós-condições (alfabeto e canonicidade)
    // ----------------------------
    @Test
    void postcondition_alphabetAndCanonical_selected() {
        int[] samples = {1, 2, 3, 4, 5, 6, 9, 14, 19, 40, 44, 49, 58, 90, 99, 400, 444, 944, 1453, 1984, 2421, 3999};
        for (int n : samples) {
            String r = conv.solution(n);
            assertTrue(isRomanAlphabet(r), "Alfabeto inválido: n=" + n + " r=" + r);
            assertTrue(isCanonicalRoman(r), "Não canônico: n=" + n + " r=" + r);
        }
    }

    // ----------------------------
    // CONTRATO: Casos ouro e subtrativas
    // ----------------------------
    @Test
    void knownPairs_andSubtractivePairs() {
        assertEquals("I", conv.solution(1));
        assertEquals("III", conv.solution(3));
        assertEquals("IV", conv.solution(4));
        assertEquals("V", conv.solution(5));
        assertEquals("VI", conv.solution(6));
        assertEquals("IX", conv.solution(9));
        assertEquals("LVIII", conv.solution(58));      
        assertEquals("MCMXCIV", conv.solution(1994));  
        assertEquals("MMMCMXCIX", conv.solution(3999));

        // Subtrativas válidas
        assertEquals("IV", conv.solution(4));
        assertEquals("IX", conv.solution(9));
        assertEquals("XL", conv.solution(40));
        assertEquals("XC", conv.solution(90));
        assertEquals("CD", conv.solution(400));
        assertEquals("CM", conv.solution(900));
    }

    // ----------------------------
    // CONTRATO: Limites do domínio
    // ----------------------------
    @Test
    void boundaries_minAndMax() {
        assertEquals("I", conv.solution(1));
        assertEquals("MMMCMXCIX", conv.solution(3999));
    }

    // ----------------------------
    // CONTRATO: Pureza (mesma entrada → mesma saída)
    // ----------------------------
    @Test
    void purity_sameInputSameOutput() {
        for (int n : new int[]{1, 4, 9, 58, 1994, 3999}) {
            String a = conv.solution(n);
            String b = conv.solution(n);
            assertEquals(a, b, "Mesma entrada deve produzir mesma saída");
        }
    }
}
