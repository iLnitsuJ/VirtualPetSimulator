package model;


public class Action {

    //Action class record the current action as an int. By default, it is assigned with 0 which is walking.

    private int currentAction;

    public Action() {
        this.currentAction = -1;  //default action number.
    }

    public int getCurrentAction() {
        return this.currentAction;
    }

    public void setCurrentAction(int currentAction) {
        this.currentAction = currentAction;
    }

    //EFFECTS: Whenever this is called, random action is generated.Then record the current action
    //         as a number
    public void performsAction(int number) {

        if (number == 0) {
            System.out.print("walking\n");
            this.setCurrentAction(number);
        } else if (number == 1) {
            System.out.print("catching butterfly\n");
            this.setCurrentAction(number);
        } else if (number == 2) {
            System.out.print("peeing\n");
            this.setCurrentAction(number);
        } else if (number == 3) {
            System.out.print("sleeping\n");
            this.setCurrentAction(number);
        } else {
            System.out.print("no action number?!\n");
            this.setCurrentAction(-1);
        }


    }

    //EFFECTS: return a random number within min max, inclusively.
    public static int randomNumber(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }


}
