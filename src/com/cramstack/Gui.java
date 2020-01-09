package com.cramstack;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Queue;

public class Gui extends JFrame {

    public int ROW = 74;
    public int COL = 43;

    public int SPACE = 2 ;
    public int BOX_SIZE = 20;
    public int MOUSE_X = -1;
    public int MOUSE_Y = -1;
    public int BOX_ROW = -1;
    public int BOX_COL = -1;
    public int ACTION = -1;
    public Integer START_X = -1;
    public Integer START_Y = -1;
    public Integer END_X = -1;
    public Integer END_Y = -1;
    public boolean MOUSE_PUSH = false;

    int [][] bord = new int[ROW + 2][COL + 2];
    boolean [][] mark = new boolean[ROW + 2][COL + 2];
    GPath [][] path = new GPath[ROW + 2][COL + 2];


    Button start = new Button("Start Point");
    Button end = new Button("End Point");
    Button obstruction = new Button("Put Obstruction");
    Button find_path = new Button("Find Path");
    Button reSetAll = new Button("Reset");

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
        bord.add(find_path);
        bord.add(reSetAll);

        MouseMove mouseMove = new MouseMove();
        this.addMouseMotionListener(mouseMove);

        MouseAction mouseAction = new MouseAction();
        this.addMouseListener(mouseAction);

        obstruction.setEnabled(false);
        end.setEnabled(false);
        find_path.setEnabled(false);

        start.addActionListener(event -> {
            ACTION = 0;
        });

        obstruction.addActionListener(event -> {
            ACTION = 1;
        });

        end.addActionListener(event -> {
            ACTION = 2;
        });

        find_path.addActionListener(event -> {
            bfs();
            if(!mark[END_X][END_Y]){
                JOptionPane.showMessageDialog(this, "Can't reach the destination");
            }
            else{
                print_path(END_X , END_Y);
                this.bord[START_X][START_Y] = 1;
            }
        });

