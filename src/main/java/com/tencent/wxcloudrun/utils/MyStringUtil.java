package com.tencent.wxcloudrun.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MyStringUtil {


        /**
         * list 转换成 jsonarray
         * @param res
         * @return
         */
        public static JSONArray listtoJson(List<Map<String, Object>> res){
            JSONArray ret = new JSONArray();
            for (int i = 0; i < res.size(); i++) {
                Map<String, Object> item = res.get(i);
                JSONObject jobj = new JSONObject();
                for(String key:item.keySet()){
                    Object childitem = item.get(key);
                    if(childitem instanceof HashMap){
                        @SuppressWarnings("unchecked")
                        Map<String, Object> rn = (Map<String, Object>) childitem;
                        jobj.put(key,MyStringUtil.maptoJson(rn));
                    }else if(childitem instanceof ArrayList){
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> rn = (List<Map<String, Object>>) childitem;
                        jobj.put(key,listtoJson(rn));
                    }else if(childitem instanceof String){
                        jobj.put(key, childitem);
                    }
                }
                ret.add(jobj);
            }
            return ret;
        }

        /**
         * map 转换成jsonobject
         * @param res
         * @return
         */
        public static JSONObject maptoJson(Map<String, Object> res){
            JSONObject ret = new JSONObject();
            for(String key:res.keySet()){
                Object childitem = res.get(key);
                if(childitem instanceof HashMap){
                    @SuppressWarnings("unchecked")
                    Map<String, Object> rn = (Map<String, Object>) childitem;
                    ret.put(key,MyStringUtil.maptoJson(rn));
                }else if(childitem instanceof ArrayList){
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> rn = (List<Map<String, Object>>) childitem;
                    ret.put(key,MyStringUtil.listtoJson(rn));
                }else if(childitem instanceof String){
                    ret.put(key, childitem);
                }
            }
            return ret;
        }

        /**
         * 将数组整合成字符串
         *
         * @param strArr
         *            targetString数组
         * @param split
         *            分隔符
         * @return 组合结果
         */
        public  String join(String[] strArr, String split) {
            if (strArr == null)
                return "";
            String _str = "";
            for (int i = 0; i < strArr.length; i++) {
                if (_str.equals(""))
                    _str = strArr[i];
                else
                    _str = String.format("%s%s%s", _str, split, strArr[i]);
            }
            return _str;
        }

        /**
         * 用特定字符填充字符串
         *
         * @param sSrc
         *            要填充的字符串
         * @param ch
         *            用于填充的特定字符
         * @param nLen
         *            要填充到的长度
         * @param bLeft
         *            要填充的方向：true:左边；false:右边
         * @return 填充好的字符串
         */
        public  String fill(String sSrc, char ch, int nLen, boolean bLeft) {
            byte[] bTmp = trimnull(sSrc.getBytes());
            sSrc = new String(bTmp);
            if (sSrc == null || sSrc.equals("")) {
                StringBuffer sbRet = new StringBuffer();
                for (int i = 0; i < nLen; i++)
                    sbRet.append(ch);

                return sbRet.toString();
            }
            byte[] bySrc = sSrc.getBytes();
            int nSrcLen = bySrc.length;
            if (nSrcLen >= nLen) {
                return sSrc;
            }
            byte[] byRet = new byte[nLen];
            if (bLeft) {
                for (int i = 0, n = nLen - nSrcLen; i < n; i++)
                    byRet[i] = (byte) ch;
                for (int i = nLen - nSrcLen, n = nLen; i < n; i++)
                    byRet[i] = bySrc[i - nLen + nSrcLen];
            } else {
                for (int i = 0, n = nSrcLen; i < n; i++)
                    byRet[i] = bySrc[i];
                for (int i = nSrcLen, n = nLen; i < n; i++)
                    byRet[i] = (byte) ch;
            }
            return new String(byRet);
        }

        /**
         * 去掉字符串两头的空值
         *
         * @param byRet
         *            要去除的字符串
         * @return 去除好的字符串
         */
        public  byte[] trimnull(byte[] byRet) {
            int startPos = 0;
            int endPos = byRet.length - 1;
            for (int i = 0; i < byRet.length; i++) {
                if (byRet[i] != 0) {
                    startPos = i;
                    break;
                }
                if (i == (byRet.length - 1) && byRet[i] == 0) {
                    return null;
                }
            }
            for (int i = byRet.length - 1; i >= 0; i--) {
                if (byRet[i] != 0) {
                    endPos = i;
                    break;
                }
            }
            byte[] byTmp = new byte[endPos - startPos + 1];
            System.arraycopy(byRet, startPos, byTmp, 0, endPos - startPos + 1);
            return byTmp;
        }

        /**
         * 如果字符串为null或者空格那么返回真
         *
         * @param str
         *            要判断的字符串
         * @return 是否符合
         */
        public  boolean isNullOrWhiteSpace(String str) {
            if (isNullOrEmpty(str))
                return true;

            if (str.trim().isEmpty())
                return true;

            return false;
        }

        /**
         * 如果字符串为null或者空字符串那么返回真
         *
         * @param str
         *            要判断的字符串
         * @return 是否符合
         */
        public static boolean isNullOrEmpty(String str) {
            if (str == null)
                return true;

            if (str.isEmpty())
                return true;

            return false;
        }

        /**
         * 不区分大小写比较字符串
         *
         * @param str1
         *            字符串1
         * @param str2
         *            字符串2
         * @return true-相同；false-不同
         */
        public  boolean equalsNotCL(String str1, String str2) {
            if (str1.regionMatches(true, 0, str2, 0, str2.length())) {
                return true;
            } else {
                return false;
            }

        }

        /**
         * 比较字符串A出现key的index是否在字符串B的index同位置也出现
         * 如字符串A为"0001001";字符串B为"1101001";key为'1'返回true；key为'0'返回false
         *
         * @param stringA
         *            字符串A
         * @param stringB
         *            字符串B
         * @return true-有相同；false-无相同
         */
        public  boolean Check2SKeyAtSameIndex(String stringA, String stringB,
                                                    char key) {
            boolean flag = false;
            char[] typesA = stringA.toCharArray();
            char[] typesB = stringB.toCharArray();
            int count = 0;
            for (int i = 0; i < typesA.length; i++) {
                if (typesA[i] == key) {
                    flag = true;
                    flag = flag && (typesB[i] == key);
                    count++;
                }
            }
            if (count == 0) {
                flag = true;
            }
            return flag;
        }

        /**
         * 检查字符串A按照split切分后的字符串是否包含在字符串B被split切分后的数组中
         *
         * @param stringA
         *            字符串A
         * @param stringB
         *            字符串B
         * @param split
         *            分隔符
         * @return true-包含；false-不包含
         */
        public  boolean CheckBcontentA(String stringA, String stringB,
                                             String split) {
            boolean flag = false;

            String[] typesA = stringA.split(split);
            String[] typesB = stringB.split(split);
            int count = 0;
            for (int i = 0; i < typesA.length; i++) {
                for (int j = 0; j < typesB.length; j++) {
                    if (typesA[i].equals(typesB[j])) {
                        flag = true;
                        flag = flag && (typesA[i].equals(typesB[j]));
                        count++;
                    }
                }
            }
            if (count == 0) {
                flag = true;
            }
            return flag;
        }

        /**
         * 将null转化为""，如果不是null，返回object.toString()
         *
         * @param object
         * @return
         */
        public  String convertNullToEmpty(Object object) {
            if (object == null){
                return "";
            }else if("null".equals(String.valueOf(object))){
                return "";
            }else {
                return String.valueOf(object);
            }
        }

        /**
         * 转换String值为int，如果为null，返回-1
         *
         * @param vlaue
         * @return
         */
        public  int getInteger(String vlaue) {
            if (isNullOrEmpty(vlaue)) {
                return -1;
            }
            return Integer.parseInt(vlaue);
        }

        /**
         * 判断字符串是否是Int类型数字
         *
         * @param posValue
         * @return
         */
        public  boolean isInteger(String posValue) {
            try {
                Integer.parseInt(posValue);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        /**
         * 字符串填充方法
         *
         * @param str
         *            需要填充的字符窜
         * @param length
         *            填充后字符串的长度
         * @param c
         *            指定填充的字符
         * @return
         */
        public static   String fillString(String str, int length, char c) {
            String formatterString = null;
            StringBuffer strBuff = new StringBuffer();
            if (str.isEmpty() && "".equals(str)) {
                return str;
            } else {
                if (length >= str.length()) {
                    for (int i = 0; i < length - str.length(); i++) {
                        strBuff.append(c);
                    }
                    formatterString = strBuff.append(str).toString();
                    str.format(formatterString, str);
                    System.out.println(formatterString);
                    return formatterString;
                } else {
                    return str;
                }
            }
        }

        public  String trim(String srcStr) {
            if (null == srcStr) {
                return "";
            }
            return srcStr.trim();
        }

        /**
         * 分割字符串 与String类的split()方法类似，只有在以下条件下不同： 如字符串：String s = "12||3456||789||";
         * 对于String类的split()方法,返回的数据长度为5,而此split()方法则返回6;
         *
         * @param
         *
         * @return
         */
        public  String[] split(String s, String splitor) {
            // 获取字符串长度
            int size = s.length();
            // 字符长度为零，不做处理
            if (size == 0) {
                return new String[0];
            }

            int itemSize = 40;
            String[] list = new String[itemSize];

            // 获取分割符的长度
            int sliptorLen = splitor.length();
            // 开始索引
            int beginIndex = 0;
            // 结束索引
            int endIndex = 0;
            // 索引
            int index = 0;
            do {
                endIndex = s.indexOf(splitor, beginIndex);
                if (endIndex == -1) {
                    endIndex = size;
                }
                String item = s.substring(beginIndex, endIndex);
                if (index == list.length) {
                    String[] temp = list;
                    // 扩展数组
                    list = new String[temp.length + itemSize];
                    // 拷贝内容
                    System.arraycopy(temp, 0, list, 0, temp.length);
                }
                list[index++] = item;
                // 更新开始索引
                beginIndex = endIndex + sliptorLen;
            } while (beginIndex < size);

            // 定义结果数组
            String[] res = new String[index];
            System.arraycopy(list, 0, res, 0, index);
            return res;
        }

        /**
         * 截取字符串中最后一个指定字符的前/后所有字符(不含指定字符)
         *
         * @param str
         *            字符串
         * @param split
         *            指定字符
         * @param length
         *            超过该长度，截取前length长度字符
         * @param bFlag
         *            true-前，false-后
         * @return 返回后的字符串
         */
        public  String submitStr(String str, String split, int length,
                                       boolean bFlag) {
            if (MyStringUtil.isNullOrEmpty(str))
                return str;
            if (str.indexOf(split) == -1)
                return str;
            String strSub = "";
            if (!bFlag) {// 后
                strSub = str.substring(str.lastIndexOf(split) + 1);
            } else {// 前
                strSub = str.substring(0, str.lastIndexOf(split));
            }
            int cl = strSub.length();
            if (cl > length) {
                strSub = str.substring(0, length);
            }
            return strSub;
        }

        /**
         * @author cn
         * @param s
         *            要截取的字符串
         * @param length
         *            要截取字符串的长度->是字节一个汉字2个字节 return 返回length长度的字符串（含汉字）
         */
        public  String chineseSubstring(String s, int length) {
            try {
                byte[] bytes = s.getBytes("GBK");
                int n = 0; // 表示当前的字节数
                int i = 2; // 要截取的字节数，从第3个字节开始
                for (; i < bytes.length && n < length; i++) {
                    // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
                    if (i % 2 == 1) {
                        n++; // 在UCS2第二个字节时n加1
                    } else {
                        // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                        if (bytes[i] != 0) {
                            n++;
                        }
                    }

                }
                // 如果i为奇数时，处理成偶数
                /*
                 * if (i % 2 == 1){ // 该UCS2字符是汉字时，去掉这个截一半的汉字 if (bytes[i - 1] != 0)
                 * i = i - 1; // 该UCS2字符是字母或数字，则保留该字符 else i = i + 1; }
                 */
                // 将截一半的汉字要保留
                if (i % 2 == 1) {
                    i = i + 1;
                }
                return new String(bytes, 0, i, "GBK");
            } catch (Exception e) {
                // TODO: handle exception
                return s;
            }
        }
        /**
         * 检查字符串中是否包含有中文
         * @param str 要检查的字符串
         * @return 如果包含中午，返回true，没有中文，返回false。
         */
        public  boolean checkChinese(String str) {
            if(MyStringUtil.isNullOrEmpty(str)){
                return false;
            }
            String regEx = "[\\u4e00-\\ue864]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            while (m.find()) {
                return true;
            }
            return false;
        }

        /**
         * 检查字符串中是否全为中文
         * @param str 要检查的字符串
         * @return 如果包含中午，返回true，没有中文，返回false。
         */
        public  boolean isAllChinese(String str) {
            if(MyStringUtil.isNullOrEmpty(str)){
                return false;
            }
            char[] ch = str.toCharArray();
            for (char c : ch) {
                if((c < 0x4e00)||(c > 0x9fbb)){
                    return false;
                }
            }
            return true;
        }

        /**
         * 检查拼接sql的字符串是否合法 合法返回true不合法返回false
         * @param object
         * @return
         */
        public  boolean checkPJStr(Object object){
            boolean flag = true;
            try {
                String str = (String) object;
                str = str.trim();
                if(str.indexOf("1=1") >= 0){
                    flag = false;
                }
                if(str.indexOf("|") != -1||str.indexOf("--") != -1){
                    flag = false;
                }
                if(str.toLowerCase().indexOf("case")!=-1||str.toLowerCase().indexOf("delete")!=-1||str.toLowerCase().indexOf("update")!=-1||str.toLowerCase().indexOf("select")!=-1){
                    flag = false;
                }
            } catch (Exception e) {
                flag = false;
            }
            return flag;
        }

        /**
         * 对身份证号进行脱敏。前6位加****加后四位
         * @param certId
         * @return
         */
        public  String tuoMinCertId(String certId){
            if(isNullOrEmpty(certId)){
                return "";
            }else{
                int length = certId.length();
                if(length<=10){
                    return certId;
                }
                return certId.substring(0,6)+"****"+certId.substring(length-4,length);
            }
        }

        /**
         * 对手机号进行脱敏。前3位加****加后四位
         * @param certId
         * @return
         */
        public  String tuoMinPhoneNo(String certId){
            if(isNullOrEmpty(certId)){
                return "";
            }else{
                int length = certId.length();
                if(length<=7){
                    return certId;
                }
                return certId.substring(0,3)+"****"+certId.substring(length-4,length);
            }
        }
        /**
         * 单位由元转为万元，并保留两位小数
         * @param str
         * @return
         */
        public  String amtFormat(String str){
            if(isNullOrEmpty(str)){
                return "";
            }else{
                BigDecimal b= new BigDecimal(str);
                //转换为万元 除以10000
                BigDecimal d=b.divide(new BigDecimal("10000"));
                //保留两位小数
                DecimalFormat fmt= new DecimalFormat("0.00");
                //四舍五入
                fmt.setRoundingMode(RoundingMode.HALF_UP);
                return fmt.format(d);
            }
        }

}
