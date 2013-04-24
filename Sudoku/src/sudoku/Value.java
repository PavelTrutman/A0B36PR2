package sudoku;

import java.io.Serializable;

/**
 * This class enables saving values in each field in sudoku.
 *
 * <p>An instance of this class can contains values 1-9 or be empty.</p>
 *
 * @author Pavel Trutman
 */
public class Value implements Serializable{
  /**
   * Saved value
   */
  private int value;

  /**
   * TRUE if empty
   */
  private boolean isEmpty;

  /**
   * TRUE if the value is persistent and can not be changed
   */
  private boolean isPersistent = false;

  /**
   * Creates a new object filled with value.
   *
   * @param a value in field (only values 1-9), value zero sets it empty
   * @param persistent TRUE if the value is persistent and can not be changed
   * @throws RuntimeException if the value is not in 0-9
   */
  public Value(int a, boolean persistent) throws RuntimeException {
    //see method setValue()
    this.setValue(a);
    //it can be persistend only if the value is filled
    if(a != 0){
      this.isPersistent = persistent;
    }
  }

  /**
   * Creates a new empty object.
   *
   */
  public Value() {
   //see method clear()
   this.clear();
  }

  /**
   * Change saved value.
   *
   * @param a new value, value 0 sets it empty
   * @throws RuntimeException if value is not in 0-9 or is persistent
   */
  public void setValue(int a) throws RuntimeException {
    if(this.isPersistent) {
      //if can not be changed, throw an exception
      throw new RuntimeException();
    }
    else {
      //check if the value is in 1-9
      if(a >= 1 & a <= 9) {
        //if true, set the value
        this.value = a;
        this.isEmpty = false;
      }
      else if(a == 0) {
        //if zero, set it empty
        this.isEmpty = true;
      }
      else {
        //else throw exception
        throw new RuntimeException();
      }
    }
  }

  /**
   * Sets value by another value.
   *
   * @param value new value
   * @throws RuntimeException if is set as persistent
   */
  public void setValue(Value value) {
    //can be changed only if is not persistent
    if(this.isPersistent) {
      //if persistent throw exception
      throw new RuntimeException();
    }
    else {
      //set as value we got
      if(value.getIsEmpty()) {
        this.isEmpty = true;
      }
      else {
        this.value = value.getValue();
        this.isEmpty = false;
        this.isPersistent = value.getIsPersistent();
      }
    }
  }

  /**
   * Set the value as persistent.
   *
   * @throws RuntimeException if is empty
   */
  public void setPersistent() throws RuntimeException {
    //if empty
    if(this.isEmpty) {
      //throw exception
      throw new RuntimeException();
    }
    else {
      //else set as persistent
      this.isPersistent = true;
    }
  }

  /**
   * Sets as empty.
   *
   * @throws RuntimeException if is set as persistent
   */
  public void clear() {
    if(this.isPersistent) {
      throw new RuntimeException();
    }
    else {
      this.isEmpty = true;
    }
  }

  /**
   * Returns saved value.
   *
   * @return saved value
   * @throws RuntimeException if is empty
   */
  public int getValue() throws RuntimeException{
    if(!this.isEmpty) {
      //if not empty, return value
      return this.value;
    }
    else {
      //if empty, throw exception
      throw new RuntimeException();
    }
  }

  /**
   * Returns if the value is empty.
   *
   * @return TRUE if the value is empty, else FALSE
   */
  public boolean getIsEmpty() {
    return this.isEmpty;
  }

  /**
   * Returns if the value is set as persistent and can not be changed.
   *
   * @return TRUE if the value is set as persistent, else FALSE
   */
  public boolean getIsPersistent() {
    return this.isPersistent;
  }

  /**
   * Compare two values.
   *
   * @param second value to campare with
   * @return TRUE if the values are equal, else FALSE
   */
  public boolean compareValue(Value second) {
    //must be filled
    if(!this.getIsEmpty() && !second.getIsEmpty()) {
      //must have the same value
      if(this.getValue() == second.getValue()) {
        //if equals, return TRUE
        return true;
      }
    }
    //else return FALSE
    return false;
  }

  /**
   * Returns value to be printed on the screen.
   *
   * @return saved value or char '_' if is empty
   */
  public char render() {
    if(this.isEmpty) {
      //if empty return '_'
      return '_';
    }
    else {
      //else value converted to char
      return Character.forDigit(this.value, 10);
    }
  }
}