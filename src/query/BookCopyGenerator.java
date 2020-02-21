package query;

import entity.BookCopy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static data.Books.*;
import static data.Branches.*;

public class BookCopyGenerator {
  private final int maxHoldPeriod;
  private int startBarcodeNo;
  private float maxDailyFine;
  private Random rng;

  public BookCopyGenerator(int startBarcodeNo, float maxDailyFine, int maxHoldPeriod) {
    this.startBarcodeNo = startBarcodeNo;
    this.maxDailyFine = maxDailyFine;
    this.maxHoldPeriod = maxHoldPeriod;
    this.rng = new Random();
  }

  public List<BookCopy> generate(int numberToGenerate) {
    List<BookCopy> result = new ArrayList<>(numberToGenerate);
    for (int i = 0; i < numberToGenerate; i++) {
      result.add(makeRandomBook());
    }
    return result;
  }

  private BookCopy makeRandomBook() {
    return new BookCopy(barCodeNoToString(startBarcodeNo++),
        branchNames[rng.nextInt(branchNames.length)],
        bookISBNs[rng.nextInt(bookISBNs.length)],
        "available",
        rng.nextFloat() * maxDailyFine,
        rng.nextInt(maxHoldPeriod) + 1);
  }

  private static String barCodeNoToString(int barcodeNo) {
    String parsedBarcodeNo = Integer.toString(barcodeNo);
    int parsedBarcodeNoLen = parsedBarcodeNo.length();
    char[] padding = new char[12 - parsedBarcodeNoLen];
    Arrays.fill(padding, '0');
    return new String(padding) + parsedBarcodeNo;
  }

}