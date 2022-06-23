package com.julesn.uabrewbar.services.bar;

import com.julesn.uabrewbar.domain.Bar;

import java.util.List;

public interface BarService {

    Bar getBarByName(String name);
    boolean updateBarData(String name, Bar bar);
    boolean registerBar(Bar bar);
    boolean leaveTheSystem(String name);
    boolean addWorker(String bar, String id);
    List<Bar> getAll();
    boolean addPlace(String bar, String place);
}
