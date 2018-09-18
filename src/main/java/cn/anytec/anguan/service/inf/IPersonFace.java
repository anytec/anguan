package cn.anytec.anguan.service.inf;

import cn.anytec.anguan.component.facedetect.model.dto.PersonDTO;
import cn.anytec.anguan.component.facedetect.model.form.FaceForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPersonFace {

    PersonDTO save(FaceForm face);

    PersonDTO update(FaceForm face);

    boolean delete(String id_number);

    List<PersonDTO> findAll();

    PersonDTO findOne(String id_number);

    String analyse(MultipartFile photo, String macAddress) throws IOException;

}
