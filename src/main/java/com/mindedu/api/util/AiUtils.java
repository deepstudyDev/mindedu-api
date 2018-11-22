package com.mindedu.api.util;


// Java code to convert
// text to speech
import java.io.InputStream; // import  classes from Java library
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javazoom.jl.player.Player;

public class AiUtils {

    private static String ENCODING = "UTF-8"; //make constants and assign values to them
    private static String URL_BEGINNING = "http://translate.google.com/translate_tts?ie=";
    private static String URL_QUERY = "&q=";
    private static String URL_TL = "&tl=";
    private static String USER_AGENT_LITERAL = "User-Agent";
    private static String USER_AGENT = "Mozilla/4.7";

    public void say( String phrase, String lang ) {
        try {
            //Make full URL
            phrase=URLEncoder.encode(phrase, ENCODING); //assign value to variable with name 'phrase' by use method encode from class URLEncoder
           // String sURL = URL_BEGINNING + ENCODING + URL_TL + lang + URL_QUERY + phrase; //assign value to variable sURL
            String sURL = URL_BEGINNING + ENCODING + "&total=1&idx=0&textlen=32&client=tw-ob" + URL_QUERY + phrase + URL_TL + lang;
            URL url = new URL(sURL); // make instance url with constructor

            //Create connection
            URLConnection urlConn = url.openConnection(); //assign value to variable urlConn
            HttpURLConnection httpUrlConn = (HttpURLConnection) urlConn; //Declaring  httpUrlConn var of type HttpURLConnection, assigning it  value to  var urlConn (reduce to  HttpURLConnection)
            httpUrlConn.addRequestProperty(USER_AGENT_LITERAL, USER_AGENT);// use method

            //Create stream
            InputStream mp3WebStream = urlConn.getInputStream();//create instance and assign it a value

            //Play stream
            Player plr = new Player(mp3WebStream); //create instance plr with constructor
            plr.play(); //use method
        }
        //use exception with name ex
        catch (Exception ex) {
            ex.printStackTrace(); //use method
        }
    }

    public static void main(String[] args) {
        AiUtils aiUtils = new AiUtils();
        aiUtils.say("테스트야 나랑 게임하자", "ko-kr");
        aiUtils.say("6 곱하기 6 은?", "ko-kr");
        aiUtils.say("땡 틀렸어", "ko-kr");
        aiUtils.say("너 멍청하구나", "ko-kr");
        aiUtils.say("모히또가서 몰디브 한잔허자", "ko-kr");
    }
}
