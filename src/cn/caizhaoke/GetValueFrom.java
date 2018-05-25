package cn.caizhaoke;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/5/23 11:09
 */
public class GetValueFrom {
    public static void main(String[] args) {
        String s = "未知:0.2143,信用卡:0.7143,微信支付:0.0714";
        System.out.println(getValue(s, "信用卡"));
    }

    public static double getValue(String s, String k) {
        String[] strs = s.split("\\,");
        Map<String, BigDecimal> map = new HashMap<>();
        for (String str : strs) {
            String[] ms = str.split(":");
            map.put(ms[0], new BigDecimal(ms[1]));
        }

        return null == map.get(k) ? 0 : map.get(k).doubleValue();
    }

}
