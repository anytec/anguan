package cn.anytec.anguan.component.facedetect.model;



import cn.anytec.anguan.component.facedetect.model.sdkmodel.MatchFace;

import java.util.List;
import java.util.Map;

public class IdentifyPojo {

    private Map<String,List<MatchFace>> results;

    public void setResults(Map<String, List<MatchFace>> results) {
        this.results = results;
    }

    public Map<String, List<MatchFace>> getResults() {
        return results;
    }


}
