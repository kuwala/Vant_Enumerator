// Langtons Ant

// 2d Grid

// Ant mpves
/*sdfjkdf*/

import java.io.*;
import controlP5.*;
import processing.core.*;

EasyVantSim simObject;
ControlP5 cp5;
UIData uiObject;
boolean firstTimeLoadSkipped = false;
boolean firstTimeSaveSkipped = false;

int SuperSpeed = 1;
int SpeedMultiplier = 1;
int RuleEnumuration = 4;
int RuleEnumurationRange = 4;
int RuleEnumurationAdjust = 4;

int NumRules = 4;
boolean ResetButton = false;
int ruleDecValue = 0;
int maxRuleChars = 1024;
int ruleDecValueMax = 18;

int colorModeMax = 2;
int ColorMode = 0; 
// 0 - grayscale cells
// 1 - original hand picked retro colors w/ modulo on value over flow
// 2 - original hand picked retro colors w/ ignore new color on value over flow (bug/ still cool)

boolean showUI = true;

int numSaveSlots = 16;
int SaveSlot = 0;
String saveNameUI = "ui_save" +str(SaveSlot) + ".ser";
String saveNameSim = "sim_save" +str(SaveSlot) + ".ser";

Slider slide;
//String filename = sketchPath() + "vant_data.ser";
File sim_save_file;
File ui_save_file;

public void ResetButton(int theValue) {
  simObject.ResetButton(0);
  println("reset hit");
};

public void LoadButton() {
  // The ui seems to fire the buttons apon intisilizing them
  // so there is a first time load and first time save skip flag
  // that gets set instead of executing the desired functions
  println("load button hit");
  // deriver file name from SaveSlot int value + string
  saveNameUI = "ui_save" +str(SaveSlot) + ".ser";
  saveNameSim = "sim_save" +str(SaveSlot) + ".ser";
  File sim_save_file = dataFile(saveNameSim);
  File ui_save_file = dataFile(saveNameUI);

  String filename = sim_save_file.getPath();
  String filename2 = ui_save_file.getPath();
  if (firstTimeLoadSkipped == false) {
    println("----First Time load skipped------");
    firstTimeLoadSkipped = true;
  } else {
    loadSimSerializedFile(filename);
    loadUISerializedFile(filename2);
    
  }

  //EasyVantSim object1 = null; 

  // Deserialization
}
void loadSimSerializedFile(String filename) {
  try
  {    
    // Reading the object from a file 
    FileInputStream file = new FileInputStream(filename); 
    ObjectInputStream in = new ObjectInputStream(file); 
    // Method for deserialization of object 
    //object = null;
    simObject = (EasyVantSim)in.readObject();
    simObject.linkPApplet(this);

    in.close(); 
    file.close(); 

    System.out.println("Deserialized obj at: "); 
    System.out.print(filename); 
    //System.out.println("a = " + object.a);
  } 

  catch(IOException ex) 
  { 
    System.out.println("IOException is caught");
  } 

  catch(ClassNotFoundException ex) 
  { 
    System.out.println("ClassNotFoundException is caught");
  }
}
void loadUISerializedFile(String filename) {
  try
  {    
    // Reading the object from a file 
    FileInputStream file = new FileInputStream(filename); 
    ObjectInputStream in = new ObjectInputStream(file); 
    // Method for deserialization of object 
    //object = null;
    uiObject = (UIData)in.readObject();
    loadUIDataIntoUI(uiObject);

    in.close(); 
    file.close(); 

    System.out.println("Deserialized obj at: "); 
    System.out.print(filename); 
    //System.out.println("a = " + object.a);
  } 

  catch(IOException ex) 
  { 
    System.out.println("IOException is caught");
  } 

  catch(ClassNotFoundException ex) 
  { 
    System.out.println("ClassNotFoundException is caught");
  }
}
public void loadUIDataIntoUI(UIData uiData) {
  println("");
  println("loading ui data into ui vars");
  SuperSpeed = uiData.SuperSpeed;
  SpeedMultiplier = uiData.SpeedMultiplier;
  RuleEnumuration = uiData.RuleEnumuration;
  RuleEnumurationAdjust = uiData.RuleEnumurationAdjust;
  NumRules = uiData.NumRules;
}
public void saveSimObject(String filename) {

  // Serialization  
  try
  {    
    //Saving of object in a file 
    println("heellooo from save sim");

    FileOutputStream file = new FileOutputStream(filename); 

    ObjectOutputStream out = new ObjectOutputStream(file); 

    // Method for serialization of object 
    out.writeObject(simObject); 

    out.close(); 
    file.close(); 

    System.out.println("Object has been serialized");
  } 

  catch(IOException e) 
  { 
    System.out.println("IOException is caught"); 
    e.printStackTrace(); 

    // Prints what exception has been thrown 
    System.out.println(e);
  }
}
public void saveUIObject(String filename) {

  // Serialization  
  try
  {    
    //Saving of object in a file 
    FileOutputStream file = new FileOutputStream(filename); 

    ObjectOutputStream out = new ObjectOutputStream(file); 
    // Method for serialization of object
    uiObject.injectUIData(SuperSpeed, SpeedMultiplier, RuleEnumuration, RuleEnumurationAdjust, NumRules, ColorMode);
    println("heellooo from save ui");

    out.writeObject(uiObject); 

    out.close(); 
    file.close(); 

    System.out.println("Object has been serialized");
  } 

  catch(IOException e) 
  { 
    System.out.println("IOException is caught"); 
    e.printStackTrace(); 

    // Prints what exception has been thrown 
    System.out.println(e);
  }
}

