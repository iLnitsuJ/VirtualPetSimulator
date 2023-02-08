package ui;


import model.*;
import model.Action;
import model.Event;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class PetSimulatorApp extends JFrame {

    //PetSimulatorApp contains the code for user interface.Specifically methods for initialization of the app,
    //game menu, setting menu, etc.

    Scanner scanner;
    Player player;
    Stopwatch gameTimer;
    private final int appWindowWidth = 800;
    private final int appWindowHeight = 800;
    JTextArea outputConsole;
    private static final String JSON_Storage = "./data/playerProfile.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private JLabel displayedLabel;
    private JPanel centerScreen;
    private JPanel buttons;
    private JPanel leftScreen;
    Rectangle barTemplate;
    SideScreen ss;
    String selectedPetName;
    JComboBox<String> viewPetsBox;
    private final String catIdleImageSource = "./data/cat_0.gif";
    private final String dogIdleImageSource = "./data/dog_0.gif";
    ImageIcon currentImageDisplaying;
    private String titleImageIcon = "./data/catHead.png";

    //EFFECTS: Main of the application, goes through initialization, game menu. while input is not exit number,
    //         application will keep running until input is exitNumber.
    public PetSimulatorApp() {
        super("Pet Simulator");

        jsonReader = new JsonReader(JSON_Storage);
        jsonWriter = new JsonWriter(JSON_Storage);
        gameTimer = new Stopwatch();
        gameTimer.startTimer();
        scanner = new Scanner(System.in);
        EventLog.getInstance().clear();
        previousProgressInitialization();
        initializeGraphics();
        updatePetToDisplay();
        setVisible(true);
        scanner.close();

        //citation: https://stackoverflow.com/questions/32051657/how-to-perform-action-after-jframe-is-closed
        // implementing a way to do task right after the jframe window is closed. This listener will react to
        // window event open/close. Here we override the method that calls when the window is closing.Basically,
        //I wanted to save the timer right when the window is closed so I can reuse it for some features.

        //EFFECTS: When the window is closed, stores the current time in millisecond to variable end time in gameTimer.
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                gameTimer.endTimer();

                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.getDescription());
                }
            }
        });
        actionEvery30Seconds();

    }

    //EFFECTS: a loop that goes through every 30 seconds. Updating condition to simulate life regen,
    // starves, closeness increase over time.
    public void actionEvery30Seconds() {

        while (true) {
            if (gameTimer.elapsedTime() >= 30000) {
                gameTimer.startTimer();
                for (Pet pet : this.player.getPets()) {
                    pet.getAnimal().animalConditionUpdate();
                }
                updatePetToDisplay();
            }
        }
    }

    //Citation: simple drawing player
    //Citation: https://www.javatpoint.com/how-to-change-titlebar-icon-in-java-awt-swing
    //EFFECTS: Initialize all jSwing component for GUI. set up JFrame, initialize all buttons, Text area, middle display
    //window and the left side panel for pet condition.

    public void initializeGraphics() {
        setIconImage(Toolkit.getDefaultToolkit().createImage(titleImageIcon));
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(appWindowWidth, appWindowHeight));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createTopPanel();
        createBottomTextArea();
        createCenterDisplayWindow();
        createSidePanel();

        this.getContentPane().add(buttons, BorderLayout.PAGE_START);
        this.getContentPane().add(leftScreen, BorderLayout.LINE_START);
        this.getContentPane().add(centerScreen, BorderLayout.CENTER);
        //this.getContentPane().add(buttons, BorderLayout.LINE_END);
        this.getContentPane().add(outputConsole, BorderLayout.PAGE_END);

    }

    //EFFECTS: Initializing center component
    public void createCenterDisplayWindow() {
        centerScreen = new JPanel();
        centerScreen.setLayout(new FlowLayout());
        displayedLabel = new JLabel();
        changeDisplayImageBaseOnPet(player.getPets().get(0));
        centerScreen.setPreferredSize(new Dimension(400,300));
        centerScreen.add(displayedLabel);
    }

    //Citation: https://stackoverflow.com/questions/36054058/how-to-read-a-gif-
    // with-javas-imageio-and-preserve-the-animation
    //MODIFIES: this
    //EFFECTS: set the center display image to image from imageSource.
    public void setDisplayImage(String imageSource) {
        currentImageDisplaying = new ImageIcon(Toolkit.getDefaultToolkit().createImage(imageSource));
        displayedLabel.setIcon(currentImageDisplaying);
    }

    //EFFECTS: Create a JPanel for top side such as initialize all buttons and labels and add them to this JPanel.
    public void createTopPanel() {
        buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        createViewPetComboBox();

        buttons.add(createAddPetButton());
        buttons.add(createFeedButton());
        buttons.add(createCleanButton());
        buttons.add(new JLabel("Current Pet: "));
        buttons.add(viewPetsBox);
        buttons.add(createSaveAndLoadButton());
        buttons.add(createSettingButton());
    }

    //EFFECTS: create clean button.
    private JButton createCleanButton() {
        return new JButton("Clean");
    }

    //EFFECTS: create setting button.
    private JButton createSettingButton() {
        return new JButton("Setting");
    }

    //EFFECTS: create feed button that calls feed method.
    private JButton createFeedButton() {
        //Initialize for feed button.
        JButton feedPetButton = new JButton("Feed Pet");
        feedPetButton.addActionListener(e -> {
            feedButtonPopUp();
            updatePetToDisplay();
        });

        return feedPetButton;
    }

    //EFFECTS: create  viewPet Combo Box.
    private void createViewPetComboBox() {
        //Initialize for pet collection view combo box
        viewPetsBox = new JComboBox<>();
        addAllPetsToViewComboBox();

        viewPetsBox.addActionListener(e -> {
            JComboBox storageCB = (JComboBox)e.getSource();
            selectedPetName = (String) storageCB.getSelectedItem();
            updatePetToDisplay();
        });
    }

    //EFFECTS: Add every pet player has as a string to viewPetBox.
    public void addAllPetsToViewComboBox() {
        for (int i = 0; i < this.player.getPets().size(); i++) {
            addPetToPetComboBox(this.player.getPets().get(i));
        }
    }

    //EFFECTS: creating save/load button that does save and load.
    private JButton createSaveAndLoadButton() {
        //Initialize for Save/load button
        JButton saveAndLoadButton = new JButton("Save/Load");
        saveAndLoadButton.addActionListener(e -> saveLoadPopUp());
        return saveAndLoadButton;
    }

    //EFFECTS: creates add pet button and action performed by clicking it.
    public JButton createAddPetButton() {
        JButton morePetButton = new JButton("More Pet");
        morePetButton.addActionListener(e -> {
            morePetPopUp();
            updatePetToDisplay();
        });

        return morePetButton;
    }

    //Citation: https://stackoverflow.com/questions/21957696/how-to-use-joptionpane-with-many-options-java
    //creating dialogs with more than 2 choices.
    private void feedButtonPopUp() {

        String[] options = createStringArrayOfInventoryItems();

        String message = "Which food would you like to feed with?";
        int userChoice = JOptionPane.showOptionDialog(this, message, "Pick a food", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,"delete");
        String[] optionsForPet;
        if (this.player.getPets().size() <= 1) {
            optionsForPet = new String[]{this.player.getPetByNumber(0).getAnimal().getAnimalName()};
        } else {
            optionsForPet = createStringArrayOfPets();
        }


        String messageForPet = "Which pet you want to feed on?";
        int userChoiceForPet = JOptionPane.showOptionDialog(this, messageForPet,
                "Pick a pet to feed with.", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, optionsForPet, options[0]);

        if (userChoice == 0) {
            player.useFood(player.getFoodFromInventory(options[0]),userChoiceForPet);
        } else if (userChoice == 1) {
            player.useFood(player.getFoodFromInventory(options[1]),userChoiceForPet);
        } else if (userChoice == 2) {
            player.useFood(player.getFoodFromInventory(options[2]),userChoiceForPet);
        }  // Do nothing if player close the popup


    }

    //EFFECTS: create an array of string with names of all inventory items
    public String[] createStringArrayOfInventoryItems() {
        String[] temp = new String[this.player.getInventory().size()];
        for (int i = 0; i < this.player.getInventory().size(); i++) {
            temp[i] = this.player.getInventory().get(i).getName();
        }

        return temp;
    }

    //EFFECTS: create an array of string with names of all player's pet.
    public String[] createStringArrayOfPets() {
        String[] temp = new String[this.player.getPets().size()];
        for (Pet pet: this.player.getPets()) {
            temp[this.player.getPets().indexOf(pet)] = pet.getAnimal().getAnimalName();
        }
        return temp;
    }


    //EFFECTS: perform the option of adding another pet by asking name,
    //         and type of pet player want and add to player pets.
    private void morePetPopUp() {
        String[] options = {"Cat", "Dog"};
        String message = "What pet would you like?";
        int userChoice = JOptionPane.showOptionDialog(this, message, "New pet", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, "delete");

        if (userChoice == 0) { // pick cat
            String petName = JOptionPane.showInputDialog("Please enter name of this pet.");
            Pet temp = new Pet(petName, "Cat");
            player.addPet(temp);
            addPetToPetComboBox(temp);
        } else if (userChoice == 1) { // pick dog
            String petName = JOptionPane.showInputDialog("Please enter name of this pet.");
            Pet temp = new Pet(petName, "Dog");
            player.addPet(temp);
            addPetToPetComboBox(temp);
        }  // Do nothing if player close the popup

    }

    //EFFECTS: perform the option of adding first pet by asking name,
    //         and type of pet player want and add to player pets.
    private void firstPetPopUp() {
        String[] options = {"Cat", "Dog"};
        String message = "What pet would you like?";
        int userChoice = JOptionPane.showOptionDialog(this, message, "New pet", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, "delete");

        if (userChoice == 0) { // pick cat
            String petName = JOptionPane.showInputDialog("Please enter name of this pet.");
            Pet temp = new Pet(petName, "Cat");
            player.addPet(temp);

        } else if (userChoice == 1) { // pick dog
            String petName = JOptionPane.showInputDialog("Please enter name of this pet.");
            Pet temp = new Pet(petName, "Dog");
            player.addPet(temp);
        }  //


    }

    //EFFECTS: add specific pet to the pet box combo box.
    public void addPetToPetComboBox(Pet pet) {
        viewPetsBox.addItem(pet.getAnimal().getAnimalName());
    }


    //EFFECTS: create and initialize for the left side JPanel with layout of grid, and add the sidescreen.
    public void createSidePanel() {
        barTemplate = new Rectangle(0,0,100,10);
        leftScreen = new JPanel();
        leftScreen.setLayout(new GridLayout(1,1,2,0));
        leftScreen.setPreferredSize(new Dimension(400,300));

        //default display the first pet.
        ss = new SideScreen(this.player.getPets().get(0), barTemplate, player.getScore());
        leftScreen.add(ss,0);
    }

    //EFFECTS: updates the middle screen with specify pet.
    public void updateMiddleAndSideScreen(Pet pet) {
        ss.setScore(player.getScore());
        ss.setPet(pet);
        changeDisplayImageBaseOnPet(pet);
        repaint();
        pack();
    }


    //EFFECTS: set display base on pet type.
    public void changeDisplayImageBaseOnPet(Pet pet) {
        if (pet.getAnimal().getType().equals("Cat")) {
            setDisplayImage(catIdleImageSource);
        } else {
            setDisplayImage(dogIdleImageSource);
        }
    }


    //MODIFIES:this
    //EFFECTS: update the display pet
    public void updatePetToDisplay() {
        Pet petToDisplay = null;
        if (selectedPetName == null) {
            selectedPetName = player.getPets().get(0).getAnimal().getAnimalName();
        }
        for (Pet pet : this.player.getPets()) {
            if (selectedPetName.equals(pet.getAnimal().getAnimalName())) {
                petToDisplay = pet;
            }
        }
        if (petToDisplay == null) {
            outputConsole.append("Could not find pet in player collection");
        } else {
            updateMiddleAndSideScreen(petToDisplay);
        }
    }

    //creating text area
    public void createBottomTextArea() {
        outputConsole = new JTextArea();
        outputConsole.setMinimumSize(new Dimension(300,300));
        outputConsole.setFont(new Font("DIALOG", Font.PLAIN,30));
        outputConsole.append("Game objective: Keep pet alive to earn more closeness"
                + " which counts towards player score.");
    }

    public void saveLoadPopUp() {
        String[] options = {"Save","Load"};
        String test = "Would you like to save or load";
        int userChoice = JOptionPane.showOptionDialog(this,test,"save or load", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,options, "delete");

        if (userChoice == 0) { //if user  pick save
            savePlayerProfile();
        } else if (userChoice == 1) {  //if user  pick load
            loadPlayerProfile();
            EventLog.getInstance().clear();
            reloadViewPetBox();
            updatePetToDisplay();
        }  //if player close the popup, do nothing.
    }


    //EFFECTS: creates a new JCombo box and add all pets name to it.
    private void reloadViewPetBox() {
        viewPetsBox.removeAllItems();
        for (Pet pet : this.player.getPets()) {
            addPetToPetComboBox(pet);
        }
        viewPetsBox.repaint();
    }


    //EFFECTS: Loading to previous game progress.
    public void previousProgressInitialization() {
        String test = "Would you like to reload in saved progress or start new progress?";
        int userChoice = JOptionPane.showOptionDialog(this,test,"Initialization", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,null, "delete");

        if (userChoice == 0) {
            loadPlayerProfile();
        } else {
            newPlayerInitialization();
        }
    }

    //EFFECTS: Initialization for new player.
    public void newPlayerInitialization() {
//        System.out.println("Please enter your name.");
//        player = new Player(scanner.nextLine());
//        System.out.println("Please choose what pet would you like? \n  Enter 'Cat' or 'Dog'");
//        String petType = scanner.nextLine();
//        while ((!Objects.equals(petType, "Cat")) && (!Objects.equals(petType, "Dog"))) {
//            System.out.println("Wrong user input, please enter Cat or Dog");
//            petType = scanner.nextLine();
//        }
//        System.out.println("Please enter your pet name: ");
//        String petName = scanner.nextLine();
//        player.addPet(new Pet(petName,petType));
        String playerName = JOptionPane.showInputDialog("Please enter your name.");
        player = new Player(playerName);
        firstPetPopUp();
        inventoryInitialize();
        selectedPetName = this.player.getPets().get(0).getAnimal().getAnimalName();
    }

    //EFFECTS: Initialize the inventory
    public void inventoryInitialize() {
        this.player.getInventory().add(new Food("apple", 10));
        this.player.getInventory().add(new Food("banana", 20));
        this.player.getInventory().add(new Food("orange", 30));

    }

    //EFFECTS: save player profile to file
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void savePlayerProfile() {
        try {
            jsonWriter.openPrintWriter();
            jsonWriter.write(player);
            jsonWriter.closePrintWriter();
            System.out.println(player.getPlayerName() + "'s profile has been saved to " + JSON_Storage);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save game progress to:" + JSON_Storage);
        }
    }

    //EFFECTS: load player profile from file, then select the first pet player have to display.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void loadPlayerProfile() {
        try {
            player = jsonReader.read();
            EventLog.getInstance().clear();
            System.out.println(player.getPlayerName() + "'s profile has been loaded from " + JSON_Storage);
        } catch (IOException e) {
            System.out.println("Unable to load game progress from: " + JSON_Storage);
        }
    }



