package query;

import data.Branches;
import entity.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static data.Branches.*;

public class RoomGenerator {
  private Random rng;

  public RoomGenerator() {
    this.rng = new Random(123);
  }

  public List<Room> generate() {
    List<Room> result = new ArrayList<>(branchNames.length);
    for (int i = 0; i < branchNames.length; i++) {
      for (int j = 1; j <= roomsToReserve[i]; j++) {
        result.add(createRandomRoom(branchNames[i], j));
      }
    }
    return result;
  }

  private Room createRandomRoom(String branch, int roomNo) {
    return new Room(
        branch,
        String.valueOf(roomNo),
        0,
        1 + rng.nextInt(6),
        rng.nextBoolean()
    );
  }
}
