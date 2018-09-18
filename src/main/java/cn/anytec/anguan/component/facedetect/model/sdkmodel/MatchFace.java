package cn.anytec.anguan.component.facedetect.model.sdkmodel;

public class MatchFace{
    private double confidence;
    private IdentifyFace face;

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public void setFace(IdentifyFace face) {
        this.face = face;
    }

    public IdentifyFace getFace() {
        return face;
    }


}
