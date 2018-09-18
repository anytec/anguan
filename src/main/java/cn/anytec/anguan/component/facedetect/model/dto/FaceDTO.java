package cn.anytec.anguan.component.facedetect.model.dto;


import java.util.List;

public class FaceDTO {

    private List<PersonDTO> results;

    private String next_page;

    private String prev_page;

    public List<PersonDTO> getResults() {
        return results;
    }

    public void setResults(List<PersonDTO> results) {
        this.results = results;
    }

    public String getNext_page() {
        return next_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }

    public String getPrev_page() {
        return prev_page;
    }

    public void setPrev_page(String prev_page) {
        this.prev_page = prev_page;
    }

    @Override
    public String toString() {
        return "FaceDTO{" +
                "results=" + results +
                ", next_page='" + next_page + '\'' +
                ", prev_page='" + prev_page + '\'' +
                '}';
    }
}
