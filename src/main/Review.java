package main;

import entity.Reviews;
import query.ReviewsGenerator;

public class Review {
  public static void main(String[] args) {
    new ReviewsGenerator()
        .generate()
        .stream()
        .map(Reviews::toSLQInsert)
        .forEach(System.out::println);
  }
}
