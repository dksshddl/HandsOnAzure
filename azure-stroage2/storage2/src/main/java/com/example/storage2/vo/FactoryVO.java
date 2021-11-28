package com.example.storage2.vo;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FactoryVO extends TableServiceEntity {
    String id;
    String name;
    String type;
    String site;
}
