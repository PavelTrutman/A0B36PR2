package sudoku;

import java.awt.*;
import java.io.*;
import javax.imageio.*;
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
  public MenuItem newGame;
  public MenuItem restartGame;
  public MenuItem nextMoveGame;
  public MenuItem solveGame;
  public MenuItem saveGame;
  public MenuItem loadGame;
  public MenuItem exitGame;
  private JMenu aboutMenu;
  public MenuItem gameAbout;
  public MenuItem authorAbout;

  /*
   * File choosers
   */
  private JFileChooser fileChooser;

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

    //set icon
    try {
      this.setIconImage(ImageIO.read(new File(".\\src\\sudoku\\images\\ico.png")));
    }
    catch(IOException ex) {
      System.out.println("When reading icon file: " + ex.getMessage());
    }

    //INIT FILE CHOOSER DIALOG
    fileChooser = new JFileChooser();

    //MENUS
    menuBar = new JMenuBar();

    //game menu
    gameMenu = new JMenu();
    gameMenu.setText("Game");
    menuBar.add(gameMenu);

    newGame = new MenuItem(this, "New");
    gameMenu.add(newGame);

    restartGame = new MenuItem(this, "Restart");
    gameMenu.add(restartGame);

    nextMoveGame = new MenuItem(this, "Next move");
    gameMenu.add(nextMoveGame);

    solveGame = new MenuItem(this, "Solve");
    gameMenu.add(solveGame);

    gameMenu.addSeparator();

    saveGame = new MenuItem(this, "Save");
    gameMenu.add(saveGame);

    loadGame = new MenuItem(this, "Load");
    gameMenu.add(loadGame);

    gameMenu.addSeparator();

    exitGame = new MenuItem(this, "Exit");
    gameMenu.add(exitGame);

    //about menu
    aboutMenu = new JMenu();
    aboutMenu.setText("About");
    menuBar.add(aboutMenu);

    gameAbout = new MenuItem(this, "About game");
    aboutMenu.add(gameAbout);

    authorAbout = new MenuItem(this, "About author");
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

  public void setSudoku(Sudoku sudoku) {
    this.sudoku = sudoku;
    this.setFromSudoku();
  }

  private void setFromSudoku() {
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        this.inputs[i][j].clear();
        this.inputs[i][j].setFromValue(this.sudoku.getValue(new Coordinates(i, j)));
      }
    }
  }

  public void restart() {
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        if(this.inputs[i][j].isEditable()) {
          this.inputs[i][j].clear();
          this.sudoku.getValue(new Coordinates(i, j)).clear();
        }
      }
    }
  }

  public void hint() {
    Coordinates crd;
    Value val;
    crd = this.sudoku.hint();
    if(crd != null) {
      val = this.sudoku.getValue(crd);
      this.inputs[crd.getX()][crd.getY()].clear();
      this.inputs[crd.getX()][crd.getY()].setFromValue(val);
      this.inputs[crd.getX()][crd.getY()].setGreen();
      this.revalidateInputs();
    }
    else {
      JOptionPane.showMessageDialog(null, "Sudoku has no solution now. Try to remove some numbers.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void solve() {
    if(this.sudoku.solve(false)) {
      this.setSudoku(sudoku);
    }
    else {
      JOptionPane.showMessageDialog(null, "Sudoku has no solution now. Try to remove some numbers.", "Error", JOptionPane.ERROR_MESSAGE);
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

  public void save() {
    int ret;
    fileChooser.setApproveButtonText("Save");
    fileChooser.setDialogTitle("Save");
    ret = fileChooser.showOpenDialog(this);
     if (ret == JFileChooser.APPROVE_OPTION) {
       File file = fileChooser.getSelectedFile();
       if(this.sudoku.writeToFile(file.getAbsolutePath())) {
         JOptionPane.showMessageDialog(null, "Sudoku was successfuly saved.", "Game saved", JOptionPane.INFORMATION_MESSAGE);
       }
       else {
         JOptionPane.showMessageDialog(null, "Error when saving game. Not saved!", "Error", JOptionPane.ERROR_MESSAGE);
       }
     }
  }

  public void load() {
    fileChooser.setApproveButtonText("Load");
    fileChooser.setDialogTitle("Load");
    int ret;
    Sudoku s;
    ret = fileChooser.showOpenDialog(this);
     if (ret == JFileChooser.APPROVE_OPTION) {
       File file = fileChooser.getSelectedFile();
       s = Sudoku.readFromFile(file.getAbsolutePath());
       if(s != null) {
         this.setSudoku(s);
         //JOptionPane.showMessageDialog(null, "Sudoku was successfuly saved.", "Game saved", JOptionPane.INFORMATION_MESSAGE);
       }
       else {
         JOptionPane.showMessageDialog(null, "Error when loading file.", "Error", JOptionPane.ERROR_MESSAGE);
       }
     }
  }

}