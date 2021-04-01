package io.corecode.mywebsite.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Review;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {
    public List<Review> getAllReviewsByBookId(int bookId) throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "review").asString();

        Type reviewsListType = new TypeToken<ArrayList<Review>>() {
        }.getType();
        Gson gson = new Gson();
        List<Review> list = gson.fromJson(response.getBody(), reviewsListType);
        List<Review> filteredList = new ArrayList<Review>();

        for (Review s : list) {
            if (s.getBookId() == bookId) {
                filteredList.add(s);
            }
        }


        return filteredList;

    }

    public Review getReviewById(int reviewId) throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "review/" + reviewId)
                .asString();
        Review review = new Gson().fromJson(response.getBody(), Review.class);
        return review;
    }

    public int deleteReviewById(int reviewId) throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete(MyConstants.url + "review/" + reviewId)
                .asString();
        return response.getStatus();
    }

    public int updateReview(Review review) throws Exception {
        String temp = review.getDescription().replaceAll("(\\r\\n)", " ");
        review.setDescription(temp);

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put(MyConstants.url + "review/" + review.getReviewId())
                .header("Content-Type", "application/json")
                .body("{\r\n    \"userId\": " + review.getUserId() + ",\r\n    \"bookId\": " + review.getBookId() +
                        ",\r\n    \"stars\": " + review.getStars() + ",\r\n    \"description\": \"" + review.getDescription() + "\"\r\n}")
                .asString();
        return response.getStatus();
    }

    public List<Review> getAllReviews() throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "review").asString();

        Type reviewsListType = new TypeToken<ArrayList<Review>>() {
        }.getType();
        Gson gson = new Gson();
        List<Review> list = gson.fromJson(response.getBody(), reviewsListType);

        return list;

    }

    public int createReview(Review review) throws Exception{
        Unirest.setTimeouts(0, 0);
        String temp = review.getDescription().replaceAll("(\\r\\n)", " ");
        review.setDescription(temp);
        HttpResponse<String> response1 = Unirest.post(MyConstants.url + "review/")
                .header("Content-Type", "application/json")
                .body("{\r\n    \"userId\": " + review.getUserId() + ",\r\n    \"bookId\": " + review.getBookId() + ",\r\n    \"stars\": " + review.getStars() + ",\r\n    \"description\": \"" + review.getDescription() + "\"\r\n}")
                .asString();
        return response1.getStatus();

    }
}
