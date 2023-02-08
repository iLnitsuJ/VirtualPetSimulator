package ui;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

//Citation:https://greenteapress.com/thinkjava6/html/thinkjava6017.html
// This side screen class is responsible for drawing the animal condition display on the left side of GUI.

public class SideScreen extends JPanel {
    private Pet pet;
    private Rectangle barSize;
    int barAlignmentValueXAxis;

    public void setScore(int score) {
        this.score = score;
    }

    private int score;

    public SideScreen(Pet pet, Rectangle barSize, int score) {
        super();
        this.pet = pet;
        this.barSize = barSize;
        this.score = score;
    }

    //This is called every time the screen is painting
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawStatBars(g);
        } catch (IOException e) {
            System.out.println("Image is not found");
        }
    }

    //EFFECTS: draws stats bar for animal pet
    private void drawStatBars(Graphics g) throws IOException {
        barAlignmentValueXAxis = 150;
        g.setFont(new Font(Font.SERIF,Font.PLAIN,25));
        displayHealth(g);
        displayPoopMeter(g);
        displayStarveMeter(g);
        displayCloseness(g);
    }

    //EFFECTS: prints health, draws bar outline in black, and fill the inside with red.
    public void displayHealth(Graphics g) {
        //draw health label
        g.setColor(Color.BLACK);
        g.drawString("Health ",0,30);

        //draw for health meter bar
        g.drawRect(barAlignmentValueXAxis,15,(int)barSize.getWidth(),(int)barSize.getHeight());
        g.setColor(Color.RED);
        g.fillRect(barAlignmentValueXAxis,15,pet.getAnimal().getHealthLevel(),10);
        g.drawString(pet.getAnimal().getHealthLevel() + "%",270,30);
    }

    //EFFECTS: prints poop meter label , draws bar outline in black, and fill the inside with orange.
    public void displayPoopMeter(Graphics g) {
        //draw for Poop meter label
        g.setColor(Color.BLACK);
        g.drawString("Poop meter ",0,60);

        //draw for poop meter bar
        g.drawRect(barAlignmentValueXAxis,47,(int)barSize.getWidth(),(int)barSize.getHeight());
        g.setColor(Color.ORANGE);
        g.fillRect(barAlignmentValueXAxis,47,pet.getAnimal().getPoopMeter(),10);
        g.drawString(pet.getAnimal().getPoopMeter() + "%",270,60);
    }

    //EFFECTS: prints starve meter label, draws bar outline in black, and fill the inside with cyan.
    public void displayStarveMeter(Graphics g) {
        //draw starve meter label
        g.setColor(Color.BLACK);
        g.drawString("Starve meter ",0,90);

        //draw for starve meter bar
        g.drawRect(barAlignmentValueXAxis,77,(int)barSize.getWidth(),(int)barSize.getHeight());
        g.setColor(Color.CYAN);
        g.fillRect(barAlignmentValueXAxis,77,pet.getAnimal().getStarveMeter(),10);
        g.drawString(pet.getAnimal().getStarveMeter() + "%",270,90);
    }

    //EFFECTS: prints closeness, draws image of heart and display the score of player.
    private void displayCloseness(Graphics g) throws IOException {
        //draw closeness label
        g.setColor(Color.BLACK);
        g.drawString("Closeness ",0,120);
        g.drawString(Integer.toString(pet.getAnimal().getClosenessLevel()),barAlignmentValueXAxis,120);

        g.drawString("Player Score ",0,150);

        // Citation: https://stackoverflow.com/questions/36054058/how-to-read-a-gif-with-javas-imageio-and-preserve-the-animation
        // EFFECTS: Creating an image object from string source and draw it out.
        g.drawImage(ImageIO.read(new File("./data/heart_image_croped.png")), 180, 133, null);
        g.drawString(Integer.toString(this.score),barAlignmentValueXAxis, 150);
    }



    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
