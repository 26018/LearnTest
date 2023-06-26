package top.fixyou;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lsk
 * @description
 * @date 2023/4/19 17:28
 */

public class CreateTableTest {

    public static void main(String[] args) {

//        BigDecimal bigDecimal = new BigDecimal("-2.003750834278914E7");
//        System.out.println(Double.parseDouble(bigDecimal.toPlainString()));
//        ArrayList<SuperMapResource> list = new ArrayList<>();
//
//        ArrayList<String> lists = new ArrayList<>();
//        List<String> strings = lists.stream().filter(s -> list.stream().map(SuperMapResource::getName).noneMatch(l -> l.endsWith(s))).collect(Collectors.toList());
//        System.out.println("世界地图_Day".matches("[\u4e00-\u9fa5]+"));
//        System.out.println("世界地图_".matches("[\u4e00-\u9fa5]+"));


//        String str = "zhongwen";
        String str = "中文";
//        String str = "中文zhongwen";

//        String chars = "abcd";
//        System.out.println(chars.matches("[a-d]g"));

        String reg = "[\u4e00-\u9fa5]";
        System.out.println(str.matches(reg));
        System.out.println(isContainsChinese(str,reg));
    }
    public static boolean isContainsChinese(String str,String reg) {
        if (str == null) {
            return false;
        }
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        System.out.println(m.matches());
        return m.find();
    }
}
