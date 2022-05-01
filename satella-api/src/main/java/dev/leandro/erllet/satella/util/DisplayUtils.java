package dev.leandro.erllet.satella.util;

import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class DisplayUtils {

    private DisplayUtils() {
    }

    private static final Pattern TOKEN_PATTERN = Pattern.compile("\\{(.+?)}");


    public static String join(List<String> items) {
        if (items.isEmpty()) {
            return StringUtils.EMPTY;
        }
        val size = items.size();
        if (size == 1) {
            return items.get(0);
        }
        return StringUtils.join(items.subList(0, size - 1), ", ") + " e " + items.get(size - 1);
    }


    public static String replaceTokens(String source, Map<String, String> tokens) {
        return replaceTokens(source, tokens::get);
    }


    public static String replaceTokens(String source, Function<String, String> tokenSupplier) {
        val matcher = TOKEN_PATTERN.matcher(source);
        val buffer = new StringBuffer();
        while (matcher.find()) {
            val replacement = tokenSupplier.apply(matcher.group(1));
            matcher.appendReplacement(buffer, "");
            buffer.append(replacement);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

}
