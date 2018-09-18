package cn.anytec.anguan.controller;

import cn.anytec.anguan.component.facedetect.model.dto.FaceDTO;
import cn.anytec.anguan.component.facedetect.model.dto.PersonDTO;
import cn.anytec.anguan.component.facedetect.model.form.FaceForm;
import cn.anytec.anguan.httpconfig.ServerResponse;
import cn.anytec.anguan.service.inf.IPersonFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/face")
public class FaceController {

    @Autowired
    private IPersonFace personFace;

    @PostMapping("add")
    public ServerResponse add (FaceForm faceForm) {

        PersonDTO result = personFace.save(faceForm);

        if (result != null) {
            return ServerResponse.createBySuccessMessage("人脸信息添加成功");
        }
        return ServerResponse.createByErrorMessage("人脸信息添加失败");
    }

    @PutMapping(value = "update")
//    public ServerResponse update(@RequestParam String id, FaceForm faceForm){
    public ServerResponse update(String id_number, MultipartFile photo, String photoLink) {
        FaceForm faceForm = new FaceForm();
        faceForm.setId_number(id_number);
        faceForm.setPhoto(photo);
        faceForm.setPhotoLink(photoLink);

        PersonDTO update = personFace.update(faceForm);
        if (update != null) {
            return ServerResponse.createBySuccessMessage("人脸信息更新成功");
        }
        return ServerResponse.createByErrorMessage("人脸信息更新失败");
    }

    @DeleteMapping("delete/{id_number}")
    public ServerResponse delete(@PathVariable("id_number") String id_number) {

        boolean delete = personFace.delete(id_number);

        if (delete) {
            return ServerResponse.createBySuccessMessage("人脸信息已删除");
        }
        return ServerResponse.createByErrorMessage("人脸信息删除失败");
    }

    @GetMapping("lists")
    public ServerResponse lists() {

        List<PersonDTO> faceAll = personFace.findAll();
        return ServerResponse.createBySuccess(faceAll);
    }

    @GetMapping("person/{id_number}")
    public ServerResponse person(@PathVariable("id_number") String id_number) {

        PersonDTO faceOne = personFace.findOne(id_number);

        if (null != faceOne) {
            return ServerResponse.createBySuccess(faceOne);
        }
        return ServerResponse.createByErrorMessage("未找到相关人脸信息");
    }

    @PostMapping("analyse")
    public ServerResponse analyse(HttpServletRequest req, @RequestBody(required = false) MultipartFile photo, @RequestBody(required = false) String macAddress) throws IOException {

        macAddress = req.getParameter("macAddress");

        String result = personFace.analyse(photo, macAddress);
        if (null != result) {
            return ServerResponse.createBySuccess(result);
        }
        return ServerResponse.createByErrorMessage("未检测到人脸");
    }
}
