package com.cramstack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Gui extends JFrame {

    public int ROW = 74;
    public int COL = 43;

    public int SPACE = 2 ;
    public int BOX_SIZE = 20;
    public int MOUSE_X = -1;
    public int MOUSE_Y = -1;
    public int BOX_ROW = -1;
    public int BOX_COL = -1;

    int [][] visit = new int[ROW + 2][COL + 2];


    Button start = new Button("Start Point");
    Button end = new Button("End Point");
    Button obstruction = new Button("Obstruction Point");

    public Gui() throws HeadlessException {

        this.setTitle("Game");
        this.setSize(1510 , 915);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        Bord bord = new Bord();
        this.setContentPane(bord);

        bord.add(start);
        bord.add(obstruction);
        bord.add(end);

        MouseMove mouseMove = new MouseMove();
        this.addMouseMotionListener(mouseMove);

        MouseAction mouseAction = new MouseAction();
        this.addMouseListener(mouseAction);

        start.addActionListener(event -> {
            obstruction.setEnabled(false);
            end.setEnabled(false);

        });

        obstruction.addActionListener(event -> {
            start.setEnabled(false);
            end.setEnabled(false);
        });

        end.addActionListener(event -> {
            start.setEnabled(false);
            obstruction.setEnabled(false);
        });

    }

    public class Bord extends JPanel{

        public void paintComponent(Graphics g){

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0 , 0 , 1510 , 915);
            for(int i = 1 ; i < ROW ; ++i){
                for(int j = 3 ; j < COL ; ++j){
                    g.setColor(Color.GRAY);
                    if(MOUSE_X >= (SPACE + i * BOX_SIZE) && MOUSE_X < (SPACE + i * BOX_SIZE + BOX_SIZE -  SPACE) &&
                            MOUSE_Y >= SPACE + j * BOX_SIZE + BOX_SIZE + (5 * SPACE ) && MOUSE_Y < SPACE + j * BOX_SIZE + BOX_SIZE + BOX_SIZE + (5 * SPACE) ) {

                        g.setColor(Color.RED);
                    }
                    g.fillRect((SPACE + i * BOX_SIZE), (SPACE + j * BOX_SIZE), (BOX_SIZE - 2 * SPACE), (BOX_SIZE - 2 * SPACE));
                }
            }
        }
    }



    public int getBoxRow(){

        int x = -1;

        for(int i = 1 ; i < ROW ; ++i){
            for(int j = 1 ; j < COL ; ++j){
                if(MOUSE_X >= (SPACE + i * BOX_SIZE) && MOUSE_X < (SPACE + i * BOX_SIZE + BOX_SIZE -  SPACE)){
                    x = i;
                    break;
                }
            }
            if(x != -1) break;
        }
        return x;
    }

    public int getBoxCol(){

        int x = -1;

        for(int i = 1 ; i < ROW ; ++i){
            for(int j = 1 ; j < COL ; ++j){
                if(MOUSE_Y >= SPACE + j * BOX_SIZE + BOX_SIZE + (5 * SPACE ) && MOUSE_Y < SPACE + j * BOX_SIZE + BOX_SIZE + BOX_SIZE + (5 * SPACE)) {
                    x = j;
                    break;
                }
            }
            if(x != -1) break;
        }
        return  x;
    }

    public void reSetVisitArray(){

        for(int i = 0 ; i < ROW ; ++i){
            for(int j = 0 ; j < COL ; ++j){
                visit[i][j] = -1;
            }
        }
    }




    public class MouseMove implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            System.out.println(getBoxRow() + " " + getBoxCol());
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            MOUSE_X = mouseEvent.getX();
            MOUSE_Y = mouseEvent.getY();
        }
    }

    public class  MouseAction implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            BOX_ROW = getBoxRow();
            BOX_COL = getBoxCol();

            //System.out.println(BOX_COL + " " + BOX_ROW);
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            System.out.println(getBoxRow() + " " + getBoxCol());
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }



}
