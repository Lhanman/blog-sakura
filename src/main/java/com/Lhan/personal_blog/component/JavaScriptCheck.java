package com.Lhan.personal_blog.component;

public class JavaScriptCheck {

    public static String javaScriptCheck(String comment)
    {
        comment = testCheck(comment,"script");
        comment = testCheck(comment,"iframe");

        return comment;


    }

    private static String testCheck(String comment,String sign)
    {
        String signFir = "<" +sign;
        String signSec = "</" +sign +">";
        int begin,end,theEnd;
        String newStr="";
        begin = comment.indexOf(signFir);
        end = comment.indexOf(signSec);

        if (begin == -1)
        {
            return comment;
        }
        while (begin != -1)
        {
            theEnd = comment.indexOf(">");
            newStr += comment.substring(0,begin);
            newStr += "[removed]" +comment.substring(theEnd+1,end) +"[removed]";

            comment = comment.substring(end+9);

            begin = comment.indexOf(signFir);
            end = comment.indexOf(signSec);

        }
        return newStr;

    }
}
