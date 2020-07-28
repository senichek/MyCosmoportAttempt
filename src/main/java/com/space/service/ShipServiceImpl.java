package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;


    public ShipServiceImpl() {
    }

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        //super();
        this.shipRepository = shipRepository;
    }

    @Override
    public List<Ship> getAllShipsUnfiltered() {
        List<Ship> allShipsList = new ArrayList<>();
        Iterable<Ship> ships = shipRepository.findAll();
        for (Ship x : ships) {
            allShipsList.add(x);
        }return allShipsList;
    }

    @Override
    public Ship saveShip(Ship ship) {
        return null;
    }

    @Override
    public Ship getShip(Long id) {
        return null;
    }

    @Override
    public Ship updateShip(Ship oldShip, Ship newShip) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void deleteShip(Ship ship) {

    }

    @Override
    public List<Ship> getShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        return null;
    }

    @Override
    public List<Ship> sortShipsByOrder(List<Ship> ships, ShipOrder order) {

        String orderName = new String();
        if (order == null) {
            orderName = "id";
        } else orderName = order.getFieldName();

        switch (orderName) {
            case "speed":
                Collections.sort(ships, new Comparator<Ship>() {
                    @Override
                    public int compare(Ship o1, Ship o2) {
                        if (o1.getSpeed() > o2.getSpeed()) {
                            return 1;
                        }
                        if (o1.getSpeed() < o2.getSpeed()) {
                            return -1;
                        }
                        else return 0;
                    }
                });
                break;

            case "prodDate":
                Collections.sort(ships, new Comparator<Ship>() {
                    @Override
                    public int compare(Ship o1, Ship o2) {
                        if (o1.getProdDate().before(o2.getProdDate())) {
                            return 1;
                        }
                        if (o1.getProdDate().after(o2.getProdDate())) {
                            return -1;
                        }
                        else return 0;
                    }
                });
                break;

            case "rating":
                Collections.sort(ships, new Comparator<Ship>() {
                    @Override
                    public int compare(Ship o1, Ship o2) {
                        return o1.getRating() > o2.getRating() ? 1 : (o1.getRating() < o2.getRating()) ? -1 : 0;
                    }
                });
                break;

            case "id":
                Collections.sort(ships, new Comparator<Ship>() {
                    @Override
                    public int compare(Ship o1, Ship o2) {
                        if (o1.getId() > o2.getId()) {
                            return 1;
                        }
                        if (o1.getId() < o2.getId()) {
                            return -1;
                        }
                        else return 0;
                    }
                });
                break;
        }

        return ships;
    }

    @Override
    public List<Ship> getSublistBasedOnPageSizeAndPageNumber(List<Ship> ships, Integer pageNumber, Integer pageSize) {

        // indexFrom расчитываем по формуле pageNum * pageSize;
        // indexTO расчитываем по формуле indexFrom + pageSize;
        Integer indexTO = 0;
        Integer indexFrom = 0;

        /* Инструкции из задания:
        Если параметр pageNumber не указан – нужно использовать значение 0.
        Если параметр pageSize не указан – нужно использовать значение 3. */
        if (pageSize == null || pageNumber == null) {
            return ships.subList(0, 3);
        }

        /*но если получаем параметры PageSize и PageNumber, то выводим часть списка на основе indexFrom и indexTO.
        например, при размере страницы 5 и номере страницы 2 выводим список кораблей с 6 по 10 номер. */
        else {

            indexFrom = pageNumber * pageSize;
            indexTO = indexFrom + pageSize;

            /* indexTo не должен выходить за пределы подсписка. Если indexTo выходит за пределы списка, то
            переменной indexTO присваивается значение равное размеру списка*/
            if (indexTO > ships.size()) {
                indexTO = ships.size();
            }

            return ships.subList(indexFrom, indexTO);
        }
    }

    @Override
    public boolean isShipValid(Ship ship) {
        return false;
    }

    @Override
    public double computeRating(double speed, boolean isUsed, Date prod) {
        return 0;
    }
}
