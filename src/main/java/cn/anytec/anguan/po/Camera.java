package cn.anytec.anguan.po;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "fd_cameras")
public class Camera implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "设备名称必填")
    @Length(max = 50, message = "设备名称不能超过50个字符")
    private String name;

    @Pattern(regexp = "^([0-9a-fA-F]{2})(([-][0-9a-fA-F]{2}){5})$|^([0-9a-fA-F]{2})(([:][0-9a-fA-F]{2}){5})$", message = "mac地址格式不正确")
    @NotNull(message = "mac地址必填")
    private String macAddress;

    @Pattern(regexp = "^(1\\d{2}|2[0-4][0-9]|25[0-5]|[1-9][0-9]|[1-9])(\\.(1\\d{2}|2[0-4][0-9]|25[0-5]|[1-9][0-9]|[0-9])){3}$", message = "ip地址格式不正确")
    @NotNull(message = "推送地址必填")
    private String pushIp;

    @Pattern(regexp = "^(1|0(\\.\\d{1,2})?)$", message = "阈值范围为[0.00-1.00],")
    @NotNull(message = "阈值必填")
    private String threshold;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getPushIp() {
        return pushIp;
    }

    public void setPushIp(String pushIp) {
        this.pushIp = pushIp;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", pushIp='" + pushIp + '\'' +
                ", threshold='" + threshold + '\'' +
                '}';
    }
}
