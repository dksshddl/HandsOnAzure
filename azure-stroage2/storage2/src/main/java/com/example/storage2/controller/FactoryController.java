package com.example.storage2.controller;

import com.example.storage2.service.FactoryService;
import com.example.storage2.vo.FactoryVO;
import com.example.storage2.vo.ResponseVO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/factory",produces = MediaType.APPLICATION_JSON_VALUE)
public class FactoryController {

    public final FactoryService factoryService;
    
    @GetMapping("/{id}")
    public ResponseEntity<FactoryVO> getFactory(@PathVariable String id) {
        FactoryVO vo = factoryService.getFactory(id);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseVO> insertFactory(@RequestBody FactoryVO vo) {
        String id = factoryService.insertFactory(vo);
        return new ResponseEntity<>(new ResponseVO(id, "success"), HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<ResponseVO> updateFactory(@RequestBody FactoryVO vo) {
        String id = factoryService.updateFactory(vo);
        return new ResponseEntity<>(new ResponseVO(id, "success"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseVO> deleteFactory(@PathVariable String id) {
        factoryService.deleteFactory(id);
        return new ResponseEntity<>(new ResponseVO(id, "success"), HttpStatus.OK);
    }
}
