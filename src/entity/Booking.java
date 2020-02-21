package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
  private int librarycardno;
  private String branchname;
  private String roomno;
  private LocalDate startdate;
  private LocalTime startTime;
  private int duration;

  public Booking(int librarycardno, String branchname, String roomno,
                 LocalDate startdate, LocalTime starttime, int duration) {
    this.librarycardno = librarycardno;
    this.branchname = branchname;
    this.roomno = roomno;
    this.startdate = startdate;
    this.startTime = starttime;
    this.duration = duration;
  }

  public String toSQLInsert() {
    return
        "INSERT INTO booking (librarycardno, branchname, roomno, startdate, " +
            "duration, starttime) " +
            "VALUES (" +
            librarycardno + ", '" +
            branchname + "', '" +
            roomno + "', '" +
            startdate + "', " +
            duration + ", '" +
            startTime + "');";
  }

  public boolean conflicts(Booking other) {
    if (!startdate.equals(other.startdate)
    || !roomno.equals(other.getRoomno()))
      return false;
    return !other.getStartTime().isAfter(startTime.plusHours(duration))
        && !startTime.isAfter(other.getStartTime().plusHours(other.getDuration()));
  }

  public int getLibrarycardno() {
    return librarycardno;
  }

  public String getBranchname() {
    return branchname;
  }

  public String getRoomno() {
    return roomno;
  }

  public LocalDate getStartdate() {
    return startdate;
  }

  public int getDuration() {
    return duration;
  }

  public LocalTime getStartTime() {
    return startTime;
  }
}
