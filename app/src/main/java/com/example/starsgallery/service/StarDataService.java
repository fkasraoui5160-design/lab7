package com.example.starsgallery.service;

import com.example.starsgallery.adapter.GalleryStarAdapter;
import com.example.starsgallery.dao.IDaoContract;
import java.util.ArrayList;
import java.util.List;

public class StarDataService implements IDaoContract<GalleryStarAdapter.StarModel> {

    private List<GalleryStarAdapter.StarModel> starCollection;
    private static StarDataService singletonInstance;

    private StarDataService() {
        starCollection = new ArrayList<>();
        populateInitialData();
    }

    public static StarDataService getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new StarDataService();
        }
        return singletonInstance;
    }

    private void populateInitialData() {
        starCollection.add(new GalleryStarAdapter.StarModel("Emma Watson",
                "https://image.tmdb.org/t/p/w500/7GXYwIBRxVj8L7Bo2ZrHr6m7m4F.jpg",
                4.5f));
        starCollection.add(new GalleryStarAdapter.StarModel("Tom Cruise",
                "https://image.tmdb.org/t/p/w500/8qBylBsQf4llkGrWR3qAsOtOU8O.jpg",
                4.2f));
        starCollection.add(new GalleryStarAdapter.StarModel("Scarlett Johansson",
                "https://image.tmdb.org/t/p/w500/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg",
                4.7f));
        starCollection.add(new GalleryStarAdapter.StarModel("Leonardo DiCaprio",
                "https://image.tmdb.org/t/p/w500/woVhK2XhMxWt0bBQkFgLJm9Bk3P.jpg",
                4.8f));
        starCollection.add(new GalleryStarAdapter.StarModel("Jennifer Lawrence",
                "https://image.tmdb.org/t/p/w500/3Ed6nG8eHn2xVzP8lFk8G9vW7Yr.jpg",
                4.3f));
        starCollection.add(new GalleryStarAdapter.StarModel("Robert Downey Jr",
                "https://image.tmdb.org/t/p/w500/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg",
                4.9f));
        starCollection.add(new GalleryStarAdapter.StarModel("Angelina Jolie",
                "https://image.tmdb.org/t/p/w500/k3W1XXddDOH2zibHk5gI3bL4pN.jpg",
                4.6f));
        starCollection.add(new GalleryStarAdapter.StarModel("Brad Pitt",
                "https://image.tmdb.org/t/p/w500/cckcYc2v0yhGtc9QZRel7PJcWnL.jpg",
                4.4f));
        starCollection.add(new GalleryStarAdapter.StarModel("Zendaya",
                "https://image.tmdb.org/t/p/w500/3HIK4n9k2jZ9m9B5JQkL8g5nM7V.jpg",
                4.7f));
        starCollection.add(new GalleryStarAdapter.StarModel("Dwayne Johnson",
                "https://image.tmdb.org/t/p/w500/5Brc3H9Vk4Xm8rL1J8hKj5YxP7W.jpg",
                4.8f));
        starCollection.add(new GalleryStarAdapter.StarModel("Gal Gadot",
                "https://image.tmdb.org/t/p/w500/8uW3hSx7k9M2qL4nN6vK1jR5pB8.jpg",
                4.6f));
        starCollection.add(new GalleryStarAdapter.StarModel("Chris Evans",
                "https://image.tmdb.org/t/p/w500/3bOGNsH5U5m4vJ2k9L8xP7qR6tY.jpg",
                4.5f));
    }

    @Override
    public boolean insert(GalleryStarAdapter.StarModel element) {
        return starCollection.add(element);
    }

    @Override
    public boolean modify(GalleryStarAdapter.StarModel element) {
        for (GalleryStarAdapter.StarModel current : starCollection) {
            if (current.getUniqueId() == element.getUniqueId()) {
                current.setFullName(element.getFullName());
                current.setPhotoUrl(element.getPhotoUrl());
                current.setUserRating(element.getUserRating());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(GalleryStarAdapter.StarModel element) {
        return starCollection.remove(element);
    }

    @Override
    public GalleryStarAdapter.StarModel fetchById(int identifier) {
        for (GalleryStarAdapter.StarModel current : starCollection) {
            if (current.getUniqueId() == identifier) {
                return current;
            }
        }
        return null;
    }

    @Override
    public List<GalleryStarAdapter.StarModel> fetchAll() {
        return starCollection;
    }
}