package com.Lhan.personal_blog.util;

/**
 * markdown截取文章生成摘要
 *
 */
public class BuildArticleSummaryUtil {

    public String buildArticleSummary(String htmlArticleContent)
    {
        String regex = "\\s+";
        String str = htmlArticleContent.trim();
        //去掉所有空格
        String articleSummary = str.replaceAll(regex,StringUtil.BLANK);

        int beginIndex = articleSummary.indexOf("<");
        int endIndex = articleSummary.indexOf(">");
        String myArticleSummary = "";
        String nowStr = "";
        while (beginIndex != -1)
        {
            nowStr = articleSummary.substring(0,beginIndex);
            if (nowStr.length() > 66)
            {
                nowStr = nowStr.substring(0,66);
                myArticleSummary +=nowStr;
            }
            else
            {
                myArticleSummary = nowStr;
            }
            articleSummary = articleSummary.substring(endIndex+1);
            beginIndex = articleSummary.indexOf("<");
            if (myArticleSummary.length() < 66)
            {
                //过滤掉<pre>标签中的代码块
                if (articleSummary.length() > 4)
                {
                    if (articleSummary.charAt(beginIndex) == '<'
                            && articleSummary.charAt(beginIndex+1) =='p'
                            && articleSummary.charAt(beginIndex+2) =='r'
                            && articleSummary.charAt(beginIndex+3) =='e')
                    {
                        endIndex = articleSummary.indexOf("</pre>");
                        endIndex = endIndex + 5;
                    }
                    else
                    {
                        endIndex = articleSummary.indexOf(">");
                    }
                }
                else
                {
                    endIndex = articleSummary.indexOf(">");
                }
            }
            else
            {
                break;
            }
        }
        if (myArticleSummary.length() > 66)
        {
            myArticleSummary = myArticleSummary.substring(0,66);
        }
        return myArticleSummary;
    }
}