//    //EFFECTS: prints out the status of specific pet.
//    public void getPetStatus(Pet pet) {
//        System.out.print(
//                "----------------------------------------------------------\n"
//                        + "Pet name: " + pet.getAnimal().getAnimalName()
//                        + "\nHealth level: " + pet.getAnimal().getHealthLevel()
//                        + "\nCloseness level: " + pet.getAnimal().getClosenessLevel()
//                        + "\nPoop meter: " + pet.getAnimal().getPoopMeter()
//                        + "\nCurrent action: ");
//        pet.getAnimal().getAction().performsAction(Action.randomNumber(0,3));
//
//    }

//    //EFFECTS: prints out all pet's name from pets(player pet collection).
//    public void displayPets() {
//        if (player.getPets().size() != 0) {
//            for (Pet pet:player.getPets()) {
//                System.out.print(player.getPets().indexOf(pet) + "." + pet.getAnimal().getAnimalName() + "\t");
//            }
//        } else {
//            System.out.println("You have no pet in your house now.");
//        }
//        System.out.println();
//    }

//    //EFFECTS: Game menu which prints out the option player can choose.
//    public void gameMenu() {
//        System.out.println(
//                "-------------------------------------------------------------\n"
////                + "Player name: " + this.player.getPlayerName() + "\t"
////                + "Pet name: " + player.getPet(defaultPetNumber).getAnimal().getAnimalName()
//                + "Hi " + this.player.getPlayerName()
//                + ",\n"
//                + "What would you like to do? \n\n"
//                + "option 0: Get more pets!! \n"
//                + "option 1: Feed pet\n"
//                + "option 2: Play with pet (Not available now)\n"
//                + "option 3: Clean up the house (Not available now)\n"
//                + "option 4: View pet collection\n"
//                + "option 5: Grab more pet food   (Not available now)\n"
//                + "option 6: Settings\n"
//                + "option 7: Save / Load\n"
//                + "option 9: Exit the game\n"
//                + "-------------------------------------------------------------\n");
//
//        System.out.println("Pick an option by entering specific number (ie. 1, 2, etc):  ");
//
//    }

