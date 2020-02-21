package query;

import entity.Booking;
import entity.Room;
import util.RandomDateTimeGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static data.Patrons.librarycardno;

public class BookingGenerator {
  private Random rng;
  private List<Room> rooms;
  private LocalTime openingTime;
  private LocalTime closingTime;
  private LocalDate startDate;
  private LocalDate endDate;
  private long maxReservationDurationHours;

  public BookingGenerator(List<Room> rooms, LocalTime openingTime, LocalTime closingTime, LocalDate startDate, LocalDate endDate, long maxReservationTimeHrs) {
    this.rooms = rooms;
    this.openingTime = openingTime;
    this.closingTime = closingTime;
    this.startDate = startDate;
    this.endDate = endDate;
    this.maxReservationDurationHours = maxReservationTimeHrs;
    rng = new Random(123);
  }

  public List<Booking> generate(int count) {
    List<Booking> result = new ArrayList<>(count);
    for (int curCount = 0; curCount < count; curCount++) {
      result.add(makeRandomBooking());
    }
    return removeConflicts(result);
  }

  private Booking makeRandomBooking() {
    int patronLibCardNo = librarycardno[rng.nextInt(librarycardno.length)];
    Room room = rooms.get(rng.nextInt(rooms.size()));
    LocalDate resDate = RandomDateTimeGenerator.between(startDate,
        endDate.plusDays(1));
    LocalTime resTime = RandomDateTimeGenerator.between(
        openingTime,
        closingTime.minusHours(maxReservationDurationHours)
    );
    LocalTime formattedResTime = resTime
        .withMinute(rng.nextBoolean() ? 30 : 0)
        .withSecond(0);
    int duration = 1 + rng.nextInt((int) maxReservationDurationHours);

    return new Booking(patronLibCardNo, room.getBranch(), room.getRoomNo(),
        resDate, formattedResTime, duration);
  }

  private static List<Booking> removeConflicts(List<Booking> bookings) {
    List<Booking> result = new ArrayList<>(bookings.size());
    for (Booking booking :
        bookings) {
      if (!conflicts(booking, result)) {
        result.add(booking);
      }
    }
    return result;
  }

  private static boolean conflicts(Booking booking, List<Booking> bookings) {
    return
      bookings.stream()
              .anyMatch(booking::conflicts);
  }
}
