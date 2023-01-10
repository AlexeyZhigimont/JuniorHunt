package com.juniorhunt.service;

import com.juniorhunt.model.Country;
import com.juniorhunt.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    public List<Country> findAll(){
        return countryRepository.findAll();
    }
}
