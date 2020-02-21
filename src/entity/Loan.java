package entity;

import java.sql.Date;

public class Loan {
  private long loanId;
  private int libraryCardNo;
  private Date checkoutDate;
  private Date returnDate;

  public Loan(long loanId, int libraryCardNo, Date checkoutDate,
              Date returnDate) {
    this.loanId = loanId;
    this.libraryCardNo = libraryCardNo;
    this.checkoutDate = checkoutDate;
    this.returnDate = returnDate;
  }

  public String toSQLInsert() {
    if (returnDate == null) {
      return
          "INSERT INTO loan (loanid, librarycardno, checkoutdate) " +
              "VALUES (" +
              loanId + ", " +
              libraryCardNo + ", '" +
              checkoutDate + "');";
    }
    return
        "INSERT INTO loan (loanid, librarycardno, checkoutdate, returndate) " +
            "VALUES (" +
             loanId + ", " +
            libraryCardNo + ", '" +
            checkoutDate + "', '" +
            returnDate + "');";
  }

  public int getLibraryCardNo() {
    return libraryCardNo;
  }

  public Date getCheckoutDate() {
    return checkoutDate;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  public Loan setReturnDate(Date returnDate) {
    return new Loan(loanId, libraryCardNo, checkoutDate, returnDate);
  }

  public long getLoanId() {
    return loanId;
  }

  @Override
  public String toString() {
    return "Loan{" +
        "loanId=" + loanId +
        ", libraryCardNo=" + libraryCardNo +
        ", checkoutDate=" + checkoutDate +
        ", returnDate=" + returnDate +
        '}';
  }
}
