package cn.anytec.anguan.component.facedetect.model.form;

import org.springframework.web.multipart.MultipartFile;

public class FaceForm {

    private String id_number;

    private MultipartFile photo;

    private String photoLink;

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    @Override
    public String toString() {
        return "FaceForm{" +
                "id_number='" + id_number + '\'' +
                ", photo=" + photo +
                ", photoLink='" + photoLink + '\'' +
                '}';
    }

    public FaceForm() {
    }

    public FaceForm(String id_number) {
        this.id_number = id_number;
    }

    public FaceForm(String id_number, MultipartFile photo, String photoLink) {
        this.id_number = id_number;
        this.photo = photo;
        this.photoLink = photoLink;
    }
}
