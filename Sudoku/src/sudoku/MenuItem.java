package sudoku;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Pavel Trutman
 */
public class MenuItem extends JMenuItem implements ActionListener {

  /*
   * Graphical user interface, where the MenuItem was made
   */
  private GUI gui;

  public MenuItem(GUI gui, String string) {
    super(string);
    this.gui = gui;
    this.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    MenuItem menu = (MenuItem) ae.getSource();
    if(menu == gui.newGame) {
      gui.setSudoku(Sudoku.generatePuzzle());
    }
    else if(menu == gui.restartGame) {
      gui.restart();
    }
    else if(menu == gui.nextMoveGame) {
      gui.hint();
    }
    else if(menu == gui.solveGame) {
      gui.solve();
    }
    else if(menu == gui.saveGame) {
      gui.save();
    }
    else if(menu == gui.loadGame) {
      gui.load();
    }
    else if(menu == gui.exitGame) {
      System.exit(0);
    }
    else if(menu == gui.gameAbout) {
      JOptionPane.showMessageDialog(null, "Sudoku, version 1.0\nBuilt 2013/04/04.\n\nThis game was made as semestral work at CTU.\n\nAll sources are avaible at site: https://github.com/trutmpav/A0B36PR2.\nAll contributors are welcome.", "About game", JOptionPane.PLAIN_MESSAGE);
    }
    else if(menu == gui.authorAbout) {
      JOptionPane.showMessageDialog(null, "Made by Pavel Trutman.\nStudent at CTU, FEE.\nE-mail: trutmpav@fel.cvut.cz.", "About author", JOptionPane.PLAIN_MESSAGE);
    }
  }


}
