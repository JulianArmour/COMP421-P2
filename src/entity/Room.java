package entity;

public class Room {
  private String branch;
  private String roomNo;
  private int status;
  private int capacity;
  private boolean hasWhiteBoard;

  public Room(String branch, String roomNo, int status, int capacity, boolean hasWhiteBoard) {
    this.branch = branch;
    this.roomNo = roomNo;
    this.status = status;
    this.capacity = capacity;
    this.hasWhiteBoard = hasWhiteBoard;
  }

  public String getBranch() {
    return branch;
  }

  public String getRoomNo() {
    return roomNo;
  }

  public int getStatus() {
    return status;
  }

  public int getCapacity() {
    return capacity;
  }

  public boolean isHasWhiteBoard() {
    return hasWhiteBoard;
  }

  @Override
  public String toString() {
    return "Room{" +
        "branch='" + branch + '\'' +
        ", roomNo='" + roomNo + '\'' +
        ", status=" + status +
        ", capacity=" + capacity +
        ", hasWhiteBoard=" + hasWhiteBoard +
        '}';
  }

  public String toSQLInsert() {
    return
        "INSERT INTO room (branch, roomno, status, capacity, haswhiteboard) " +
        "VALUES ('" +
        branch + "', " +
        roomNo + ", " +
        status + ", " +
        capacity + ", " +
        hasWhiteBoard + ");";
  }
}
