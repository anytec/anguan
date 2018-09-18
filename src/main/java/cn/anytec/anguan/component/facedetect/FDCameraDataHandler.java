package cn.anytec.anguan.component.facedetect;


import cn.anytec.anguan.config.GeneralConfig;
import cn.anytec.anguan.service.inf.IFaceAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Scope("singleton")
public class FDCameraDataHandler {
    private static final Logger logger = LoggerFactory.getLogger(FDCameraDataHandler.class);

    private ExecutorService faceReconPool = null;

    @Autowired
    private GeneralConfig config;

    ThreadLocal<Integer> counts = new ThreadLocal<>();
    @Autowired
    private IFaceAnalysis faceAnalysis;

    @PostConstruct
    public void init() {
        faceReconPool = Executors.newFixedThreadPool(config.getDataThreadNum(), (runnable)-> {
            Thread thread = new Thread(runnable);
            thread.setPriority(5);
            thread.setDaemon(true);
            return thread;
        });
    }


    public void onCameraData(byte[] mJpgData,String mStrMac) {
        if(!mStrMac.contains(":")){
            faceReconPool.execute(()->
                    faceAnalysis.analyse(mJpgData,mStrMac)
            );
            return;
        }

        Integer count = counts.get();
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        counts.set(count);

        if ((count % config.getDataThreshold()) == 0) {
            logger.debug("Put to pool - mac:" + mStrMac +" length:"+ mJpgData.length);
            counts.set(0);
            //faceReconPool.execute(new FDThread(data.mJpgData, data.mStrMac,businessService));
            faceReconPool.execute(()->
                    faceAnalysis.analyse(mJpgData,mStrMac)
            );
        }
    }


}

