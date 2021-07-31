package com.example.explorercalifornia.controller;

import com.example.explorercalifornia.model.Tour;
import com.example.explorercalifornia.model.TourRating;
import com.example.explorercalifornia.model.TourRatingPk;
import com.example.explorercalifornia.repo.TourRatingRepository;
import com.example.explorercalifornia.repo.TourRepository;
import com.example.explorercalifornia.web.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/tours/{tourId}/ratings")
public class TourRatingController
{
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    //It is better style to inject service but trying to keep this example simple
    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController() {
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDTO ratingDto) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(new TourRatingPk(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(), ratingDto.getComment()));
    }


    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist " + tourId));
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }
}
