package top.fixyou.netty.redisclient.util;

import java.util.ArrayList;

/**
 * @author Lsk
 * @description
 * @date 2023/4/10 15:48
 */

public class ResponseParser {

    public static String parse(String[] params) {
        StringBuilder builder = new StringBuilder();

        if (params == null || params.length == 0) {
            return "(null)";
        }

        if (params[0].startsWith("*")) {
            ArrayList<String> temp = new ArrayList<>();
            int size = Integer.parseInt(params[0].substring(1));
            for (int i = 0; i < size; i++) {
                int index = 2 * (i + 1);
                temp.add(params[index]);
            }
            builder.append(temp);
        } else {
            return params[0].substring(1);
        }
        String ret = builder.toString();
        return ret.startsWith("[") && ret.endsWith("]") ? ret.substring(1, ret.length() - 1) : ret;
    }

}
