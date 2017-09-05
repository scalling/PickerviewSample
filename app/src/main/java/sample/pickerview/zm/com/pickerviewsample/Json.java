package sample.pickerview.zm.com.pickerviewsample;

import android.text.TextUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Json {
    private static ObjectMapper mapper = new ObjectMapper();
    public static boolean configure = false;//是否检测


    public static <T> T fromJson(String jsonString, TypeReference<T> tTypeReference) {
        configure();
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) mapper.readValue(jsonString, tTypeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void configure(){
        if(!configure){
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

    }


}
