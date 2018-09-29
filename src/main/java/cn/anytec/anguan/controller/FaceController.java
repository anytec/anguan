package cn.anytec.anguan.controller;

import cn.anytec.anguan.component.facedetect.model.dto.PersonDTO;
import cn.anytec.anguan.component.facedetect.model.form.FaceForm;
import cn.anytec.anguan.httpconfig.ServerResponse;
import cn.anytec.anguan.service.inf.IPersonFace;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/face")
@Api(value = "人脸识别接口", tags = "人脸识别接口")
public class FaceController {

    @Autowired
    private IPersonFace personFace;

    @ApiOperation("添加人脸信息")
    @PostMapping("")
    public ServerResponse add (FaceForm faceForm) {

        PersonDTO result = personFace.save(faceForm);

        if (result != null) {
            return ServerResponse.createBySuccessMessage("人脸信息添加成功");
        }
        return ServerResponse.createByErrorMessage("人脸信息添加失败");
    }

    @ApiOperation("修改人脸信息")
    @PutMapping("")
    public ServerResponse update(HttpServletRequest req, MultipartFile photo, String photoLink) {
        FaceForm faceForm = new FaceForm();

        String id_number = req.getParameter("id_number");
        if (StrUtil.isBlank(id_number)) {
            return ServerResponse.createByErrorMessage("id_number不能为空");
        }

        faceForm.setId_number(id_number);
        faceForm.setPhoto(photo);
        faceForm.setPhotoLink(photoLink);

        PersonDTO update = personFace.update(faceForm);
        if (update != null) {
            return ServerResponse.createBySuccessMessage("人脸信息更新成功");
        }
        return ServerResponse.createByErrorMessage("人脸信息更新失败");
    }

    @ApiOperation("根据id_number删除人脸信息")
    @DeleteMapping("{id_number}")
    public ServerResponse delete(@PathVariable("id_number") String id_number) {

        boolean delete = personFace.delete(id_number);

        if (delete) {
            return ServerResponse.createBySuccessMessage("人脸信息已删除");
        }
        return ServerResponse.createByErrorMessage("人脸信息删除失败");
    }

    @ApiOperation("查询人脸信息列表")
    @GetMapping("lists")
    public ServerResponse lists() {

        List<PersonDTO> faceAll = personFace.findAll();
        return ServerResponse.createBySuccess(faceAll);
    }

    @ApiOperation("根据id_nubmer查询单个人脸信息")
    @GetMapping("{id_number}")
    public ServerResponse person(@PathVariable("id_number") String id_number) {

        PersonDTO faceOne = personFace.findOne(id_number);

        if (null != faceOne) {
            return ServerResponse.createBySuccess(faceOne);
        }
        return ServerResponse.createByErrorMessage("未找到相关人脸信息");
    }

    @ApiOperation("人脸检测")
    @PostMapping("analyse")
    public ServerResponse analyse(HttpServletRequest req, @RequestBody(required = false) MultipartFile photo) throws IOException {

        String macAddress = req.getParameter("macAddress");

        String result = personFace.analyse(photo, macAddress);
        if (null != result) {
            return ServerResponse.createBySuccess(result);
        }
        return ServerResponse.createByErrorMessage("未检测到人脸");
    }

    @ApiOperation("用于测试推送数据")
    // 用于测试推送数据.url: ip:port/face/test
    @PostMapping("test")
    public String test(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder(1);
        Map<String, String[]> parameterMap = request.getParameterMap();

        parameterMap.forEach((K, V) -> {
            System.out.println(K + ":");
            Arrays.asList(V).forEach(System.out::println);
            sb.append(Arrays.asList(V).toString());
        });
        return "meta: " + sb.toString();
    }
}
