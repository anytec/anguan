package cn.anytec.anguan.component.facedetect.model.sdkmodel;

import java.util.List;

public class IdentifyFace{
    private String id;
    private String person_id;
    private double age;
    private String gender;
    private List<String> emotions;
    private boolean friend;
    private List<String> galleries;
    private String meta;
    private String normalized;
    private String photo;
    private String thumbnail;
    private String photo_hash;
    private String timestamp;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    public void setId(String id) {
        this.id = id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmotions(List<String> emotions) {
        this.emotions = emotions;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public void setGalleries(List<String> galleries) {
        this.galleries = galleries;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public void setNormalized(String normalized) {
        this.normalized = normalized;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setPhoto_hash(String photo_hash) {
        this.photo_hash = photo_hash;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public String getId() {
        return id;
    }

    public String getPerson_id() {
        return person_id;
    }

    public Double getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getEmotions() {
        return emotions;
    }

    public boolean isFriend() {
        return friend;
    }

    public List<String> getGalleries() {
        return galleries;
    }

    public String getMeta() {
        return meta;
    }

    public String getNormalized() {
        return normalized;
    }

    public String getPhoto() {
        return photo;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPhoto_hash() {
        return photo_hash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public int getHeight(){
        return y2-y1;
    }
    public int getWidth(){
        return x2-x1;
    }
}