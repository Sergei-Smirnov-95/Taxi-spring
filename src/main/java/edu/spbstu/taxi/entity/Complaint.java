package edu.spbstu.taxi.entity;

import lombok.Data;

@Data
public class Complaint {
    private String textComplaint;
    private boolean isConfirmed;

    public String getPassengerText(){
        return textComplaint;
    }
    public void setConfirmed(boolean c){
        isConfirmed = c;
    }
}
