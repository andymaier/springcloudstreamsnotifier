package de.amitc.springcloudstreamsnotifier.messaging;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HangoutsService {

    @Value("${chat.url}")
    String chaturl;

    @Value("${chat.key}")
    String key;

    @Value("${chat.token}")
    String token;

    @Autowired
    RestTemplate restTemplate;

    public void sendMessageToHangout(String message) { 

        OutputStream outputStream = null;
        try {
            URL obj = new URL(chaturl);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "";

            // Send post request
            con.setDoOutput(true);
            outputStream = con.getOutputStream();

            String str =  "{\"text\": \""+message+"\"}";
            outputStream.write(str.getBytes());
            DataOutputStream wr = new DataOutputStream(outputStream);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println(chaturl);                
                System.out.println("Send message to Hangouts: " + message);
            } else {
                System.out.println(con.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToChat(String message) {        
        String content = "{\"text\": \""+message+"\"}";

        //TextMessage tm = new TextMessage();
        //tm.setText(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.ACCEPT,MediaType.ALL_VALUE);
        headers.set(HttpHeaders.USER_AGENT, "insomnia/7.0.3");
        headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.length()));
        HttpEntity entity = new HttpEntity<String>(content, headers);    

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(chaturl)
        .queryParam("key", key)
        .queryParam("token", token);
        String url = builder.build(true).toUriString();
       
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, entity, String.class);    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("StatusCode: " + response.getStatusCode());
    }
}

class TextMessage {
    String text;

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}