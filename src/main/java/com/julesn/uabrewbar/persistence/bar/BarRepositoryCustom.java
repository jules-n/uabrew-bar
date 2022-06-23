package com.julesn.uabrewbar.persistence.bar;

import com.julesn.uabrewbar.domain.Bar;

import java.util.Map;

public interface BarRepositoryCustom {
    Bar getBarByCriteria(Map<String, Object> criteria);
    boolean deleteBarByCriteria(Map<String, Object> criteria);
    boolean update(Bar bar, String name);
}
