package cn.bugstack.xfg.dev.tech.test;

import cn.bugstack.xfg.dev.tech.test.domain.model.entity.ChatCompletionRequest;
import cn.bugstack.xfg.dev.tech.test.domain.model.entity.UserEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson2.JSONB;
import com.alibaba.fastjson2.JSONWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Type;
import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 单元测试 文档；<a href="https://alibaba.github.io/fastjson2/">fastjson2</a>
 * @create 2023-09-23 06:18
 */
@Slf4j
public class ApiTest {

    public static void main(String[] args) {
        // 创建自定义的日期格式化器
        SimpleDateFormatSerializer dateFormat = new SimpleDateFormatSerializer("dd/MM/yyyy");

        // 创建Fastjson的配置对象
        SerializeConfig config = new SerializeConfig();
        config.put(Date.class, dateFormat);

        // 将日期字符串转换为Java Date对象
        String dateString = "Thu Sep 21 00:00:00 CST 2023";
        Date date = JSON.parseObject(dateString, (Type) Date.class, (ParseProcess) config);

        System.out.println(date);

        String strJson = JSON.toJSONString(UserEntity.builder().build());
        UserEntity userEntity = JSON.parseObject(strJson, UserEntity.class);
    }

    /**
     * JsonProperty 设定序列化字段
     */
    @Test
    public void test_JsonProperty() {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("3.5")
                .topP(1.0D)
                .maxTokens(1024)
                .build();
        log.info(JSON.toJSONString(request));
    }

    /**
     * 1. 排除不被序列化的字段
     * 2. 指定不被序列化的字段
     * 3. 格式化序列化的字段
     */
    @Test
    public void test_excludes() {
        UserEntity userEntity = UserEntity.builder()
                .amount(100D)
                .userName("xfg")
                .password("abc000")
                .createTime(new Date())
                .build();

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        Collections.addAll(filter.getExcludes(), "password");

        log.info(JSON.toJSONString(userEntity, filter));
    }

    @Test
    public void test_parseObject() {
        String jsonStr = "{\"userName\":\"xfg\",\"createTime\":\"21/09/2023\"}";
        UserEntity userEntity = JSON.parseObject(jsonStr, UserEntity.class);
        log.info(userEntity.toString());
    }

    @Test
    public void test_map2json() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "xfg");
        map.put("key2", 123);
        map.put("key3", false);
        log.info(JSON.toJSONString(map));
    }

    @Test
    public void test_json2map() {
        String jsonString = "{\"key1\":\"xfg\",\"key2\":123,\"key3\":false}";
        Map<String, Object> map = JSON.parseObject(jsonString, Map.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            log.info("{} : {}", entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void test_JSONArray() {
        String text1 = "{\"id\": 2,\"name\": \"fastjson2\"}";
        JSONObject obj = JSON.parseObject(text1);

        int id = obj.getIntValue("id");
        String name = obj.getString("name");

        String text2 = "[2, \"fastjson2\"]";
        JSONArray array = JSON.parseArray(text2);

        int id2 = array.getIntValue(0);
        String name2 = array.getString(1);
    }

    @Test
    public void test_toBytes() {
        UserEntity userEntity = UserEntity.builder()
                .amount(100D)
                .userName("xfg")
                .password("abc000")
                .createTime(new Date())
                .build();

        byte[] bytes1 = JSONB.toBytes(userEntity);
        byte[] bytes2 = JSONB.toBytes(userEntity, JSONWriter.Feature.BeanToArray);
    }

    /**
     * toString 转对象
     */
    @Test
    public void testToString2Bean() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .amount(100D)
                .userName("xfg")
                .password("abc000")
                .createTime(new Date())
                .build();

        log.info(userEntity.toString());
        log.info(JSON.toJSONString(ToString2Bean.toObject(userEntity.toString(), UserEntity.class)));
    }

}
