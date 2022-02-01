package com.example.manager.service;

import com.example.manager.domain.item.ItemType;
import com.example.manager.domain.item.ItemVO;
import com.example.manager.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserItemService {

    private final UserItemRepository userItemRepository;

    public void addLikeItem(String userId, String iteminfo) {
        userItemRepository.insert(userId, iteminfo, ItemType.LIKE);
    }

    public void addHateItem(String userId, String iteminfo) {
        userItemRepository.insert(userId, iteminfo, ItemType.HATE);
    }

    public void deleteLikeItem(String userId, String itemId) {
        userItemRepository.delete(userId, itemId, ItemType.LIKE);
    }

    public void deleteHateItem(String userId, String itemId) {
        userItemRepository.delete(userId, itemId, ItemType.HATE);
    }

    public Mono<List<ItemVO>> getLikeItem(String userId) {
        return Mono.fromCallable(() -> userItemRepository.findAll(userId, ItemType.LIKE));
    }

    public Mono<List<ItemVO>> getHateItem(String userId) {
        return Mono.fromCallable(() -> userItemRepository.findAll(userId, ItemType.HATE));
    }
}
