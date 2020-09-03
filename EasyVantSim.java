import java.io.*;
//import controlP5.*;
import processing.core.*;


public class EasyVantSim implements java.io.Serializable {
  private static final long serialversionUID = 129348938L; 
  protected transient PApplet pa;//parent

  //public transient ControlP5 cp5;
  public int conColor;
  public int SuperSpeed;
  public int SpeedMultiplier;
  public int RuleEnumuration;
  public int RuleEnumurationRange;
  public int RuleEnumurationAdjust;

  public int NumRules;
  public int Computations;
  //Slider abc;

  public int rows;
  public int cols;
  public int [][] grid;

  public int size;

  public int x;
  public int y;
  public int dir; // counter clockwise starting facing right
  public int cell;
  public int generations;

  public boolean mirror;
  public int ruleDecValue;
  public int maxRuleChars;
  public int ruleDecValueMax;

  // rule definition
  public int numRules;
  public char[] rules;
  public int a; 

  // colors
  int colorMode = 0;


  // Default constructor 
  public EasyVantSim(PApplet pa, int maxRuleChars) {
    this.pa = pa;

    conColor = pa.color(127);
    // int range -2,147,483,648 - 2,147,483,647 
    // long range -9,223,372,036,854,775,808  - -9,223,372,036,854,775,807
    ruleDecValueMax = (int)pa.pow(2, this.maxRuleChars)-1;


    //cp5 = new ControlP5(this.pa);
    conColor = 0;
    SuperSpeed = 1;
    SpeedMultiplier = 1;
    RuleEnumuration = 4;
    RuleEnumurationRange = 4;
    RuleEnumurationAdjust = 4;

    NumRules = 4;
    Computations = 0;
    //Slider abc;

    rows = 160;
    cols = 160;
    grid = new int[cols][rows];

    size = 5;

    x = cols/2;
    y = rows/2;
    dir = 0; // counter clockwise starting facing right
    cell = 0;
    generations = 0;

    mirror = false;
    ruleDecValue = 0;
    //maxRuleChars = 16;
    this.maxRuleChars = maxRuleChars;
    ruleDecValueMax = 6;

    // rule definition
    numRules = 4;
    rules = new char[maxRuleChars];
  }

  public void setup() {


    initGrid();
  }
  void linkPApplet(PApplet pa) {
    this.pa = pa;
  }

  void injectUIData(int ss, int sm, int re, int rea, int nr, int cm) {
    SuperSpeed = ss;
    SpeedMultiplier = sm;
    RuleEnumuration = re;
    RuleEnumurationAdjust = rea;
    NumRules = nr;
    colorMode = cm;
    //if(rb==true)
    //ResetButton(0);
  }



  char[] intToRuleChars(int num) {
    //pa.println("num = " + num + ", maxRuleChars = " + maxRuleChars
    //  + ", numMax = " + (int)pa.pow(2, maxRuleChars-1));

    for (int i = maxRuleChars-1; i >=0; i --) {
      int numMax = (int)pa.pow(2, i);
      //pa.println(maxRuleChars-1);
      if (num >= numMax) {
        rules[i] = 'R';
        num -= numMax;
        //println("num: "+num+"numMax: "+numMax);
      } else {
        //pa.println(rules.length);
        rules[i] = 'L';
      }
    }


    return rules;
  }

