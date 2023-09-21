package cn.bugstack.xfg.dev.tech.test.domain.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private String userName;
    private String password;
    // 不被序列化
    @JSONField(name="amount", serialize=false)
    private Double amount;
    // 序列化格式
    @JSONField(name="createTime", format="dd/MM/yyyy", ordinal = 3)
    private Date createTime;

    @Override
    public String toString() {
        return "UserEntity{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", amount=" + amount +
                ", createTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime) +
                '}';
    }

}
