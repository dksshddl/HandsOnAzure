package com.example.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@EnableSwagger2
@SpringBootApplication
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

	@Bean
	public Map<String, String> categoryEnToKoMap() {
		String[] categoryEn = {"womanclothe", "manclothe", "food", "home_decoration", "digital", "office", "living", "beauty", "sports",
				"health", "kitchen", "pets", "hobby", "car", "book", "travel"};
		String[] categoryKo = {"여성패션", "남성패션", "식품", "가구/홈인테리어", "가전/디지털", "문구/오피스", "생활용품", "뷰티", "스포츠/레저", "건강식품/헬스", "주방용품",
				"반려동물용품", "완구/취미", "자동차용품", "도서/CD/DVD", "여행"};
		Map<String, String> enToKo = new HashMap<>();
		for (int i = 0; i < categoryEn.length; i++) {
			enToKo.put(categoryEn[i], categoryKo[i]);
		}
		return enToKo;
	}

	@Bean
	public Map<String, String> categoryKoToEn() {
		String[] categoryEn = {"womanclothe", "manclothe", "food", "home_decoration", "digital", "office", "living", "beauty", "sports",
				"health", "kitchen", "pets", "hobby", "car", "book", "travel"};
		String[] categoryKo = {"여성패션", "남성패션", "식품", "가구/홈인테리어", "가전/디지털", "문구/오피스", "생활용품", "뷰티", "스포츠/레저", "건강식품/헬스", "주방용품",
				"반려동물용품", "완구/취미", "자동차용품", "도서/CD/DVD", "여행"};
		Map<String, String> koToEn = new HashMap<>();
		for (int i = 0; i < categoryEn.length; i++) {
			koToEn.put(categoryKo[i], categoryEn[i]);
		}
		return koToEn;
	}
}