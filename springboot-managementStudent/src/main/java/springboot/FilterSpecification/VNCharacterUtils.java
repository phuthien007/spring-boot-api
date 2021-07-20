package springboot.FilterSpecification;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;

public class VNCharacterUtils {
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("")
        .replaceAll("Đ", "D").replace("đ", "d");
    }
}
