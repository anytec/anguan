package cn.anytec.anguan.component.facedetect.model;


import cn.anytec.anguan.component.facedetect.model.sdkmodel.FaceInfo;

import java.util.List;

public class DetectPojo {

    private double orientation;
    private List<FaceInfo> faces;

    public double getOrientation() {
        return orientation;
    }

    public List<FaceInfo> getFaces() {
        return faces;
    }


}
