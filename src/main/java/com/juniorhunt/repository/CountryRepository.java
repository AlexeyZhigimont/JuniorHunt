package com.juniorhunt.repository;

import com.juniorhunt.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCountry(String country);

    List<Country> findAll();
}
