package com.mindedu.api.util;

import com.google.common.io.Resources;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mindedu.api.define.datasource.HttpMethod;
import com.mindedu.api.define.error.MindException;
import com.mindedu.api.vo.ApiRequestVO;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiUtils {

    @Autowired
    private ServletContext servletContext;



    static final Logger logger = LoggerFactory.getLogger(ApiUtils.class);

    /**
     * <PRE>
     * 1. Comment : JSON 형식 REST API 콜 메서드
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 06 .27
     * </PRE>
     * @param callUrl(호춣 url)
     * @param httpMethod(GET, POST, ... 정의)
     * @param paramJsonStr(JSON형식 파라미터 스트링)
     * @return
     */
    public static ApiRequestVO callRestApiJson(String callUrl, HttpMethod httpMethod, String paramJsonStr, String cookie) {
        ApiRequestVO apiRequestVO = null;
        HttpResponse response = null;
        HttpGet httpGet;
        HttpPost httpPost;
        HttpPut httpPut;
        HttpDelete httpDelete;

        logger.info("<=============================== api start ===============================>");
        logger.info("call url ==========> " + callUrl);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            switch (httpMethod) {
                case POST :
                    httpPost = new HttpPost(callUrl);
                    StringEntity stringEntity = new StringEntity(paramJsonStr, "UTF-8");
                    httpPost.addHeader("content-type", "application/json");
                    httpPost.addHeader("Cookie", "JSESSIONID=" + cookie);
                    httpPost.setEntity(stringEntity);
                    response = httpClient.execute(httpPost);
                    break;
                case PUT :
                    httpPut = new HttpPut(callUrl);
                    response = httpClient.execute(httpPut);
                    break;
                case DELETE :
                    httpDelete = new HttpDelete(callUrl);
                    response = httpClient.execute(httpDelete);
                    break;
                case GET :
                    httpGet = new HttpGet(callUrl);
                    response = httpClient.execute(httpGet);
                    break;
            }
            String requestBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            int httpCode = response.getStatusLine().getStatusCode();
            logger.info("http code ==================> " + httpCode);

            if (httpCode != 200) {
                throw new MindException(httpCode);
            }
            apiRequestVO = new ApiRequestVO(requestBody, httpCode);
            logger.info("response result ==================> " + apiRequestVO.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiRequestVO;
    }

    /**
     * <PRE>
     * 1. Comment : HTTP POST 파라미터 URL콜
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 06 .27
     * </PRE>
     * @param url
     * @param nameValuePairList
     * @return
     * @throws Exception
     */
    public static ApiRequestVO sendPostParam(String url, List<NameValuePair>nameValuePairList) throws Exception {
        logger.info("<=============================== sendPostParam start ===============================>");
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        logger.info("call url =====================> " + url);

        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, "UTF-8"));
        httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
        HttpResponse httpResponse = httpClient.execute(httpPost);

        int httpCode = httpResponse.getStatusLine().getStatusCode();
        logger.info("http code =====================> " + httpCode);

        HttpEntity httpEntity = httpResponse.getEntity();
        String requestBody = EntityUtils.toString(httpEntity);

        ApiRequestVO apiRequestVO = new ApiRequestVO(requestBody, httpCode);
        logger.info("result =====================> " + apiRequestVO.toString());

        return apiRequestVO;
    }

    public static void main(String[] args) throws Exception{

        //String content = new String(Files.readAllBytes(Paths.get("test.txt")));

        ApiUtils utils = new ApiUtils();
        String s = utils.getFileFromURL();

        System.out.println("Contents (Java 7) : " + s);


        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("language_code", "korean");
        jsonObject2.put("audio", s);

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("access_key", "0ef3aed0-7938-455e-ac76-d0e6bbffbb2e");
        jsonObject3.put("argument", jsonObject2);


        String body = jsonObject3.toString();
        System.out.println(body);
        URL postUrl = new URL("http://aiopen.etri.re.kr:8000/WiseASR/Recognition");
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true); 				// xml내용을 전달하기 위해서 출력 스트림을 사용
        connection.setInstanceFollowRedirects(false);  //Redirect처리 하지 않음
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        OutputStream os= connection.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        //System.out.println("Location: " + connection.getHeaderField("Location"));

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);

            JsonParser parser = new JsonParser();
            JsonElement elem = parser.parse(output);
            JsonObject obj = elem.getAsJsonObject().get("return_object").getAsJsonObject();
            System.out.println("elem :::" + obj.toString());
            JsonElement elem2 = parser.parse(obj.toString());
            String obj2 = elem2.getAsJsonObject().get("recognized").getAsString();
            System.out.println("elem :::" + obj2);

            AiUtils aiUtils = new AiUtils();
            aiUtils.say(obj2, "ko-kr");

        }
        connection.disconnect();
    }

    private String getFileFromURL() throws Exception {
        //URL url = this.getClass().getClassLoader().getResource("/test.txt");
        URL url = Resources.getResource("test.txt");
        String s = Resources.toString(url, Charsets.UTF_8);
        return s;
    }

}
