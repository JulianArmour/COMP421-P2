package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class RandomDateTimeGenerator {

  private static Random rng = new Random(123);

  public static LocalDate between(LocalDate startInclusive,
                                  LocalDate endExclusive) {
    long startEpochDay = startInclusive.toEpochDay();
    long endEpochDay = endExclusive.toEpochDay();
    long randomDay = rng
        .longs(startEpochDay, endEpochDay)
        .findFirst()
        .orElseThrow(
            () -> new RuntimeException("could not generate random date"));
    return LocalDate.ofEpochDay(randomDay);
  }

  public static LocalTime between(LocalTime startIncl, LocalTime endExcl) {
    int startTime = startIncl.toSecondOfDay();
    int endTime = endExcl.toSecondOfDay();
    int randomOffset = rng.nextInt(endTime - startTime);
    return LocalTime.ofSecondOfDay(startTime + randomOffset);
  }
}
