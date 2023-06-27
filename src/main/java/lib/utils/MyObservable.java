package lib.utils;

import java.util.Vector;

public class MyObservable {
    protected Vector<MyObserver> observers;

    public MyObservable() {
        this.observers = new Vector<>();
    }

    public void addObserver(MyObserver observer) {
        if (observer == null)
            throw new NullPointerException();
        if (!observers.contains(observer)) {
            observers.addElement(observer);
        }
    }

    public void deleteObserver(MyObserver observer) {
        observers.removeElement(observer);
    }

    protected void notifyObservers() {
        for (MyObserver observer : observers) {
            observer.onUpdate(this);
        }
    }
}
