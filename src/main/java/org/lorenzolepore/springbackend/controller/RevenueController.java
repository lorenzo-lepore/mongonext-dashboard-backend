package org.lorenzolepore.springbackend.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.lorenzolepore.springbackend.model.Revenue;
import org.lorenzolepore.springbackend.service.RevenueService;

@RestController
@RequestMapping("/revenues")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    /* TESTING RELATED */

    @PostMapping("/saveTest")
    public ResponseEntity<Revenue> saveTest() {
        return new ResponseEntity<>(revenueService.saveRevenue(new Revenue(new ObjectId(), "Dec", 12345)), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Revenue Controller is working", HttpStatus.OK);
    }

    /* CRUD METHODS */

    @PostMapping("/save")
    public ResponseEntity<Revenue> saveRevenue(@RequestBody Revenue revenue) {
        return new ResponseEntity<>(revenueService.saveRevenue(revenue), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Revenue>> getAllRevenues() {
        return new ResponseEntity<>(revenueService.getAllRevenues(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Revenue> getRevenueById(@PathVariable String id) {
        Optional<Revenue> revenue = revenueService.getRevenueById(id);

        if (revenue.isPresent()) {
            return new ResponseEntity<>(revenue.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Revenue> updateRevenue(
            @RequestParam String id,
            @RequestParam int amount
    ) {
        Optional<Revenue> existingRevenue = revenueService.getRevenueById(id);

        if (amount < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (existingRevenue.isPresent()) {
            Revenue savedRevenue = revenueService.updateRevenue(existingRevenue.get(), amount);
            return new ResponseEntity<>(savedRevenue, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRevenue(
            @RequestParam String id
    ) {
        boolean isDeleted = revenueService.deleteRevenue(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
