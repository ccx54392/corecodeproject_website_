package io.corecode.mywebsite.controller;


import io.corecode.mywebsite.model.Review;
import io.corecode.mywebsite.model.User;
import io.corecode.mywebsite.repository.BookRepository;
import io.corecode.mywebsite.repository.ReviewRepository;
import io.corecode.mywebsite.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ReviewController {

    @PostMapping("saveReview")
    public String saveReview(@ModelAttribute("review") Review review, Map<String, Object> model) throws Exception {

        model.put("userId", review.getUserId());
        model.put("bookId", review.getBookId());

        model.put("book", new BookRepository().getBookById(review.getBookId()));

        int status = new ReviewRepository().createReview(review);

        review.setDescription("");

        model.put("book", new BookRepository().getBookById(review.getBookId()));

        if (status == 201) {
            model.put("message", "Your review was successfully added");

        } else {
            model.put("message", "Error contacting the backend service");
        }

        return "book";

    }

    @GetMapping("deleteReview")
    public String deleteReview(@RequestParam("reviewId") int reviewId, @RequestParam("bookId") int bookId, @RequestParam("userId") int userId, @ModelAttribute("review") Review review, Map<String, Object> model) throws Exception {
        int status = new ReviewRepository().deleteReviewById(reviewId);
        model.put("bookId", bookId);
        model.put("userId", userId);
        model.put("book", new BookRepository().getBookById(bookId));
        if (status == 200) {
            model.put("message", "Review was deleted successfully");
        } else {
            model.put("message", "Error contacting the backend service");
        }
        return "book";
    }

    @GetMapping("deleteReviewOnList")
    public String deleteReview(@RequestParam("reviewId") int reviewId, @RequestParam("bookId") int bookId, @RequestParam("userId") int userId, Map<String, Object> model) throws Exception {
        int status = new ReviewRepository().deleteReviewById(reviewId);
        User user = new UserRepository().getUserById(userId);
        model.put("user", user);
        model.put("bookId", bookId);
        model.put("userId", userId);
        if (status == 200) {
            model.put("message", "Review was deleted successfully");
        } else {
            model.put("message", "Error contacting the backend service");
        }
        return "manage_reviews_user";
    }

    @GetMapping("updateReview")
    public String updateReview(@RequestParam("reviewId") int reviewId, @RequestParam("bookId") int bookId, @RequestParam("userId") int userId, @ModelAttribute("review") Review review, Map<String, Object> model) throws Exception {
        model.put("bookId", bookId);
        model.put("userId", userId);

        Review dataReview = new ReviewRepository().getReviewById(reviewId);
        model.put("book", dataReview.getBook());

        review.setDescription(dataReview.getDescription());
        review.setReviewId(dataReview.getReviewId());
        review.setBookId(dataReview.getBookId());
        review.setStars(dataReview.getStars());
        review.setUserId(dataReview.getUserId());

        List<Integer> starsList = new ArrayList<>();
        starsList.add(1);
        starsList.add(2);
        starsList.add(3);
        starsList.add(4);
        starsList.add(5);
        for (int i = 0; i < starsList.size(); i++) {
            if (review.getStars() == starsList.get(i)) {
                Integer temp = starsList.get(i);
                starsList.remove(i);
                starsList.add(temp);
            }
        }
        model.put("starsList", starsList);

        return "manage_reviews_user_update_review";
    }

    @PostMapping("updateReview")
    public String updateReview(@ModelAttribute("review") Review review, Map<String, Object> model) throws Exception {

        model.put("bookId", review.getBookId());
        model.put("userId", review.getUserId());

        model.put("book", new BookRepository().getBookById(review.getBookId()));

        int status = new ReviewRepository().updateReview(review);

        List<Integer> starsList = new ArrayList<>();
        starsList.add(1);
        starsList.add(2);
        starsList.add(3);
        starsList.add(4);
        starsList.add(5);
        for (int i = 0; i < starsList.size(); i++) {
            if (review.getStars() == starsList.get(i)) {
                Integer temp = starsList.get(i);
                starsList.remove(i);
                starsList.add(temp);
            }
        }
        model.put("starsList", starsList);

        if (status == 200) {
            model.put("message", "Review was updated successfully");
        } else {
            model.put("message", "Error connecting to the backend service");
        }

        return "manage_reviews_user_update_review";
    }

    @GetMapping("deleteReviewListAdmin")
    public String deleteReviewListAdmin(Map<String, Object> model) throws Exception {
        model.put("reviews", new ReviewRepository().getAllReviews());

        return "manage_reviews_admin";
    }

    @GetMapping("deleteReviewAdmin")
    public String deleteReview(@RequestParam("reviewId") int reviewId, Map<String, Object> model) throws Exception {
        int status = new ReviewRepository().deleteReviewById(reviewId);
        model.put("reviews", new ReviewRepository().getAllReviews());
        if (status == 200) {
            model.put("message", "Review was deleted successfully");
        } else {
            model.put("message", "Error contacting the backend service");
        }
        return "manage_reviews_admin";
    }

}
