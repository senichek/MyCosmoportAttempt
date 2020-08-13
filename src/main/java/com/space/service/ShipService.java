package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.Date;
import java.util.List;

public interface ShipService {

    // Получаем список кораблей без какой-либо фильтрации или сортировки
    List<Ship> getAllShipsUnfiltered();

    Ship saveShip(Ship ship);

    Ship getShip(Long id);

    List<Ship> getShipsByFullOrPartialName(String name, List<Ship> ships);

    List<Ship> getShipsByFullOrPartialPlanetName(String name, List<Ship> ships);

    public List<Ship> getShipsBetweenProdDates(Long before, Long after, List<Ship> ships);

    public List<Ship> getShipsBetweenMinAndMaxCrewSize(Integer minCrewSize, Integer maxCrewSize, List<Ship> ships);

    public List<Ship> getShipsBetweenMinAndMaxSpeedRange(Double minSpeed, Double maxSpeed, List<Ship> ships);

    public List<Ship> getShipsBetweenMinAndMaxRating(Double minRating, Double maxRating, List<Ship> ships);

    public List<Ship> getShipsByType(ShipType type, List<Ship> ships);

    public List<Ship> getShipsByUsedUnusedParam(boolean used, List <Ship> ships);

    Ship updateShip(Ship oldShip, Ship newShip) throws IllegalArgumentException;

    void deleteShip(Ship ship);

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
            Integer pageSize
    );

    List<Ship> sortShipsByOrder(List<Ship> ships, ShipOrder order);

    List<Ship> getSublistBasedOnPageSizeAndPageNumber(List<Ship> ships, Integer pageNumber, Integer pageSize);

    boolean isShipValid(Ship ship);

    double computeRating(double speed, boolean isUsed, Date prod);
}
