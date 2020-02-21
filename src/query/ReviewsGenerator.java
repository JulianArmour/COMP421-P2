package query;

import entity.Reviews;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static data.Reviews.*;

public class ReviewsGenerator {
  private Random rng = new Random(123);

  public List<Reviews> generate() {
    return IntStream.range(0, libCardNos.length)
        .filter(i -> rng.nextInt(4) == 0) //25% chance to create review
        .mapToObj(this::makeReview)
        .collect(Collectors.toList());
  }

  private Reviews makeReview(int reviewsDataIndex) {
    return new Reviews(
        libCardNos[reviewsDataIndex],
        isbns[reviewsDataIndex],
        rng.nextInt(6),
        0,
        Date.valueOf(date[reviewsDataIndex]).toLocalDate().plusDays(rng.nextInt(8))
    );
  }
}
