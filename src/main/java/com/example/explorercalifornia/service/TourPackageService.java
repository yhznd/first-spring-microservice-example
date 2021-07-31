package com.example.explorercalifornia.service;

import com.example.explorercalifornia.model.TourPackage;
import com.example.explorercalifornia.repo.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourPackageService
{
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    public TourPackage createTourPackage(String code, String name)
    {

        return tourPackageRepository.findById(code).orElse(tourPackageRepository.save(new TourPackage(code,name)));
        //if we cant find, then we gonna create new TourPackage
    }

    private Iterable<TourPackage> lookup()
    {
        return tourPackageRepository.findAll();
    }

    public long total()
    {
        return tourPackageRepository.count();
    }
}
