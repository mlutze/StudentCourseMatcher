package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.object.Qualification;
import com.gmail.mcdlutze.studentcoursematcher.object.SeatType;
import com.gmail.mcdlutze.studentcoursematcher.parser.Ternean;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SeatTypesManager {
    private final Map<String, SeatType> seatTypes;

    public SeatTypesManager(Map<String, Map<String, Ternean>> seatTypes,
                            QualificationsManager qualificationsManager) {
        this.seatTypes = seatTypes.entrySet().stream().collect(Collectors
                .toMap(Map.Entry::getKey, e -> createSeatType(e.getKey(), e.getValue(), qualificationsManager)));
    }

    public SeatType getSeatType(String key) {
        return seatTypes.get(key);
    }

    private SeatType createSeatType(String name, Map<String, Ternean> qualifications,
                                    QualificationsManager qualificationsManager) {
        Set<Qualification> required =
                qualifications.entrySet().stream().filter(e -> e.getValue().equals(Ternean.TRUE)).map(Map.Entry::getKey)
                        .map(qualificationsManager::getQualification).collect(Collectors.toSet());
        Set<Qualification> forbidden =
                qualifications.entrySet().stream().filter(e -> e.getValue().equals(Ternean.FALSE))
                        .map(Map.Entry::getKey).map(qualificationsManager::getQualification)
                        .collect(Collectors.toSet());
        return new SeatType(name, required, forbidden);
    }
}
