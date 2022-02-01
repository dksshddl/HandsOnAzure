package com.example.manager.repository;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.TableEntity;
import com.example.manager.domain.item.ItemType;
import com.example.manager.domain.item.ItemVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserItemRepositoryTest {

    @Autowired
    private UserItemRepository userItemRepository;

    @Autowired
    @Qualifier("itemTableClient")
    private TableClient tableClient;

    @Test
    void insert_test() {
        Assertions.assertThatCode(() -> userItemRepository.insert("userId", "itemId", ItemType.LIKE))
                .doesNotThrowAnyException();
        Assertions.assertThatCode(() -> userItemRepository.insert("userId", "itemId", ItemType.HATE))
                .doesNotThrowAnyException();

        TableEntity likeEntity = tableClient.getEntity(ItemType.LIKE.getSymbol(), "userId_itemId");
        TableEntity hateEntity = tableClient.getEntity(ItemType.HATE.getSymbol(), "userId_itemId");
        Assertions.assertThat(likeEntity).isNotNull();
        Assertions.assertThat(hateEntity).isNotNull();
        tableClient.deleteEntity(likeEntity);
        tableClient.deleteEntity(hateEntity);
    }

    @Test
    void delete_test() {
        Assertions.assertThatCode(() -> userItemRepository.insert("userId", "itemId", ItemType.LIKE))
                .doesNotThrowAnyException();
        Assertions.assertThatCode(() -> userItemRepository.insert("userId", "itemId", ItemType.HATE))
                .doesNotThrowAnyException();
        Assertions.assertThatCode(() -> userItemRepository.delete("userId", "itemId", ItemType.LIKE))
                .doesNotThrowAnyException();
        Assertions.assertThatCode(() -> userItemRepository.delete("userId", "itemId", ItemType.HATE))
                .doesNotThrowAnyException();
    }

    @Test
    void findAll_test() {
        userItemRepository.insert("userId", "item1", ItemType.LIKE);
        userItemRepository.insert("userId", "item2", ItemType.LIKE);
        userItemRepository.insert("userId", "item1", ItemType.HATE);
        userItemRepository.insert("userId", "item2", ItemType.HATE);

        List<ItemVO> likeList = userItemRepository.findAll("userId", ItemType.LIKE);
        List<ItemVO> hateList = userItemRepository.findAll("userId", ItemType.HATE);

        Assertions.assertThat(likeList.size()).isEqualTo(2);
        Assertions.assertThat(hateList.size()).isEqualTo(2);

        userItemRepository.delete("userId", "item1", ItemType.LIKE);
        userItemRepository.delete("userId", "item2", ItemType.LIKE);
        userItemRepository.delete("userId", "item1", ItemType.HATE);
        userItemRepository.delete("userId", "item2", ItemType.HATE);
    }
}
