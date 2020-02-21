package main;

import entity.BookCopy;
import entity.Includes;
import entity.Loan;
import query.BookCopyGenerator;
import query.IncludesGenerator;
import query.LoanGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    final LocalDate startDate = LocalDate.of(2019, 1, 1);
    final LocalDate endDate = LocalDate.now();
    final Random rng = new Random(2398476);

    LoanGenerator loanGenerator = new LoanGenerator(150700000, 150700400, 14, 0);
    List<Loan> loans = loanGenerator.generate(100, startDate, endDate);
    List<Loan> unreturnedLoans = loanGenerator.generate(50, endDate.minusDays(14));
    loans.addAll(unreturnedLoans);

    BookCopyGenerator bookCopyGenerator = new BookCopyGenerator(0, 5.50f, 21);
    List<BookCopy> bookCopies = bookCopyGenerator.generate(400);

    IncludesGenerator includesGenerator = new IncludesGenerator(bookCopies,
        loans, 5);

    final List<Includes> includes = includesGenerator.generateRandom();

    bookCopies = updateLoanedBookCopyStatus(bookCopies, loans, includes);

    bookCopies
        .stream()
        .map(BookCopy::toSQLInsert)
        .forEach(System.out::println);
    loans
        .stream()
        .map(Loan::toSQLInsert)
        .forEach(System.out::println);
    includes
        .stream()
        .map(Includes::toSQLInsert)
        .forEach(System.out::println);
  }

  private static List<BookCopy> updateLoanedBookCopyStatus(List<BookCopy> copies,
                                                           List<Loan> loans,
                                                           List<Includes> includes) {
    Map<Long, Loan> loanMap =
        loans.stream()
            .collect(Collectors.toMap(Loan::getLoanId, Function.identity()));

    List<BookCopy> result = new ArrayList<>(loans.size());
    for (BookCopy copy : copies) {
      boolean copyIsOnLoan = isCopyOnLoan(includes, loanMap, copy);
      result.add(updateCopyStatus(copy, copyIsOnLoan));
    }
    return result;
  }

  private static BookCopy updateCopyStatus(BookCopy copy, boolean copyOnLoan) {
    if (copyOnLoan) {
      return copy.setStatus("on loan");
    } else {
      return copy;
    }
  }

  private static boolean isCopyOnLoan(List<Includes> includes, Map<Long, Loan> loanMap, BookCopy copy) {
    return includes.stream()
        .filter(inc -> inc.getBarcodeno().equals(copy.getBarcodeno()))
        .map(Includes::getLoanid)
        .map(loanMap::get)
        .anyMatch(loan -> loan.getReturnDate() == null);
  }
}
