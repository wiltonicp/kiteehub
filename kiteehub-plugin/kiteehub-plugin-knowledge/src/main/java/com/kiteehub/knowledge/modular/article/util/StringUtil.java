package com.kiteehub.knowledge.modular.article.util;

import org.jsoup.Jsoup;

/**
 * 补充工具
 *
 * @author Ranger
 * @date  2024/01/04 09:54
 **/
public class StringUtil {

    public static String truncateString(String input,int num) {
        String text = Jsoup.parse(input).text();
        if (text.length() <= num) {
            return text;
        } else {
            return text.substring(0, num);
        }
    }
}
