package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShipController {

    private ShipService shipService;

    public ShipController() {
    }

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @RequestMapping(path = "/rest/ships", method = RequestMethod.GET)
    public List<Ship> getAllShips (
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating,
            @RequestParam(value = "order", required = false) ShipOrder order,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {


        /*// Получаем весь список кораблей
        List<Ship> allShips = shipService.getAllShipsUnfiltered();

        List<Ship> sortedByOrder = shipService.sortShipsByOrder(allShips, order);
        // возвращаем подсписок на основе размера и номера страницы
        return shipService.getSublistBasedOnPageSizeAndPageNumber(sortedByOrder, pageNumber, pageSize);*/

        return shipService.getFilteredShips(name, planet, shipType, after, before,
                                            isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                                            minRating, maxRating, order, pageNumber, pageSize);
        }


        @RequestMapping(path = "/rest/ships/count", method = RequestMethod.GET)
        public Integer getShipsCount () {

            // Получаем общее количество кораблей, находящихся в базе данных
            return shipService.getAllShipsUnfiltered().size();
        }
}
