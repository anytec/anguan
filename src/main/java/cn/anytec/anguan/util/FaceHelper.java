package cn.anytec.anguan.util;

import cn.anytec.anguan.component.facedetect.model.ErrorPojo;
import cn.anytec.anguan.component.facedetect.model.IdentifyPojo;
import cn.anytec.anguan.component.facedetect.model.dto.FaceDTO;
import cn.anytec.anguan.component.facedetect.model.dto.PersonDTO;
import cn.anytec.anguan.component.facedetect.model.form.FaceForm;
import cn.anytec.anguan.component.facedetect.model.sdkmodel.MatchFace;
import cn.anytec.anguan.config.GeneralConfig;
import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.httpconfig.ResponseCode;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class FaceHelper {

    private static final Log log = LogFactory.get();
    private static final GeneralConfig config = ApplicationContextHolder.getBean(GeneralConfig.class);

    public static PersonDTO save(FaceForm face)  {

        HttpRequest httpRequest = HttpRequest.post(config.getFaceUrl())
                .header("Authorization", "Token " + config.getToken())
                .form("meta", face.getId_number())
                .form("galleries", config.getStaticGallery())
                .timeout(10000);

        if (StrUtil.isNotBlank(face.getPhotoLink())) {
            httpRequest.form("photo", face.getPhotoLink());
        }else if (face.getPhoto() != null){
            try {
                httpRequest.form("photo", face.getPhoto().getBytes(), "photo");
            } catch (IOException e) {
                throw new AnguanException(e);
            }
        }else {
            throw new AnguanException("未传递任何人脸信息(链接/文件)");
        }
        HttpResponse httpResponse = httpRequest.execute();
        int status = httpResponse.getStatus();
        String body = httpResponse.body();


        PersonDTO personDTO;
        if (status == HttpStatus.HTTP_OK) {
            FaceDTO faceDTO = JSON.parseObject(body, new TypeReference<FaceDTO>() {});
            personDTO = Optional.ofNullable(faceDTO.getResults().get(0))
                    .orElseThrow(() -> new AnguanException(ResponseCode.ERROR.getCode(), "返回结果为空!"));
            return personDTO;
        }else {
            ErrorPojo errorPojo = JSON.parseObject(body, new TypeReference<ErrorPojo>() {});
            throw new AnguanException(status, errorPojo.getReason());
        }
    }

    public static PersonDTO findOne (String id_number) throws AnguanException {

        String url = config.getAnalyseMetaUrl() + id_number;

        HttpResponse httpResponse = HttpRequest.get(url)
                .header("Authorization", "Token " + config.getToken())
                .timeout(10000)
                .execute();

        PersonDTO personDTO;
        int status = httpResponse.getStatus();
        if (status == HttpStatus.HTTP_OK) {
            FaceDTO faceDTO = JSON.parseObject(httpResponse.body(), new TypeReference<FaceDTO>() {});
            personDTO = Optional.ofNullable(faceDTO.getResults().get(0))
                    .orElseThrow(() -> new AnguanException(ResponseCode.ERROR.getCode(), "返回结果为空!"));
            return personDTO;
        }else if(status == HttpStatus.HTTP_NOT_FOUND){
            return null;
        }else {
            ErrorPojo errorPojo = JSON.parseObject(httpResponse.body(), new TypeReference<ErrorPojo>() {});
            throw new AnguanException(status, errorPojo.getReason());
        }
    }

    public static List<PersonDTO> findAll() {

        HttpResponse httpResponse = HttpRequest.get(config.getFacesUrl())
                .header("Authorization", "Token " + config.getToken())
                .timeout(10000)
                .execute();

        int status = httpResponse.getStatus();

        if (status == HttpStatus.HTTP_OK) {
            FaceDTO faceDTO = JSON.parseObject(httpResponse.body(), new TypeReference<FaceDTO>() {});
            List<PersonDTO> personDTOS = Optional.ofNullable(faceDTO.getResults())
                    .orElseThrow(() -> new AnguanException(ResponseCode.ERROR.getCode(), "返回结果为空!"));
            return personDTOS;
        }else {
            ErrorPojo errorPojo = JSON.parseObject(httpResponse.body(), new TypeReference<ErrorPojo>() {});
            throw new AnguanException(status, errorPojo.getReason());
        }
    }

    public static boolean delete(FaceForm face) {

        PersonDTO result = findOne(face.getId_number());

        String id_number = Optional.ofNullable(result)
                .map(PersonDTO::getId)
                .orElseThrow(() -> new AnguanException("id_number不存在"));

        String url = config.getFaceUrl() + "/id/" + id_number;

        HttpResponse httpResponse = HttpRequest.delete(url)
                .header("Authorization", "Token " + config.getToken())
                .timeout(10000)
                .execute();

        int status = httpResponse.getStatus();
        if (status == HttpStatus.HTTP_NO_CONTENT) {
            return true;
        }else if (status == HttpStatus.HTTP_NOT_FOUND){
            throw new AnguanException(status, "id_number不存在");
        }else {
            throw new AnguanException(httpResponse.getStatus(), httpResponse.body());
        }
    }

    public static Map<String, List<MatchFace>> identify(byte[] photo) {

        HttpResponse httpResponse = HttpRequest.post(config.getIdentifyUrl())
                .header("Authorization", "Token " + config.getToken())
                .form("photo", photo, "photo")
                .timeout(10000)
                .execute();

        int status = httpResponse.getStatus();
        if (status == HttpStatus.HTTP_OK) {
            log.info("【检测人脸】 成功检测到人脸");
            IdentifyPojo JSONResult = JSON.parseObject(httpResponse.body(), new TypeReference<IdentifyPojo>() {});
            Map<String, List<MatchFace>> identifyPojo1 =
                    Optional.ofNullable(JSONResult).map(IdentifyPojo::getResults).
                            orElseThrow(() -> new AnguanException(status, httpResponse.body()));
            return identifyPojo1;
        }
        log.error("【检测人脸】 失败, " + httpResponse.body());
        return null;
    }
}
