package com.julesn.uabrewbar.services.bar;

import com.julesn.uabrewbar.domain.Bar;
import com.julesn.uabrewbar.persistence.bar.BarRepository;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Primary
@Log4j2
public class BarServiceImpl implements BarService {

    @Setter(onMethod_ = {@Autowired})
    private BarRepository barRepository;

    @Override
    public Bar getBarByName(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return barRepository.getBarByCriteria(map);
    }

    @Override
    public boolean updateDarData(String name, Bar bar) {
        return barRepository.update(bar, name);
    }

    @Override
    public boolean registerBar(Bar bar) {
        try {
            barRepository.save(bar);
            return true;
        } catch (Exception ex) {
            log.warn(ex);
            return false;
        }
    }

    @Override
    public boolean leaveTheSystem(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return barRepository.deleteBarByCriteria(map);
    }

    @Override
    public boolean addWorker(String bar, String id) {
        var barToUpdate = getBarByName(bar);
        var workers = barToUpdate.getWorkers();
        workers.add(id);
        barToUpdate.setWorkers(workers);
        return barRepository.update(barToUpdate, bar);
    }
}
