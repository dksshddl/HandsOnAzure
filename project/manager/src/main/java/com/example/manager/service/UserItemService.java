package com.example.manager.service;

import com.example.manager.domain.item.ItemListVO;
import com.example.manager.domain.item.ItemType;
import com.example.manager.domain.item.ItemVO;
import com.example.manager.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserItemService {

    private final UserItemRepository userItemRepository;

    public boolean addLikeItem(String userId, String iteminfo) {
        userItemRepository.insert(userId, iteminfo, ItemType.LIKE);
	return true;
    }

    public boolean addHateItem(String userId, String iteminfo) {
        userItemRepository.insert(userId, iteminfo, ItemType.HATE);
	return true;
    }

    public void deleteLikeItem(String userId, String itemId) {
        userItemRepository.delete(userId, itemId, ItemType.LIKE);
    }

    public void deleteHateItem(String userId, String itemId) {
        userItemRepository.delete(userId, itemId, ItemType.HATE);
    }

    public ItemListVO getLikeItem(String userId) {
            List<ItemVO> itemVOList = userItemRepository.findAll(userId, ItemType.LIKE);
            return ItemListVO.builder().itemVOList(itemVOList).count(itemVOList.size()).build();
    }

    public ItemListVO getHateItem(String userId) {
            List<ItemVO> itemVOList = userItemRepository.findAll(userId, ItemType.LIKE);
            return ItemListVO.builder().itemVOList(itemVOList).count(itemVOList.size()).build();
    }
}
