package com.cramstack;

public class Main implements Runnable {

    Gui gui = new Gui();

    public static void main(String[] args) {

        new Thread(new Main()).start();
    }

    @Override
    public void run() {
        while (true){
            gui.repaint();
            try{ Thread.sleep(200); } catch (Exception ex){}
        }
    }
}
