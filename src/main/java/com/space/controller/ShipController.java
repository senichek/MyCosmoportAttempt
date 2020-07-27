package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        // indexFrom расчитываем по формуле pageNum * pageSize;
        // indexTO расчитываем по формуле indexFrom + pageSize;

        Integer indexTO = 0;
        Integer indexFrom = 0;


    // при первом запуске показываем весь список
        if (pageSize == null || pageNumber == null) {

                /* Вместо всего списка можно вывести лишь первые три корабля, так как при запуске на странице в поле
            "Ships in a page" всегда установлено значение 3.  */

            return shipService.getAllShipsUnfiltered();

        }

        /*но если получаем параметры PageSize и PageNumber, то выводим часть списка на основе indexFrom и indexTO.
        например, при размере страницы 5 и номере страницы 2 выводим список кораблей с 6 по 10 номер. */
        else {

            indexFrom = pageNumber * pageSize;
            indexTO = indexFrom + pageSize;

            /* indexTo не должен выходить за пределы подсписка. Если indexTo выходит за пределы списка, то
            переменной indexTO присваивается значение равное размеру списка*/
            if (indexTO > shipService.getAllShipsUnfiltered().size()) {
                indexTO = shipService.getAllShipsUnfiltered().size();
            }

            return shipService.getAllShipsUnfiltered().subList(indexFrom, indexTO);

        }

    }

    @RequestMapping(path = "/rest/ships/count", method = RequestMethod.GET)
    public Integer getShipsCount() {

        // Получаем общее количество кораблей, находящихся в базе данных
        return shipService.getAllShipsUnfiltered().size();
    }
}
