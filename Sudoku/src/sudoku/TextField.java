package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Pavel Trutman
 */
public class TextField extends JTextField implements KeyListener {

  /*
   * Coordinates
   */
  private Coordinates crd;

  /*
   * Valid
   */
  private boolean isValid;

  /*
   * Graphical user interface, where input was made
   */
  private GUI gui;

  /*
   * Default colors
   */
  private Color BGEditable= new Color(250, 250, 250);
  private Color BGNonEditable = new Color(238, 238, 238);
  private Color FGInvalid = new Color(210, 30, 30);
  private Color FGValid = new Color(50, 50, 50);

  public TextField(Coordinates crd, GUI gui) {
    this.crd = crd;
    this.gui = gui;
    this.setColumns(1);
    this.setFont(new java.awt.Font("Verdana", 1, 20));
    this.setBorder(null);
    this.setHorizontalAlignment(JTextField.CENTER);
    this.setBackground(BGEditable);
    this.setForeground(FGValid);
    this.addKeyListener(this);
  }

  public void clear() {
    this.setEditable(true);
    this.setBackground(BGEditable);
    this.setText("");
    this.setForeground(FGValid);
  }

  public void setFromValue(Value val) {
    this.setEditable(!val.getIsPersistent());
    if(val.getIsEmpty()) {
      this.setText("");
    }
    else {
      this.setText(Integer.toString(val.getValue()));
    }
  }

  @Override
  public void setEditable(boolean bln) {
    super.setEditable(bln);
    if(bln) {
      this.setBackground(BGEditable);
    }
    else {
      this.setBackground(BGNonEditable);
    }
  }

  public Coordinates getCoordinates() {
    return this.crd;
  }

  public void setValid(boolean bln) {
    this.isValid = bln;
    if(bln) {
      this.setForeground(FGValid);
    }
    else {
      this.setForeground(FGInvalid);
    }
  }

  public boolean getIsValid() {
    return this.isValid;
  }

  @Override
  public void keyTyped(KeyEvent key) {
    //get the focused component
    TextField In = (TextField) gui.getMostRecentFocusOwner();

    //get char from pressed key
    char c = key.getKeyChar();

    //clear input if Ctrl+v pressed
    if(key.isControlDown()) {
      c = '0';
    }

    //only if is editable
    if(In.isEditable()) {
      //only numbers 1-9
      if(Integer.valueOf(c) >= 49 && Integer.valueOf(c) <= 57) {
        //set input text
        In.setText(String.valueOf(c));
        //insert into object
        gui.getSudoku().getValue(In.getCoordinates()).clear();
        In.setValid(gui.getSudoku().insert(In.getCoordinates(), new Value(Integer.parseInt(String.valueOf(c)), false)));
        if(gui.getSudoku().checkIsSolved()) {
          JOptionPane.showMessageDialog(null, "Congratulations!\nYou have successfully solved the sudoku.", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        }
      }
      //if pressed number 0
      else if(Integer.valueOf(c) == 48) {
        //clear input text
        In.setText("");
        //clear from object
        gui.getSudoku().getValue(In.getCoordinates()).clear();
        In.setValid(true);
      }
    }

    //revalidate all invalid values
    gui.revalidateInputs();

    //consume the key
    key.consume();
  }

  @Override
  public void keyPressed(KeyEvent ke) {
    //do nothing
  }

  @Override
  public void keyReleased(KeyEvent ke) {
    //do nothing
  }

}
