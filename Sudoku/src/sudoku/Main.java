package sudoku;

/**
 * @author Pavel Trutman
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    GUI window;
    Sudoku sudoku;

    sudoku = new Sudoku();

    //insert some testing values
    sudoku.insert(new Coordinates(1, 1), new Value(3, false));
    sudoku.insert(new Coordinates(5, 3), new Value(9, true));

    window = new GUI(sudoku);


  }
}
