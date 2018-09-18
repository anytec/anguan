package cn.anytec.anguan.component.facedetect.model;

public class Face {

    private String id_number;

    private String photo;

    private String face;

    @Override
    public String toString() {
        return "Face{" +
                "id_number='" + id_number + '\'' +
                ", photo='" + photo + '\'' +
                ", face='" + face + '\'' +
                '}';
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }
}
