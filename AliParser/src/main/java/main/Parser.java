package main;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Parser {

    private final String path;
    private String response;

    public Parser(int limit, int offset, int widgetId, String postback, String path) {
        this.path = path + "?" + "limit=" + limit + "&offset=" +
                offset + "&widget_id=" + widgetId + "&postback=" + postback;
    }

    public JSONObject getJson() throws IOException {
        response = read();
        JSONObject object = new JSONObject(response);
        return object;
    }

    private String read() throws IOException {
        URL url = new URL(path);
        URLConnection con = url.openConnection();
        con.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder builder = new StringBuilder();
        reader.lines().forEach(builder::append);
        reader.close();
        return builder.toString();
    }

    public String getResponse() {
        return response;
    }
}
