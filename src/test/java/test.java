import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description
 * @Author fengjl
 * @Date 2019/6/25 15:09
 * @Version 1.0
 **/
public class test {
    public static void main(String[] args) {
        String getid=UUID.randomUUID().toString().replace("-", "");

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("deviceId","459432813018562");
        stringObjectHashMap.put("startTime","20190430204238");
        stringObjectHashMap.put("endTime","20190430205238");
        stringObjectHashMap.put("travelId",getid);
        System.out.println(JSON.toJSONString(stringObjectHashMap));



    }


    public static void strToObject(String str) {
        Map<String, Student> map = (Map<String, Student>) JSON.parseObject(str, new TypeReference<Map<String, Student>>() {
        });
        Set<Map.Entry<String, Student>> m = map.entrySet();
        Iterator<Map.Entry<String, Student>> it = m.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Student> en = it.next();
            String id = en.getKey();
            Student stu = en.getValue();
            System.out.println(stu.getId() + "===" + stu.getName());
        }
        System.out.println(map.size());
    }

    // 把对象转换成JSON字符串
    public static void objectToStr(Map map) {
        String str = JSON.toJSONString(map);
        System.out.println(str);
    }
}



