package query;

import entity.BookCopy;
import entity.Includes;
import entity.Loan;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IncludesGenerator {
  private List<BookCopy> bookCopies;
  private Map<Long, Loan> loans;
  private int maxLoanedCopies;
  private Random rng;

  public IncludesGenerator(List<BookCopy> bookCopies, List<Loan> loans, int maxLoanedCopies) {
    this.bookCopies = bookCopies;
    this.loans = loans.stream()
        .collect(Collectors.toMap(Loan::getLoanId,
            Function.identity()));
    this.maxLoanedCopies = maxLoanedCopies;
    this.rng = new Random();
  }

  private static boolean overlaps(Loan l1, Loan l2) {
    final Date today = Date.valueOf(LocalDate.now());
    if (l1.getReturnDate() == null)
      l1 = l1.setReturnDate(today);
    if (l2.getReturnDate() == null)
      l2 = l2.setReturnDate(today);

    return !l1.getReturnDate().before(l2.getCheckoutDate())
        && !l1.getCheckoutDate().after(l2.getReturnDate());
  }

  public List<Includes> generateRandom() {
    List<Includes> result = new LinkedList<>();

    List<Integer> bookCopyIdsToShuffle = IntStream.range(0, bookCopies.size())
        .boxed()
        .collect(Collectors.toList());

    for (Loan loan : loans.values()) {
      int numberOfLoanedCopies = 1 + rng.nextInt(maxLoanedCopies);
      //attempt to include 'numberOfLoanedCopies' book copies that don't
      // conflict with the loan-bookCopy pairs in 'result'
      Collections.shuffle(bookCopyIdsToShuffle);

      bookCopyIdsToShuffle.stream()
          .map(bookCopies::get)
          .filter(bookCopy -> !loanConflicts(loan, bookCopy, result))
          .map(bookCopy -> new Includes(loan.getLoanId(), bookCopy.getBarcodeno()))
          .limit(numberOfLoanedCopies)
          .forEach(result::add);
    }
    return result;
  }

  /*returns true of there's already another loan that has the book copy already
  checked out*/
  private boolean loanConflicts(Loan loanToCheck,
                                BookCopy copyToCheck,
                                List<Includes> includes) {
    final Predicate<Includes> includesCopyToCheck =
        include -> include.getBarcodeno().equals(copyToCheck.getBarcodeno());
    return includes.stream()
        .filter(includesCopyToCheck)
        .map(Includes::getLoanid)
        .filter(loans::containsKey)
        .map(loans::get)
        .anyMatch(loan -> overlaps(loan, loanToCheck));
  }
}
