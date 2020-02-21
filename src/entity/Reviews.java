package entity;

import java.time.LocalDate;

public class Reviews {
  private int librarycardno;
  private String isbn;
  private int rating;
  private int privacy;
  private LocalDate date;

  public Reviews(int librarycardno, String isbn, int rating, int privacy, LocalDate date) {
    this.librarycardno = librarycardno;
    this.isbn = isbn;
    this.rating = rating;
    this.privacy = privacy;
    this.date = date;
  }

  public String toSLQInsert() {
    return
        "INSERT INTO reviews (librarycardno, isbn, rating, privacy, date) " +
        "VALUES (" +
        librarycardno + ", '" +
        isbn + "', " +
        rating + ", " +
        privacy + ", '" +
        date + "');";
  }
}
