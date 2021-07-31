package com.example.explorercalifornia.service;


import com.example.explorercalifornia.model.Difficulty;
import com.example.explorercalifornia.model.Region;
import com.example.explorercalifornia.model.Tour;
import com.example.explorercalifornia.model.TourPackage;
import com.example.explorercalifornia.repo.TourPackageRepository;
import com.example.explorercalifornia.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService
{
    TourRepository tourRepository;
    TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository,TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository=tourPackageRepository;
    }

    public Tour createTour(String title, String description, String blurb, Integer price,
                           String duration, String bullets,
                           String keywords, String tourPackageName, Difficulty difficulty, Region region ) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(()->
                new RuntimeException("Tour package does not exist: " + tourPackageName));

        return tourRepository.save(new Tour(title, description,blurb, price, duration,
                bullets, keywords, tourPackage, difficulty, region));
    }

    public long total()
    {
        return tourRepository.count();
    }

    public List<Tour> getAllTours()
    {
        return (List<Tour>) tourRepository.findAll();
    }
}
