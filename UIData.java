import java.io.*;
//import controlP5.*;
import processing.core.*;

class UIData implements java.io.Serializable  {
    private static final long serialversionUID = 129348939L; 

  public int SuperSpeed;
  public int SpeedMultiplier;
  public int RuleEnumuration;
  //int RuleEnumurationRange;
  public int RuleEnumurationAdjust;
  
  public int NumRules;
  public int colorMode;
  //int ruleDecValue;
  //int maxRuleChars;
  //int ruleDecValueMax;
  
  
  public UIData() {
  SuperSpeed = 1;
  SpeedMultiplier = 1;
  RuleEnumuration = 4;
  //RuleEnumurationRange = 4;
  RuleEnumurationAdjust = 4;
  
  NumRules = 4;
  //ruleDecValue = 0;
  //maxRuleChars = 16;
  //ruleDecValueMax = 16;
  
  // rule definition
  //numRules = 4;
  }
  public void injectUIData(int ss, int sm, int re, int rea, int nr, int cm) {
    SuperSpeed = ss;
    SpeedMultiplier = sm;
    RuleEnumuration = re;
    RuleEnumurationAdjust = rea;
    NumRules = nr;
    colorMode = cm;
    //if(rb==true)
      //ResetButton(0);
  }
  
}
