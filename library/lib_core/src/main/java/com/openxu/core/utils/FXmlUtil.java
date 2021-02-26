package com.openxu.core.utils;

import android.text.TextUtils;


/**
 * autour : openXu
 * date : 2016/7/24 14:12
 * className : FontUtil
 * version : 1.0
 * description : xml格式字符串相关工具
 *
 *
 *
 */
public class FXmlUtil {

    /**
     * 将服务器返回的xml格式中转义字符还原为真实字符
     * 转换前：&lt;div style=&quot;color: yellow&quot;&gt;暗示&lt;/div&gt;
     * 转换后：<div style='color: yellow'>暗示</div>
     * @param xmlStr
     * @return
     */
    public static String escapeCharacterXml(String xmlStr){
        if(TextUtils.isEmpty(xmlStr))
            return xmlStr;

//        FLog.w("xml转义前："+xmlStr);
        if(xmlStr.contains("&amp;")){
            xmlStr = xmlStr.replaceAll("&amp;", "&");
        }
        if(xmlStr.contains("&apos;")){
            xmlStr = xmlStr.replaceAll("&apos;", "'");
        }
        if(xmlStr.contains("&lt;")){
            xmlStr = xmlStr.replaceAll("&lt;", "<");
        }
        if(xmlStr.contains("&gt;")){
            xmlStr = xmlStr.replaceAll("&gt;", ">");
        }
        if(xmlStr.contains("&quot;")){
            xmlStr = xmlStr.replaceAll("&quot;", "'");
        }
        if(xmlStr.contains("&nbsp;")){
            xmlStr = xmlStr.replaceAll("&nbsp;", " ");
        }
//        FLog.i("xml转义后："+xmlStr);
        return xmlStr;
    }


}


