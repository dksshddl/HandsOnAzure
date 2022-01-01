package com.example.storage2.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import com.example.storage2.vo.PredictionVO;
import com.example.storage2.vo.RequestVO;
import com.google.common.io.ByteStreams;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.CustomVisionPredictionClient;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.CustomVisionPredictionManager;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.Predictions.PredictionsClassifyImageDefinitionStages.WithExecute;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.models.ClassifyImageOptionalParameter;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.models.ImagePrediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VisionService {
    final static String predictionApiKey = "ad54def56e6a4bf088b94070cd2b5832";
    final static String predictionEndpoint = "https://changjongvision.cognitiveservices.azure.com/";
    final static String predictionResourceId = "/subscriptions/cfad2bfb-ee2f-4833-9e2c-4acf2facc955/resourceGroups/changjong/providers/Microsoft.CognitiveServices/accounts/changjongvision";
    final static String projectUUID = "55f32485-4435-4a96-8c35-815f13815ed2";
    final static String publishedName = "changjongvision";

    private final CustomVisionPredictionClient predictor = getPredictClient();

    @Autowired
    private VisionClient visionClient;

    public PredictionVO predictByUrl(RequestVO vo) {
        return visionClient.predictionByUrl(vo);
    }

    public PredictionVO predictByImage(String file) {
        return visionClient.predictionByImage(file.getBytes());
    }

    // public PredictionVO predict(RequestVO vo) {
    //     // log.info("start prediction...");
    //     // byte[] data = readImage(vo.getFileName());
    //     // ImagePrediction results = predictor.predictions()
    //     //                                    .classifyImage(UUID.fromString(projectUUID), publishedName, data, null);
    //     // log.info("end prediction...");
                                        
    //     // return PredictionVO.builder()
    //     //                    .predictions(results.predictions())
    //     //                    .build();

    // }

    private byte[] readImage(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(new File(fileName));
            return ByteStreams.toByteArray(fis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static CustomVisionPredictionClient getPredictClient() {
        log.info("create prediction clietn");
        CustomVisionPredictionClient client = CustomVisionPredictionManager.authenticate(predictionEndpoint, predictionApiKey)
                                                                            .withEndpoint("https://changjongvision.cognitiveservices.azure.com/");
        return client;
    }
}