//    //EFFECTS: prints out the setting menu for player.
//    public void settingMenu() {
//        System.out.println(
//                "-------------------------------------------------------------\n"
//                        + "Hi " + this.player.getPlayerName()
//                        + ",\n"
//                        + "What would you like to do? \n\n"
//                        + "option 0: Change pet name \n"
//                        + "option 1: Change player name\n"
//                        + "option 2: Do nothing\n"
//                        + "-------------------------------------------------------------\n");
//
//        System.out.println("Pick an option by entering specific number (ie. 1, 2, etc):  ");
//
//    }

//    //REQUIRES: input is an int.
//    //EFFECTS: Perform the option base on option number.
//    public void performOption(int optionNumber) {
//        if (optionNumber == 0) {
//            //addAnotherPetOption();
//        } else if (optionNumber == 1) {
//            //feedOption();
//        } else if (optionNumber == 2) {
//            System.out.println("option 2 not available now.");
//
//        } else if (optionNumber == 3) {
//            System.out.println("option 3 not available now.");
//
//        } else if (optionNumber == 4) {
//            viewPetCollection();
//
//        } else if (optionNumber == 5) {
//            System.out.println("option 5 not available now.");
//
//        } else if (optionNumber == 6) {
//            performSettingOption();
//
//        } else if (optionNumber == 7) {
//            performSaveLoadOption();
//
//        } else if (optionNumber == exitNumber) {
//            System.out.println("Exiting the game.  ");
//
//        } else {
//            System.out.println("Incorrect input,please enter number according to options(ie. 1, 2, etc):  ");
//        }
//
//
//    }

