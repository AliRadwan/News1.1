package com.example.wekaradwan.news;

/**
 * Created by weka radwan on 4/28/2018.
 */


/**
 * this Data class contiant the text and data
 */
public class Data {

    /**
     * String resource ID for the  TextView title
     */
    private String title;
    /**
     * String resource ID for the  TextView mInformation
     */
    private String mInformation;
    /**
     * String resource ID for the  TextView date
     */
    private String date;
    /**
     * String resource ID for the  mUrl
     */
    private String mUrl;
    /**
     * String resource ID for the  TextView @mِAuthorName
     */
    private String mِAuthorName;

    /**
     * @param title       that's will have the title text
     * @param information that's will have the information text
     * @param time        that's will have the date text
     * @param url         that's will have the url text
     * @param authorName  that's will have the author text
     */
    public Data(String title, String information, String authorName, String time, String url) {
        this.title = title;
        this.mInformation = information;
        this.date = time;
        this.mUrl = url;
        this.mِAuthorName = authorName;
    }

    /**
     * @return text for the title textView
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return text for the Information textView
     */
    public String getmInformation() {
        return mInformation;
    }

    /**
     * @return text for the Date textView
     */
    public String getDate() {
        return date;
    }

    /**
     * @return text for the Url textView
     */
    public String getmUrl() {
        return mUrl;
    }

    /**
     * @return text for the authorName textView
     */
    public String getAuthorName() {
        return mِAuthorName;
    }

}
