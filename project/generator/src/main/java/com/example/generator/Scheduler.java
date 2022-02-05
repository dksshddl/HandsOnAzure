package com.example.generator;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.ListEntitiesOptions;
import com.azure.data.tables.models.TableEntity;
import com.example.generator.domain.Advertisement;
import com.example.generator.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.azure.data.tables.implementation.TablesConstants.PARTITION_KEY;


@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final TableClient tableClient;
    private final KafkaService kafkaService;

    @Scheduled(cron = "0 0 * ? * *")
    void schedule() {
        String filter = String.format("%s eq 'Advertisement'", PARTITION_KEY);
        ListEntitiesOptions options = new ListEntitiesOptions().setFilter(filter);
        List<TableEntity> entities = tableClient.listEntities(options, null, null).stream().collect(Collectors.toList());
	Random rand = new Random();
	rand.setSeed(System.currentTimeMillis());
        int randomIdx = rand.nextInt(entities.size());
        TableEntity entity = entities.get(randomIdx);

        String id = (String) entity.getProperty("RowKey");
        String itemLink = (String) entity.getProperty("ItemLink");
        String imgLink = (String) entity.getProperty("ImgLink");
        String name = (String) entity.getProperty("Name");
        String price = (String) entity.getProperty("Price");
        String categoryEn = (String) entity.getProperty("CategoryEn");
        String categoryKo = (String) entity.getProperty("CategoryKo");
        log.info("\nItem info\n" +
                "  itemId: {}\n" +
                "  itemLink: {}\n" +
                "  imgLink: {}\n" +
                "  name: {}\n" +
                "  price: {}\n" +
                "  category: {}", id, itemLink, imgLink, name, price, categoryKo);
        Advertisement ad = Advertisement.builder().id(id).itemLink(itemLink).imgLink(imgLink).name(name).price(price).categoryEn(categoryEn).categoryKo(categoryKo).build();
        kafkaService.insert(ad);
        
        // send all ad
        // List<Advertisement> advertisements = tableClient.listEntities(options, null, null).stream().map((entity) -> {
        //     String id = (String) entity.getProperty("RowKey");
        //     String itemLink = (String) entity.getProperty("ItemLink");
        //     String imgLink = (String) entity.getProperty("ImgLink");
        //     String name = (String) entity.getProperty("Name");
        //     String price = (String) entity.getProperty("Pdockrice");
        //     String categoryEn = (String) entity.getProperty("CategoryEn");
        //     String categoryKo = (String) entity.getProperty("CategoryKo");
        //     log.info("\nItem info\n" +
        //             "  itemId: {}\n" +
        //             "  itemLink: {}\n" +
        //             "  imgLink: {}\n" +
        //             "  name: {}\n" +
        //             "  price: {}\n" +
        //             "  category: {}", id, itemLink, imgLink, name, price, categoryKo);
        //     return Advertisement.builder().id(id).itemLink(itemLink).imgLink(imgLink).name(name).price(price).categoryEn(categoryEn).categoryKo(categoryKo).build();
        // }).collect(Collectors.toList());

        // advertisements.forEach((kafkaService::insert));
    }
}
