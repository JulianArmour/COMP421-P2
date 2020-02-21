package query;

import entity.Loan;
import util.RandomDateTimeGenerator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LoanGenerator {
  private Random rng;
  private int patronIDStartIncl;
  private int patronIDEndExcl;
  private int maxLoanPeriod;
  private long startLoanID;//10000000

  public LoanGenerator(int patronIDStartIncl, int patronIDEndExcl, int maxLoanPeriod, long startLoanID) {
    this.rng = new Random();
    this.patronIDStartIncl = patronIDStartIncl;
    this.patronIDEndExcl = patronIDEndExcl;
    this.maxLoanPeriod = maxLoanPeriod;
    this.startLoanID = startLoanID;
  }

  public List<Loan> generate(int nLoans,
                             LocalDate minTakeOutDate,
                             LocalDate maxReturnDate) {
    return rng.ints(nLoans, patronIDStartIncl, patronIDEndExcl)
        .mapToObj(id -> createRandomLoan(id, minTakeOutDate, maxReturnDate,
            maxLoanPeriod))
        .collect(Collectors.toList());
  }

  /*Generates loans that have not yet been returned to the library*/
  public List<Loan> generate(int nLoans, LocalDate minTakeOutDate) {
    List<Loan> generatedLoansWithReturn = generate(nLoans,
        minTakeOutDate, LocalDate.now());

    return generatedLoansWithReturn
        .stream()
        .map(loan -> loan.setReturnDate(null))
        .collect(Collectors.toList());
  }

  private Loan createRandomLoan(int patronCardNo, LocalDate startBound,
                                LocalDate endBound, int maxLoanPeriod) {
    LocalDate loanDate = RandomDateTimeGenerator.between(startBound, endBound);
    LocalDate latestReturn = loanDate.plusDays(maxLoanPeriod);
    LocalDate returnDate = RandomDateTimeGenerator.between(loanDate, latestReturn);
    return new Loan(startLoanID++, patronCardNo, Date.valueOf(loanDate),
        Date.valueOf(returnDate));
  }
}
