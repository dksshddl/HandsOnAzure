package com.example.storage2.vo;

import java.util.List;

import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.models.Prediction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PredictionVO {
    List<Prediction> predictions;
}