public void SaveButton() {
  println("save button hit");
  //loadUIDataIntoObject();
  saveNameUI = "ui_save" +str(SaveSlot) + ".ser";
  saveNameSim = "sim_save" +str(SaveSlot) + ".ser";
  File sim_save_file = dataFile(saveNameSim);
  File ui_save_file = dataFile(saveNameUI);
  if(firstTimeSaveSkipped == false){
    firstTimeSaveSkipped = true;
  } else {
  saveSimObject(sim_save_file.getPath());

  saveUIObject(ui_save_file.getPath());
  }
}


void setup() {
  sim_save_file = dataFile("sim_save.ser");
  ui_save_file = dataFile("ui_save.ser");
  println(sim_save_file.getPath());
  //testSer();
  noStroke();
  frameRate(120);

  fullScreen();
  //size(900, 900);


  ruleDecValueMax = (int)pow(2, maxRuleChars)-1;
  simObject = new EasyVantSim(this, maxRuleChars);
  uiObject = new UIData();
  simObject.setup();

  // Setup UI last
  cp5 = new ControlP5(this);
  setupUI();
}

void setupUI() {

  //cp5.addToggle("mirror")
  //    .setCaptionLabel("mirror")
  //    .setPosition(400,100)
  //    .setSize(200,19)
  //    .setState(true);
  slide =  cp5.addSlider("SuperSpeed")
    .setPosition(100, 30)
    .setWidth(600)
    .setRange(1, 500)
    ;

  cp5.addSlider("SpeedMultiplier")
    .setPosition(100, 50)
    .setWidth(1200)
    .setRange(1, 2000)
    ;

  cp5.addSlider("RuleEnumuration")
    .setPosition(100, 120)
    .setWidth(800)
    .setRange(0, ruleDecValueMax)
    ;
  cp5.addSlider("RuleEnumurationAdjust")
    .setPosition(100, 160)
    .setWidth(800)
    .setRange(0, 800)
    ;
    

  cp5.addSlider("NumRules")
    .setPosition(100, 220)
    .setWidth(200)
    .setRange(1, maxRuleChars-1)
    .setNumberOfTickMarks(maxRuleChars-1)
    ;
    
  cp5.addSlider("SaveSlot")
    .setPosition(100, 70)
    .setWidth(200)
    .setRange(1, numSaveSlots)
    .setNumberOfTickMarks(numSaveSlots)
    ;
    cp5.addSlider("ColorMode")
    .setPosition(100, 85)
    .setWidth(200)
    .setRange(0, colorModeMax)
    .setNumberOfTickMarks(colorModeMax+1)
    ;
   
  //cp5.addSlider("CellSize")
  //   .setPosition(100,140)
  //   .setWidth(200)
  //   .setRange(1,8)
  //   .setNumberOfTickMarks(8)
  //   ;
  cp5.addButton("ResetButton")
    .setValue(0)
    .setPosition(100, 100)
    .setSize(100, 19)
    ;

  cp5.addButton("SaveButton")
    .setValue(0)
    .setPosition(250, 100)
    .setSize(100, 19)
    ;
  cp5.addButton("LoadButton")
    .setValue(0)
    .setPosition(400, 100)
    .setSize(100, 19)
    ;
}

void mouseReleased() {
 if(mouseButton == RIGHT) {
   simObject.ResetButton(1333333337);
 }
}

void keyPressed() {
  if(key == 'q') {
    if(showUI == true) {
      cp5.hide();
      showUI = false;
    } else {
      cp5.show();
      showUI = true;
    }
  }
  if(key == 'r') {
  simObject.ResetButton(69); // 69 black magic 
  }
}

void draw() {
  //object.update();
  background(32);
  //----->//slide.setValue(40);
  simObject.injectUIData(SuperSpeed, SpeedMultiplier, RuleEnumuration, RuleEnumurationAdjust, NumRules, ColorMode);
  //object.SuperSpeed = SuperSpeed;
  //object.SpeedMultiplier = SpeedMultiplier;
  simObject.draw();
}
