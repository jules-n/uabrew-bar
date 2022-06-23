package com.julesn.uabrewbar.controllers;

import com.julesn.uabrewbar.domain.Bar;
import com.julesn.uabrewbar.services.bar.BarService;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/bar")
@Log4j2
public class BarController {

    @Setter(onMethod_ = {@Autowired})
    private BarService barService;

    @GetMapping
    public ResponseEntity<Bar> get(@RequestAttribute String name) {
        var bar = barService.getBarByName(name);
        return ResponseEntity.ok(bar);
    }

    @PostMapping
    public ResponseEntity register(@RequestBody Bar bar) {
       try {
           isValid(bar);
           barService.registerBar(bar);
           return ResponseEntity.ok().build();
       } catch (Exception ex){
           return ResponseEntity.badRequest().body(ex);
       }
    }

    @PutMapping("{bar}")
    public ResponseEntity update(@PathVariable("bar") String barName, @RequestBody Bar bar) {
        try {
            isValid(bar);
            barService.updateDarData(barName, bar);
            return ResponseEntity.ok().build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> leaveTheSystem(@RequestAttribute String bar) {
        var result = barService.leaveTheSystem(bar);
        return result ? ResponseEntity.ok("Good Luck, " + bar) : ResponseEntity.badRequest().build();
    }

    @PutMapping("{bar}/addWorker")
    public ResponseEntity addWorker(@PathVariable("bar") String barName, @RequestAttribute String _id) {
        var result = barService.addWorker(barName, _id);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @SneakyThrows
    private boolean isValid(Bar bar) {
        final String REGEX = "[A-Za-z]+";
        var country = bar.getCountry();
        boolean isValid = country.matches(REGEX);
        if (!isValid) {
            throw new Exception("Only latin in country name");
        }
        if (country.toLowerCase(Locale.ROOT).equals("russia")
                || country.toLowerCase(Locale.ROOT).equals("ruzzia")
                || country.toLowerCase(Locale.ROOT).equals("kazaptstan")) {
            throw new Exception("Go fuck yourself, pidori");
        }
        log.info("Welcome, friends from "+country);
        return true;
    }
}
