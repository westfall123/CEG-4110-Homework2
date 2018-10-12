package com.example.westf.homework2;

import java.util.Stack;

public class Edit {

    Stack<int[]> undo;
    Stack<int[]> redo;
    ClockController ck;

    public Edit(ClockController ck) {
        this.ck = ck;
        undo = new Stack();
        redo = new Stack();
    }

    //keep a record of the previous time on the undo stack
    public void addNewundo(int[] time) {
        undo.push(time);
    }

    //push the previous time to the clock controller and also put the current time on the redo stack
    public void undo(int[] currentTime) {
        if (checkundo()) {
            int[] tmp = undo.pop();
            ck.setTime(tmp[0], tmp[1], tmp[2]);
            redo.push(currentTime
            );
        }
    }

    //push the last time displayed to the clock and save the current time to the undo stack
    public void redo(int[] currentTime) {
        if (checkRedo()) {
            checkRedo();
            int[] tmp = redo.pop();
            ck.setTime(tmp[0], tmp[1], tmp[2]);
            undo.push(currentTime);
        }
    }

    //check if their is anything to undo
    public boolean checkundo() {
        if (undo.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    //check if their is anything to redo
    public boolean checkRedo() {
        if (redo.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
