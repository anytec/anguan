package cn.anytec.anguan.service.inf;

import cn.anytec.anguan.po.Camera;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ICamera {

    Page findAll(Integer pageNum, Integer pageSize, String macAddress, String pushIp);

    Optional<Camera> findOne(Integer id);

    Camera save(Camera camera);

    Camera update(Camera camera);

    void delete(Integer id);

}
