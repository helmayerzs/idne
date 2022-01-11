package hu.idne.backend.utils.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CommonClassUtils {

    private static String fullLevel = "";
    private static String actLevel = "";

    private CommonClassUtils(){}


    public static List<String> getFieldsList(Object o) {

        List<String> result = new ArrayList<>();
        Map<String,Object> res = null;
        try {
            res = new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(o), HashMap.class);
            hashMapper(res, result);
        } catch (JsonProcessingException | ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<String> hashMapper(Map<String, Object> lhm1, List<String> res) throws ParseException {
        for (Map.Entry<String, Object> entry : lhm1.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                actLevel = key + ".";
                fullLevel +=  key + ".";
                Map<String, Object> subMap = (Map<String, Object>)value;
                hashMapper(subMap, res);
            }else{
                res.add(fullLevel + key);
            }
        }
        actLevel = fullLevel.replace(actLevel, "");
        fullLevel = actLevel;
        return res;
    }
}