  void generationRules(int numRules) {
    //generating rule enumuration array
    for (int i = 0; i < numRules; i ++) {
      char c = 'L';
      int r = pa.round(pa.random(1)*1);
      if ( r == 0 ) c = 'R';
      rules[i] =  c;
    }
  }
  void initGrid() {
    for (int i = 0; i < cols; i ++ ) {
      for (int j = 0; j < rows; j++) {
        //grid[i][j] = i % 2;
        //grid[i][j] = (((float)(j * cols )+ i ) / 100) * 255;
        grid[i][j] = 0;
      }
    }
  }
  public void ResetButton(int theValue) {
    //pa.println(theValue);
    initGrid();
    // ant stats reset
    dir = 0;
    x = cols/2;
    y = rows/2;
    this.generations = 0;
  }
  int getColorFromRules(int cell) {
    int color = 0;
    if (colorMode == 0) {
      color =(int)pa.color(cell);
    } else if (colorMode == 1) {
      color = getRetroColors1(cell);
    } else if (colorMode == 2) {
      color = getRetroColors2(cell);
    }
    return color;
  }
  int getRetroColors1(int cell) {
    int color = -1; 
    cell = cell % 16;
    if ( cell == 0 ) {
      color = pa.color(0);
    } else if ( cell == 1 ) {
      color = pa.color(0, 127, 255);
    } else if (cell == 2 ) {
      //print("hello");
      color = pa.color(255, 0, 127);
    } else if (cell == 3 ) {
      color = pa.color(127, 255, 0);
    } else if ( cell == 4 ) {
      color = pa.color(0, 127, 0);
    } else if (cell == 5 ) {
      //print("hello");
      color = pa.color(64, 0, 0);
    } else if (cell == 6 ) {
      color = pa.color(0, 64, 64);
    } else if (cell == 7 ) {
      color = pa.color(64, 64, 64);
    } else if (cell == 8 ) {
      color = pa.color(12, 0, 64);
    } else if (cell == 9 ) {
      color = pa.color(0, 32, 64);
    } else if (cell == 10 ) {
      color = pa.color(0, 128, 64);
    } else if (cell == 11 ) {
      color = pa.color(128, 0, 64);
    } else if (cell == 12 ) {
      color = pa.color(0, 32, 128);
    } else if (cell == 13 ) {
      color = pa.color(32, 0, 128);
    } else if (cell == 14 ) {
      color = pa.color(128, 0, 128);
    } else if (cell == 15 ) {
      color = pa.color(0, 128, 128);
    }
    return color;
  }
  int getRetroColors2(int cell) {
    //cell = cell % 16;
    int color = -1;
    if ( cell == 0 ) {
      color = pa.color(0);
    } else if ( cell == 1 ) {
      color = pa.color(0, 127, 255);
    } else if (cell == 2 ) {
      //print("hello");
      color = pa.color(255, 0, 127);
    } else if (cell == 3 ) {
      color = pa.color(127, 255, 0);
    } else if ( cell == 4 ) {
      color = pa.color(0, 127, 0);
    } else if (cell == 5 ) {
      //print("hello");
      color = pa.color(64, 0, 0);
    } else if (cell == 6 ) {
      color = pa.color(0, 64, 64);
    } else if (cell == 7 ) {
      color = pa.color(64, 64, 64);
    } else if (cell == 8 ) {
      color = pa.color(12, 0, 64);
    } else if (cell == 9 ) {
      color = pa.color(0, 32, 64);
    } else if (cell == 10 ) {
      color = pa.color(0, 128, 64);
    } else if (cell == 11 ) {
      color = pa.color(128, 0, 64);
    } else if (cell == 12 ) {
      color = pa.color(0, 32, 128);
    } else if (cell == 13 ) {
      color = pa.color(32, 0, 128);
    } else if (cell == 14 ) {
      color = pa.color(128, 0, 128);
    } else if (cell == 15 ) {
      color = pa.color(0, 128, 128);
    }
    return color;
  }
  void drawBoard() {
    for (int i = 0; i < cols; i ++ ) {
      for (int j = 0; j < rows; j++) {
        int cell = grid[i][j];
        int color = getColorFromRules(cell);
        if(color != -1) {
          pa.fill(color);
        }
        /*
       
         */
        //hi 


        //print(cell);
        //pa.fill(cell);
        pa.rect(i * size, j * size, size, size);
      }
    }
    pa.fill(255, 0, 0);
    pa.rect(x * size, y * size, size, size);
  }
  char cellToRule(int cell) {
    char r = 'E'; //e for error
    if (cell >= 0 && cell <numRules) {
      r = rules[cell];
    }
    return r;
  }

  void update () {
    int futureCell;
    cell = grid[x][y];
    futureCell = ( cell + 1 ) % numRules;
    char r = cellToRule(cell);
    if (mirror == true) {
      if (r == 'R') { 
        r = 'L';
      } else { 
        r = 'R';
      }
      generations--;
    } else {
      generations++;
    }

    if (r == 'L') {

      dir = (dir - 1 ) % 4;
      if (dir<0) dir += 4;
    } else if (r == 'R') {

      dir = (dir + 1 ) % 4;
      if (dir<0) dir += 4;
    } else if ( r == 'E') {
      float x = pa.random(0, 1);
      if (x <0.5) {
        dir = (dir - 1 ) % 4;
        if (dir<0) dir += 4;
      } else {
        dir = (dir + 1 ) % 4;
        if (dir<0) dir += 4;
      }
    }
    cell = futureCell;
    grid[x][y] = futureCell;
  }


  void move() {
    switch(dir) {
    case 0:
      x += 1;
      x = x % cols;

      break;

    case 1:
      y += 1;
      y = y % rows;

      break;

    case 2:
      x -= 1;
      x = x % cols;
      if (x<0) x += cols;

      break;

    case 3:
      y -= 1;
      y = y % rows;
      if (y<0) y += rows;

      break;
    }
  }
  public void draw() {

    for (int i = 0; i < rules.length; i ++) {
      //pa.print(rules[i]);
    }
    //pa.println("");

    // draw ant on the board
    //numRules = RuleEnumuration;
    numRules = NumRules;
    intToRuleChars(RuleEnumuration+RuleEnumurationAdjust);
    String ruleStr = "";
    for (int i = 0; i < numRules; i++) {
      ruleStr += rules[i];
    }
    String ruleStrB = ""; // generate a rule string up to the Boundry
    for (int i = 0; i < cell; i++) {
      ruleStrB += rules[i];
    }
    pa.fill(127);
    pa.text(ruleStr, 900, 100, 800, 800);
    pa.fill(255);
    pa.text(ruleStrB, 900, 100, 800, 800);
    //pa.println(SuperSpeed);
    for (int i = 0; i < SuperSpeed * SpeedMultiplier; i ++ ) {
      this.update();
      this.move();
    }
    // move ant
    //move();

    // update board
    drawBoard();
    // draw board
    /*pa.fill(0);
     pa.textSize(24);
     pa.text("Generations: "+ generations, 25, 25);
     pa.fill(0);
     pa.textSize(24);
     pa.text("Generations: "+ generations, 27, 27);
     pa.fill(0);
     pa.textSize(24);
     pa.text("Generations: "+ generations, 27, 25);
     pa.fill(0);
     pa.textSize(24);
     pa.text("Generations: "+ generations, 25, 27);
     */
    pa.fill(255);
    pa.textSize(24);
    pa.text("Generations: "+ generations, 26, 26);
  }
}
