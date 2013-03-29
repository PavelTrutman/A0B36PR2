package sudoku;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Graphical user interface.
 *
 * <p>Creates a graphical user interface to interact with user.</p>
 *
 * @author Pavel Trutman
 */
public class GUI extends JFrame {

  /*
   * Sudoku
   */
  private Sudoku sudoku;

  /**
   * Components
   */
  private Container container;
  private GridBagLayout bigGrid;
  private GridBagLayout smallGrid;
  private GridBagConstraints bigGridCon;
  private GridBagConstraints smallGridCon;
  private TextField[][] inputs;
  private JPanel[][] bigSquares;
  private JPanel sud;

  /*
   * Menu items
   */
  private JMenuBar menuBar;
  private JMenu gameMenu;
  private JMenuItem exitGame;
  private JMenuItem newGame;
  private JMenuItem restartGame;
  private JMenuItem nextMoveGame;
  private JMenuItem solveGame;
  private JMenuItem saveGame;
  private JMenuItem loadGame;
  private JMenu aboutMenu;
  private JMenuItem gameAbout;
  private JMenuItem authorAbout;

  /*
   * Dimension and border
   */
  private Dimension Dim;
  private Border bord;

  /*
   * Grid sizes
   */
  private int[] bigGridSize = {120, 120, 120};
  private int[] smallGridSize = {37, 37, 37};


  /**
   * Inits GUI.
   */
  public GUI (Sudoku sudoku){
    super("Sudoku");

    int i, j;

    //set sudoku
    this.sudoku = sudoku;

    //set border
    bord = new LineBorder(Color.BLACK, 1, false);

    //INIT WINDOW
    Dim = new Dimension(500, 500);
    this.setSize(Dim);
    this.setMaximumSize(Dim);
    this.setMinimumSize(Dim);
    this.setBounds(100, 100, 500, 500);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //MENUS
    menuBar = new JMenuBar();

    //game menu
    gameMenu = new JMenu();
    gameMenu.setText("Game");
    menuBar.add(gameMenu);

    newGame = new JMenuItem();
    newGame.setText("New");
    gameMenu.add(newGame);

    restartGame = new JMenuItem();
    restartGame.setText("Restart");
    gameMenu.add(restartGame);

    nextMoveGame = new JMenuItem();
    nextMoveGame.setText("Next move");
    gameMenu.add(nextMoveGame);

    solveGame = new JMenuItem();
    solveGame.setText("Solve");
    gameMenu.add(solveGame);

    gameMenu.addSeparator();

    saveGame = new JMenuItem();
    saveGame.setText("Save");
    gameMenu.add(saveGame);

    loadGame = new JMenuItem();
    loadGame.setText("Load");
    gameMenu.add(loadGame);

    gameMenu.addSeparator();

    exitGame = new JMenuItem();
    exitGame.setText("Exit");
    gameMenu.add(exitGame);

    //about menu
    aboutMenu = new JMenu();
    aboutMenu.setText("About");
    menuBar.add(aboutMenu);

    gameAbout = new JMenuItem();
    gameAbout.setText("About game");
    aboutMenu.add(gameAbout);

    authorAbout = new JMenuItem();
    authorAbout.setText("About auhtor");
    aboutMenu.add(authorAbout);

    setJMenuBar(menuBar);


    //GRID 3x3 INIT
    bigGrid = new GridBagLayout();
    bigGrid.columnWidths = bigGridSize;
    bigGrid.rowHeights = bigGridSize;
    bigGridCon = new GridBagConstraints();
    bigGridCon.fill = GridBagConstraints.BOTH;

    //GRID 9x9 INIT
    smallGrid = new GridBagLayout();
    smallGrid.columnWidths = smallGridSize;
    smallGrid.rowHeights = smallGridSize;
    smallGridCon = new GridBagConstraints();
    smallGridCon.fill = GridBagConstraints.BOTH;
    smallGridCon.insets = new Insets(2, 2, 2, 2);

    //WRAP INIT
    sud = new JPanel();
    sud.setLayout(bigGrid);
    sud.setBorder(bord);

    //CONTAINER INIT
    container = getContentPane();
    container.setLayout(new GridBagLayout());
    container.add(sud);

    //GRID 3x3 GENERATE
    bigSquares = new JPanel[3][3];
    for(i = 0; i < 3; i++) {
      for(j = 0; j <3 ; j++) {
        bigGridCon.gridx = i;
        bigGridCon.gridy = j;
        bigSquares[i][j] = new JPanel();
        bigSquares[i][j].setBorder(bord);
        bigSquares[i][j].setLayout(smallGrid);
        sud.add(bigSquares[i][j], bigGridCon);
      }
    }

    //GRID 9x9 GENERATE
    inputs = new TextField[9][9];
    for(i = 0; i < 9; i++) {
      for(j = 0; j < 9; j++) {
        smallGridCon.gridx = i%3;
        smallGridCon.gridy = j%3;
        inputs[i][j] = new TextField(new Coordinates(i, j), this);
        //inputs[i][j].addKeyListener(this);
        bigSquares[i/3][j/3].add(inputs[i][j], smallGridCon);

      }
    }

    //load values from object
    this.setFromSudoku();

    //SET IT VISIBLE AND PACK IT
    this.setVisible(true);
    pack();

  }

  public Sudoku getSudoku() {
    return sudoku;
  }

  private void setFromSudoku() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        this.inputs[i][j].setFromValue(this.sudoku.getValue(new Coordinates(i, j)));
      }
    }
  }


  public void revalidateInputs() {
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        Coordinates crd;
        Value val;
        crd = new Coordinates(i, j);
        if(!this.inputs[i][j].getText().equals("") && !this.inputs[i][j].getIsValid()) {
          val = new Value(Integer.parseInt(this.inputs[i][j].getText()), false);
          if(this.sudoku.checkValue(crd, val) == null) {
            this.sudoku.insert(crd, val);
            this.inputs[i][j].setValid(true);
          }
        }
      }
    }
  }

}
