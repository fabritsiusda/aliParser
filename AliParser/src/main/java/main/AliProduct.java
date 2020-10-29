package main;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Objects;

public class AliProduct {

    private int phase;
    private String oriMaxPrice;
    private long productId;
    private String discount;
    private long itemEvalTotalNum;
    private long gmtCreate;
    private boolean soldout;
    private long promotionId;
    private String productTitle;
    private String productDetailUrl;
    private String trace;
    private long sellerId;
    private String productImage;
    private String oriMinPrice;
    private String minPrice;
    private String totalStock;
    private long startTime;
    private String orders;
    private long endTime;
    private String maxPrice;
    private String productPositiveRate;
    private String productAverageStar;
    private String stock;
    private String totalTranpro3;

    public AliProduct(JSONObject obj) {
        obj.keySet().forEach(key -> {
            try {
                Field field = this.getClass().getDeclaredField(key);
                field.setAccessible(true);
                field.set(this, obj.get(key));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static String getFieldsName(){
        StringBuilder builder = new StringBuilder();
        Field[] fields = AliProduct.class.getDeclaredFields();
        for (Field field : fields){
            builder.append(field.getName()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public String getFields(){
        StringBuilder builder = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields){
            try {
                builder.append(field.get(this)).append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliProduct that = (AliProduct) o;
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