//    private void saveLoadMenu() {
//        System.out.println(
//                "-------------------------------------------------------------\n"
//                        + "Hi " + this.player.getPlayerName()
//                        + ",\n"
//                        + "What would you like to do? \n\n"
//                        + "option 0: Save game progress \n"
//                        + "option 1: Load game progress\n"
//                        + "option 2: return to main menu\n"
//                        + "-------------------------------------------------------------\n");
//
//        System.out.println("Pick an option by entering specific number (ie. 1, 2, etc):  ");
//
//    }

//    private void performSaveLoadOption() {
//        saveLoadMenu();
//        String userInput = scanner.nextLine();
//        if (Integer.parseInt(userInput) == 0) {
//            savePlayerProfile();
//        } else if (Integer.parseInt(userInput) == 1) {
//            loadPlayerProfile();
//        } else if (Integer.parseInt(userInput) == 2) {
//            //do nothing
//        } else {
//            System.out.println("Invalid input");
//        }
//
//    }

//    //EFFECTS: Performs setting options. First prints the menu by calling such function,hen do option base
//    //on player input.
//    private void performSettingOption() {
//        settingMenu();
//        int optionNumber = Integer.parseInt(scanner.nextLine());
//        if (optionNumber == 0) {
//            displayPets();
//            System.out.println("Pick the pet you want to rename by enter the number.");
//            int playerPetNumber = Integer.parseInt(scanner.nextLine());
//            System.out.println("Enter new name for this pet: "
//                    + player.getPetByNumber(playerPetNumber).getAnimal().getAnimalName());
//            System.out.println();
//
//            player.getPetByNumber(playerPetNumber).getAnimal().setAnimalName(scanner.nextLine());
//        } else if (optionNumber == 1) {
//            System.out.println("Enter new name for player");
//
//            player.setPlayerName(scanner.nextLine());
//
//        } else {
//            System.out.println("Incorrect input,please enter number according to options(ie. 1, 2, etc):  ");
//        }
//
//    }


//    //EFFECTS: Display names of all pets from player and ask which one's status player would like to view.
//    private void viewPetCollection() {
//        System.out.println("Here are all you pets\n");
//        displayPets();
//        System.out.println("Which pet's status would you like to view?\n"
//                        + "Pick pet by entering the number before their name\n");
//
//        getPetStatus(player.getPets().get(Integer.parseInt(scanner.nextLine())));
//
//    }






}
