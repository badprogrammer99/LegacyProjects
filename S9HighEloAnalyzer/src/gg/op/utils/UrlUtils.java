package gg.op.utils;

import gg.op.constants.OPggRegionsUrls;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UrlUtils {
    public static List<String> getListOfOpGgRegionUrls() {
        Class<OPggRegionsUrls> clazz = OPggRegionsUrls.class;
        Field[] fields = clazz.getDeclaredFields();
        List<String> urls = new ArrayList<>();

        for (Field field : fields) {
            try {
                urls.add((String) field.get(clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return urls;
    }
}
