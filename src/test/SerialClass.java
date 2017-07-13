package test;

import java.awt.event.MouseEvent;
import java.io.Serializable;

public class SerialClass implements Serializable{

    int a;
    int b;
    MouseEvent mouseEvent = null;

    public SerialClass(int a,int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "SerialClass{" +
                "a=" + a +
                ", b=" + b +
                ", mouseEvent=" + mouseEvent +
                '}';
    }
}
