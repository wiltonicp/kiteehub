package com.kiteehub.common.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TestToken {

    public static void main(String[] args) throws ParseException {
        //定义密文:用户数据，包含手机号和身份证号
        String MiW = "{\"mobile\":\"18085475002\",\"sfzh\":\"522729199709190338\"}";

        //使用混淆方法加密，得到用户加密字符串
        String MW = CommonEncrypt.mixMw(CommonEncrypt.encrypt("040f967e0cf897a51f2fd0440976bc61f8744805454448ba8627997479f3caa514b7b5eb29af841738f6fae15491e1e44ee6e3e9969f02eaa7338b0085c31e1332", MiW));

        //定义日期对象
        Date date = new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        //date = sdf.parse("202401251653");

        //日期对象转日历对象
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);

        //获取日期对象毫秒数
        long startMillis = startCalendar.getTimeInMillis();
        //日期毫秒数转字符串，进行密文拼接
        String timeString = String.valueOf(startMillis);
        String idToken_simple = CommonEncrypt.encrypt("040f967e0cf897a51f2fd0440976bc61f8744805454448ba8627997479f3caa514b7b5eb29af841738f6fae15491e1e44ee6e3e9969f02eaa7338b0085c31e1332", timeString);

        //用户加密数据和日期加密数据拼接
        String idToken = MW + "DateValidIdentify" + idToken_simple;
        System.out.println(idToken);

        //输出解密结果
        System.out.println(CommonEncrypt.decrypts("d46a1d35da26f777f6a1172ef22bb6ac61c315b981a8d788bfe69b3ef6a62d80",idToken));
    }
}
