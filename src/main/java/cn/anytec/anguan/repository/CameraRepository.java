package cn.anytec.anguan.repository;

import cn.anytec.anguan.component.facedetect.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CameraRepository extends JpaRepository<Camera, Integer>, JpaSpecificationExecutor<Camera>{

    Camera findByMacAddress(String macAddress);

}
