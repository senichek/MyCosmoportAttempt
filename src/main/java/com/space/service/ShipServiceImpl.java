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
    public List<Ship> getShipsByFullOrPartialName(String name) {

        /* Поиск по полям name и planet происходить по частичному соответствию. Например, если в БД есть корабль
        с именем «Левиафан», а параметр name задан как «иа» - такой корабль должен отображаться в результатах (Левиафан). */

        List<Ship> shipsFoundByName = new ArrayList<>();
        List<Ship> allShips = getAllShipsUnfiltered();
        for (Ship x : allShips) {
            // возвращает true если имя включает в себя последновательность символов name
            if (x.getName().contains(name)) {
                shipsFoundByName.add(x);
            }
        }
        return shipsFoundByName;
    }

    @Override
    public List<Ship> getShipsByFullOrPartialPlanetName(String name) {
        List<Ship> shipsFoundByPlanetName = new ArrayList<>();
        List<Ship> allShips = getAllShipsUnfiltered();
        for (Ship x : allShips) {
            if (x.getPlanet().contains(name)) {
                shipsFoundByPlanetName.add(x);
            }
        }
        return shipsFoundByPlanetName;
    }

    public List<Ship> getShipsByType (ShipType type) {
        List<Ship> shipsFoundByShipType = new ArrayList<>();
        List<Ship> allShips = getAllShipsUnfiltered();
        for (Ship x : allShips) {
            if (x.getShipType() == type) {
                shipsFoundByShipType.add(x);
            }
        }
        return shipsFoundByShipType;
    }

    @Override
    public List<Ship> getShipsBetweenProdDates(Long before, Long after) {

        List<Ship> allShips = getAllShipsUnfiltered();
        List<Ship> shipsBetweenTwoDates = new ArrayList<>();


        if (after != null && before == null) {
            // получаем год из переданного параметра after или before
            Date dateAfter = new Date(after);

            Calendar calendarAfter = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            calendarAfter.setTime(dateAfter);

            int afterYear = calendarAfter.get(Calendar.YEAR);

            int shipProdYear;

            for (Ship x : allShips) {
                // Получаем год создания корабля
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(x.getProdDate());
                shipProdYear = cal.get(Calendar.YEAR);
                if (shipProdYear >= afterYear) {
                    shipsBetweenTwoDates.add(x);
                }
            }
        }

        if (before != null && after == null) {
            // получаем год из переданного параметра after или before
            Date dateBefore = new Date(before);

            Calendar calendarBefore = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            calendarBefore.setTime(dateBefore);

            int beforeYear = calendarBefore.get(Calendar.YEAR);

            int shipProdYear;

            for (Ship x : allShips) {
                // Получаем год создания корабля
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(x.getProdDate());
                shipProdYear = cal.get(Calendar.YEAR);
                if (shipProdYear <= beforeYear) {
                    shipsBetweenTwoDates.add(x);
                }
            }
        }

        if (before != null && after != null) {
            // получаем год из переданного параметра after или before
            Date dateBefore = new Date(before);
            Date dateAfter = new Date(after);

            Calendar calendarBefore = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            Calendar calendarAfter = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));

            calendarBefore.setTime(dateBefore);
            calendarAfter.setTime(dateAfter);

            int beforeYear = calendarBefore.get(Calendar.YEAR);
            int afterYear = calendarAfter.get(Calendar.YEAR);

            int shipProdYear;

            for (Ship x : allShips) {
                // Получаем год создания корабля
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(x.getProdDate());
                shipProdYear = cal.get(Calendar.YEAR);
                // Сравниваем год создания корабля с переданными в функцию параметрами
                if (shipProdYear >= afterYear && shipProdYear <= beforeYear) {
                    shipsBetweenTwoDates.add(x);
                }
            }

        }

        return shipsBetweenTwoDates;
    }

    public List<Ship> getShipsBetweenMinAndMaxSpeedRange (Double minSpeed, Double maxSpeed) {

        List<Ship> allShips = getAllShipsUnfiltered();
        List<Ship> shipsBetweenMinAndMaxSpeedRange = new ArrayList<>();

        if (minSpeed != null && maxSpeed == null) {
            for (Ship x : allShips) {
                if (x.getSpeed() >= minSpeed) {
                    shipsBetweenMinAndMaxSpeedRange.add(x);
                }
            }
        }

        if (maxSpeed != null && minSpeed == null) {
            for (Ship x : allShips) {
                if (x.getSpeed() <= maxSpeed) {
                    shipsBetweenMinAndMaxSpeedRange.add(x);
                }
            }
        }

        if (maxSpeed != null && minSpeed != null) {
            for (Ship x : allShips) {
                if (x.getSpeed() >= minSpeed && x.getSpeed() <= maxSpeed) {
                    shipsBetweenMinAndMaxSpeedRange.add(x);
                }
            }

        }

        return shipsBetweenMinAndMaxSpeedRange;
    }

    @Override
    public List<Ship> getShipsBetweenMinAndMaxCrewSize(List<Ship> ships, Integer minCrewSize, Integer maxCrewSize) {

        List<Ship> shipsBetweenMinAndMaxCrewSize = new ArrayList<>();

        if (minCrewSize != null && maxCrewSize == null) {
            for (Ship x : ships) {
                if (x.getCrewSize() >= minCrewSize) {
                    shipsBetweenMinAndMaxCrewSize.add(x);
                }
            }
        }

        if (maxCrewSize != null && minCrewSize == null) {
            for (Ship x : ships) {
                if (x.getCrewSize() <= maxCrewSize) {
                    shipsBetweenMinAndMaxCrewSize.add(x);
                }
            }
        }

        if (maxCrewSize != null && minCrewSize != null) {
            for (Ship x : ships) {
                if (x.getCrewSize() >= minCrewSize && x.getCrewSize() <= maxCrewSize) {
                    shipsBetweenMinAndMaxCrewSize.add(x);
                }
            }

        }

        return shipsBetweenMinAndMaxCrewSize;
    }


    @Override
    public Ship updateShip(Ship oldShip, Ship newShip) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void deleteShip(Ship ship) {

    }

    @Override
    public List<Ship> sortShipsByOrder(List<Ship> ships, ShipOrder order) {

        /* При первом запуске приложения корабли по-умолчанию выстроены по Order By ID, поэтому если параметр order
        не был передан,то устанавливаем ему значение id, чтобы выстроить корабли по ID при первом запуске приложения. */
        String orderName = new String();
        if (order == null) {
            orderName = "id";
        } else orderName = order.getFieldName();

        // сортируем корабли по скорости
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
            // сортируем корабли по дате производства
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
            // сортируем корабли по рейтингу
            case "rating":
                Collections.sort(ships, new Comparator<Ship>() {
                    @Override
                    public int compare(Ship o1, Ship o2) {
                        return o1.getRating() > o2.getRating() ? 1 : (o1.getRating() < o2.getRating()) ? -1 : 0;
                    }
                });
                break;
            // сортируем корабли по ID
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

        // Функция возвращает подписок кораблей на основе размера страницы и её номера.

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

    @Override
    public List<Ship> getFilteredShips(
            String name,
            String planet,
            ShipType shipType,
            Long after,
            Long before,
            Boolean isUsed,
            Double minSpeed,
            Double maxSpeed,
            Integer minCrewSize,
            Integer maxCrewSize,
            Double minRating,
            Double maxRating,
            ShipOrder order,
            Integer pageNumber,
            Integer pageSize) {

        /* Сначала получаем список кораблей и сортируем этот список по полю Order. После этого
           фильтруем список по другим полям (например, по макс. и мин. скорости). */

        // Получаем весь список кораблей
        List<Ship> allShips = getAllShipsUnfiltered();

        // сортируем по полю Order
        List<Ship> sortedByOrder = sortShipsByOrder(allShips, order);

        // Возвращаем список кораблей, найденных по переданным параметрам
        List<Ship> filteredShips = new ArrayList<>();

        if (name != null) {
            filteredShips = getShipsByFullOrPartialName(name);
            return filteredShips;
        }

        if (planet != null) {
            filteredShips = getShipsByFullOrPartialPlanetName(name);
            return filteredShips;
        }

        // корабли по дате
        if (after != null || before != null) {
            filteredShips = getShipsBetweenProdDates(before, after);
            return filteredShips;
        }


        if (minCrewSize != null || maxCrewSize != null) {
            filteredShips = getShipsBetweenMinAndMaxCrewSize(sortedByOrder, minCrewSize, maxCrewSize);
            return filteredShips;
        }

        // Если параметры переданы не были, то возвращаем все корабли сразу
        else {
            return allShips;
        }
    }
}
