package com.julesn.uabrewbar.services.bar;

import com.julesn.uabrewbar.domain.Bar;

public interface BarService {

    Bar getBarByName(String name);
    boolean updateDarData(String name, Bar bar);
    boolean registerBar(Bar bar);
    boolean leaveTheSystem(String name);
    boolean addWorker(String bar, String id);
}
