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

    sudoku = Sudoku.generatePuzzle();
    window = new GUI(sudoku);


  }
}
