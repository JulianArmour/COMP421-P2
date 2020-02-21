package entity;

import java.text.DecimalFormat;

public class BookCopy {
  private String barcodeno;
  private String homebranch;
  private String copyof;
  private String status;
  private float dailyfinerate;
  private int holdperiod;

  public BookCopy(String barcodeno, String homebranch, String copyof, String status,
                  float dailyfinerate, int holdperiod) {
    if (barcodeno.length() != 12
    || homebranch.length() > 70
    || copyof.length() != 13
    || status.length() > 10) {
      throw new RuntimeException("Invalid constraints");
    }
    this.barcodeno = barcodeno;
    this.homebranch = homebranch;
    this.copyof = copyof;
    this.status = status;
    this.dailyfinerate = dailyfinerate;
    this.holdperiod = holdperiod;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BookCopy) {
      return ((BookCopy) obj).getBarcodeno().equals(barcodeno);
    }
    return super.equals(obj);
  }

  public String toSQLInsert() {
    DecimalFormat formatter = new DecimalFormat("#.##");
    return
        "INSERT INTO book_copy (barcodeno, homebranch, copyof, " +
            "status, dailyfinerate, holdperiod) " +
            "VALUES ('" +
            barcodeno + "', '" +
            homebranch + "', '" +
            copyof + "', '" +
            status + "', " +
            formatter.format(dailyfinerate) + ", " +
            holdperiod + ");";
  }

  public String getBarcodeno() {
    return barcodeno;
  }

  public String getHomebranch() {
    return homebranch;
  }

  public String getCopyof() {
    return copyof;
  }

  public String getStatus() {
    return status;
  }

  public BookCopy setStatus(String status) {
    return new BookCopy(barcodeno, homebranch, copyof, status, dailyfinerate,
        holdperiod);
  }

  public float getDailyfinerate() {
    return dailyfinerate;
  }

  public int getHoldperiod() {
    return holdperiod;
  }

  @Override
  public String toString() {
    return "BookCopy{" +
        "barcodeno='" + barcodeno + '\'' +
        ", homebranch='" + homebranch + '\'' +
        ", copyof='" + copyof + '\'' +
        ", status='" + status + '\'' +
        ", dailyfinerate=" + dailyfinerate +
        ", holdperiod=" + holdperiod +
        '}';
  }
}
