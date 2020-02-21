package main;

import entity.Booking;
import entity.Room;
import query.BookingGenerator;
import query.RoomGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RoomsAndBookings {
  public static void main(String[] args) {
    List<Room> rooms = new RoomGenerator().generate();

    BookingGenerator bookGen = new BookingGenerator(
        rooms,
        LocalTime.of(7, 0),
        LocalTime.of(23, 0),
        LocalDate.of(2020, 2, 24),
        LocalDate.of(2020, 3, 7),
        3
    );
    List<Booking> bookings = bookGen.generate(200);
    bookings
        .stream()
        .map(Booking::toSQLInsert)
        .forEach(System.out::println);
  }
}
