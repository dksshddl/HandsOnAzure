package com.example.mailsender.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Advertisement {
	private String id;
    private String categoryEn;
    private String categoryKo;
    private String itemLink;
    private String name;
    private String price;

    private String imgLink;
}
