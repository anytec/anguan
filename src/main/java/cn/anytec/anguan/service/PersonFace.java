package cn.anytec.anguan.service;

import cn.anytec.anguan.component.facedetect.model.Camera;
import cn.anytec.anguan.component.facedetect.model.dto.PersonDTO;
import cn.anytec.anguan.component.facedetect.model.form.FaceForm;
import cn.anytec.anguan.component.facedetect.model.sdkmodel.MatchFace;
import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.repository.CameraRepository;
import cn.anytec.anguan.service.inf.IPersonFace;
import cn.anytec.anguan.util.FaceHelper;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PersonFace implements IPersonFace{

    private final Log log = LogFactory.get();

    @Autowired
    private CameraRepository repository;
    @Override
    public PersonDTO save(FaceForm face) {

        PersonDTO personDTO = FaceHelper.findOne(face.getId_number());
        if (personDTO == null) {
            PersonDTO result = FaceHelper.save(face);
            return result;
        }else {
            throw new AnguanException("id_number已存在");
        }
    }

    @Override
    public PersonDTO update(FaceForm face) {

        if (StrUtil.isBlank(face.getPhotoLink()) && face.getPhoto() == null) {
            throw new AnguanException("未传递任何人脸信息(链接/文件)");
        }

        PersonDTO findResult;
        try {
            findResult = FaceHelper.findOne(face.getId_number());
        } catch (AnguanException e) {
            throw new AnguanException("id_number不存在");
        }

        if (findResult != null) {
            boolean deleteResult = FaceHelper.delete(face);
            if (deleteResult) {
                return save(face);
            }
        }else {
            throw new AnguanException("id_number不存在");
        }
        return null;
    }

    @Override
    public boolean delete(String id_number) {
        FaceForm form = new FaceForm(id_number);
        boolean delete = FaceHelper.delete(form);
        return delete;
    }

    @Override
    public List<PersonDTO> findAll() {

        return FaceHelper.findAll();
    }

    @Override
    public PersonDTO findOne(String id_number) {

        return FaceHelper.findOne(id_number);
    }

    @Override
    public String analyse(MultipartFile photo, String macAddress) throws IOException {

        Camera camera = repository.findByMacAddress(macAddress);

        String pushIp = Optional.ofNullable(camera)
                .map(Camera::getPushIp)
                .orElseThrow(() -> new AnguanException("MacAddress error"));

        Map<String, Object> resultMap = new HashMap<>();

        Map<String, List<MatchFace>> identifyPojo;
        if (photo != null) {
            identifyPojo = FaceHelper.identify(photo.getBytes());
        }else {
            throw new AnguanException("图片不能为空");
        }

        identifyPojo.forEach((String K, List<MatchFace> faces) -> {
            List<String> strings = new ArrayList<>();
            faces.forEach(face -> {
                strings.add(face.getFace().getMeta());
            });
            if (CollectionUtil.isNotEmpty(strings)) {
                resultMap.put("meta", strings);
            }
        });

        String url = "http://" + pushIp;

        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpRequest.post(url)
                    .form(resultMap)
                    .timeout(10000)
                    .execute();
        } catch (Exception e) {
            throw new AnguanException(httpResponse.getStatus(), httpResponse.body());
        }

        if (httpResponse.getStatus() != HttpStatus.HTTP_OK) {
            throw new AnguanException(httpResponse.getStatus(), "推送数据时发生错误!");
        }

        return httpResponse.body();
    }
}