        reSetAll.addActionListener(event -> {
            clearAll();
        });

    }

    private void clearAll() {

        this.MOUSE_X = -1;
        this.MOUSE_Y = -1;
        this.BOX_ROW = -1;
        this.BOX_COL = -1;
        this.ACTION = -1;
        this.START_X = -1;
        this.START_Y = -1;
        this.END_X = -1;
        this.END_Y = -1;
        this.MOUSE_PUSH = false;

        for(int i = 0 ; i < ROW ; ++i){
            for(int j = 0 ; j < COL ; ++j){
                mark[i][j] = false;
                bord[i][j] = 0;
            }
        }

        end.setEnabled(false);
        obstruction.setEnabled(false);
        find_path.setEnabled(false);
        start.setEnabled(true);
    }

    private void print_path(int x , int y) {

        if(bord[x][y] == 1){
            return;
        }
        print_path(path[x][y].row , path[x][y].col);
        bord[path[x][y].row][path[x][y].col] = 4;
    }

    private void bfs() {

        Integer [] dirX = {1 ,0,-1, 0};
        Integer [] dirY = {0 ,1, 0,-1};

        Queue < Integer > queue = new LinkedList<>();
        queue.add(START_X);
        queue.add(START_Y);
        resetMark();
        mark[START_X][START_Y] = true;

        while (!queue.isEmpty()){
            Integer nowX = queue.peek();
            queue.remove();
            Integer nowY = queue.peek();
            queue.remove();

            for(int i = 0 ; i < 4 ; ++i){
                Integer nowXX = nowX + dirX[i];
                Integer nowYY = nowY + dirY[i];

                if(nowXX >= 1 && nowXX < ROW && nowYY >= 3 && nowYY < COL && !mark[nowXX][nowYY] && bord[nowXX][nowYY] != 3){
                    queue.add(nowXX);
                    queue.add(nowYY);
                    mark[nowXX][nowYY] = true;
                    path[nowXX][nowYY] = new GPath(nowX , nowY);
                }
            }

        }

        //JOptionPane.showMessageDialog(null, mark[END_X][END_Y]);
    }

    private void resetMark() {
        for(int i = 0 ; i < ROW ; ++i){
            for(int j = 0 ; j < COL ; ++j){
                mark[i][j] = false;
            }
        }
    }

    public class Bord extends JPanel{

        public void paintComponent(Graphics g){

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0 , 0 , 1510 , 915);
            for(int i = 1 ; i < ROW ; ++i){
                for(int j = 3 ; j < COL ; ++j){
                   // g.setColor(Color.GRAY);
                    /*if(MOUSE_X >= (SPACE + i * BOX_SIZE) && MOUSE_X < (SPACE + i * BOX_SIZE + BOX_SIZE -  SPACE) &&
                            MOUSE_Y >= SPACE + j * BOX_SIZE + BOX_SIZE + (5 * SPACE ) && MOUSE_Y < SPACE + j * BOX_SIZE + BOX_SIZE + BOX_SIZE + (5 * SPACE) ) {

                        g.setColor(Color.RED);
                    }*/

                    if(bord[i][j] == 0){
                        g.setColor(Color.GRAY);
                    }
                    else if(bord[i][j] == 1){
                        g.setColor(Color.RED);
                    }
                    else if(bord[i][j] == 2){
                        g.setColor(Color.YELLOW);
                    }
                    else if(bord[i][j] == 3){
                        g.setColor(Color.BLACK);
                    }
                    else if(bord[i][j] == 4){
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect((SPACE + i * BOX_SIZE), (SPACE + j * BOX_SIZE), (BOX_SIZE - 2 * SPACE), (BOX_SIZE - 2 * SPACE));
                }
            }
        }
    }



    public int getBoxRow(int X){

        int temp = -1;

        for(int i = 1 ; i < ROW ; ++i){
            for(int j = 1 ; j < COL ; ++j){
                if(X >= (SPACE + i * BOX_SIZE) && X < (SPACE + i * BOX_SIZE + BOX_SIZE -  SPACE)){
                    temp = i;
                    break;
                }
            }
            if(temp != -1) break;
        }
        return temp;
    }

    public int getBoxCol(int Y){

        int temp = -1;

        for(int i = 1 ; i < ROW ; ++i){
            for(int j = 1 ; j < COL ; ++j){
                if(Y >= SPACE + j * BOX_SIZE + BOX_SIZE + (5 * SPACE ) && Y < SPACE + j * BOX_SIZE + BOX_SIZE + BOX_SIZE + (5 * SPACE)) {
                    temp = j ;
                    break;
                }
            }
            if(temp != -1) break;
        }
        return temp;
    }


    public class MouseMove implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

            if(getBoxRow(mouseEvent.getX()) != -1 && getBoxCol(mouseEvent.getY()) != -1 && MOUSE_PUSH) {
                if(ACTION == 1){
                    bord[getBoxRow(mouseEvent.getX())][getBoxCol(mouseEvent.getY())] = 3;
                    System.out.println(getBoxRow(mouseEvent.getX()) + " " + getBoxCol(mouseEvent.getY()));
                }
            }

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
            BOX_ROW = getBoxRow(MOUSE_X);
            BOX_COL = getBoxCol(MOUSE_Y);
            if(BOX_ROW != -1 && BOX_COL != -1) {

                if(ACTION == 0){
                    bord[BOX_ROW][BOX_COL] = 1;
                    START_X = BOX_ROW;
                    START_Y = BOX_COL;
                    start.setEnabled(false);
                    end.setEnabled(true);
                    obstruction.setEnabled(true);
                    ACTION = -1;
                }
                else if(ACTION == 2){
                    bord[BOX_ROW][BOX_COL] = 2;
                    END_X = BOX_ROW;
                    END_Y = BOX_COL;
                    start.setEnabled(false);
                    end.setEnabled(false);
                    obstruction.setEnabled(true);
                    find_path.setEnabled(true);
                    ACTION = -1;
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            MOUSE_PUSH = true;
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            MOUSE_PUSH = false;
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    public class GPath {
        public int row;
        public int col;

        public GPath(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
