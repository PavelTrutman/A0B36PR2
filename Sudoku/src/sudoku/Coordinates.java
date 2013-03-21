package Sudoku;

/**
 * Contains coordinates of items in sudoku.
 *
 * <p>This class enables saving coordinates of items in sudoku. It may
 * contains only valid combinations, i.e. numbers 0-8.</p>
 *
 * @author Pavel Trutman
 */
public class Coordinates {
  /**
   * Coordinates
   */
  private int x;
  private int y;

  /**
   * Creates valid combination of coordinates.
   *
   * @param x X-axis coordinate (values 0-8)
   * @param y Y-axis coordinate (values 0-8)
   * @throws RuntimeException if values are not 0-8
   */
  public Coordinates(int x, int y) throws RuntimeException {
    this.set(x, y);
  }

  /**
   * Return X-axis coordinate.
   *
   * @return X-axis coordinate
   */
  public int getX() {
    return this.x;
  }

  /**
   * Vrací Y-axis coordinate.
   *
   * @return Y-axis coordinate
   */
  public int getY() {
    return this.y;
  }

  /**
   * Sets new coordinates.
   *
   * @param x X-axis coordinate
   * @param y Y-axis coordinate
   * @throws RuntimeException if values are not 0-8
   */
  public void set(int x, int y) throws RuntimeException {
    //are coordinates valid?
    if(x >= 0 & x < 9) {
      this.x = x;
    }
    else {
      throw new RuntimeException();
    }
    if(y >= 0 & y < 9) {
      this.y = y;
    }
    else {
      throw new RuntimeException();
    }
  }
}
