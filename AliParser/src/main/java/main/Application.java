package main;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Application {

    private static final String URL = "https://gpsfront.aliexpress.com/getRecommendingResults.do";
    private static final int COUNT = 100;
    private static final int STEP = 40;
    private static final int WIDGET_ID = 5547572;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String postback = UUID.randomUUID().toString();
        Set<AliProduct> products = getProducts(COUNT, STEP, WIDGET_ID, URL, postback);
        writeToFile("result", setToCsv(products));
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static String setToCsv(Set<AliProduct> set){
        StringBuilder builder = new StringBuilder(AliProduct.getFieldsName()).append("\n");
        set.stream().map(AliProduct::getFields).forEach(builder::append);
        return builder.toString();
    }

    private static Set<AliProduct> getProducts(int count, int step, int widgetId, String url, String postback) throws IOException {
        Set<AliProduct> products = new HashSet<>();
        for (int i = 0; products.size() < count; i += step) {
            Parser parser = new Parser(step, i, widgetId, postback, url);
            JSONObject json = parser.getJson();
            JSONArray results = json.getJSONArray("results");
            for (Object obj : results){
                products.add(new AliProduct((JSONObject) obj));
                if (products.size() == count) {
                    break;
                }
            }
            if (products.size() == count) {
                break;
            }
        }
        return products;
    }

    private static void writeToFile(String filePath, String content) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(filePath));
        writer.write(content);
        writer.flush();
        writer.close();
    }

}
