package entity;

public class Includes {
  private long loanid;
  private String barcodeno;

  public Includes(long loanid, String barcodeno) {
    if (barcodeno.length() != 12) {
      throw new RuntimeException("constraint violated");
    }
    this.loanid = loanid;
    this.barcodeno = barcodeno;
  }

  public long getLoanid() {
    return loanid;
  }

  public String getBarcodeno() {
    return barcodeno;
  }

  public String toSQLInsert() {
    return
        "INSERT INTO includes (loanid, barcodeno) " +
            "VALUES (" +
            loanid + ", '" +
            barcodeno + "');";
  }

  @Override
  public String toString() {
    return "Includes{" +
        "loanid=" + loanid +
        ", barcodeno='" + barcodeno + '\'' +
        '}';
  }
}
