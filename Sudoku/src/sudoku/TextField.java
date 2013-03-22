package sudoku;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Pavel Trutman
 */
public class TextField extends JTextField {

  /*
   * Coordinates
   */
  private Coordinates crd;

  /*
   * Valid
   */
  private boolean isValid;

  /*
   * Default colors
   */
  private Color BGEditable= new Color(250, 250, 250);
  private Color BGNonEditable = new Color(238, 238, 238);
  private Color FGInvalid = new Color(210, 30, 30);
  private Color FGValid = new Color(50, 50, 50);

  public TextField(Coordinates crd) {
    this.crd = crd;
    this.setColumns(1);
    this.setFont(new java.awt.Font("Verdana", 1, 20));
    this.setBorder(null);
    this.setHorizontalAlignment(JTextField.CENTER);
    this.setBackground(BGEditable);
    this.setForeground(FGValid);
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

}
