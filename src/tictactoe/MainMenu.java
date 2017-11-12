/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;


import java.awt.Dimension;
import SoapConnector.Soap;
import java.awt.GridLayout;
import static java.awt.SystemColor.menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author jakec
 */
public class MainMenu extends JFrame implements ActionListener {
    
                     
    private javax.swing.JButton quit;
    private javax.swing.JButton invite;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel menuText;
    private String uname;
    private Soap db;
    private String type = "O";
    private boolean threadRun = true;
    // End of variables declaration    
    
    public MainMenu(String uname, Soap db){
        this.uname = uname;
        this.db = db;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        db.setOnline(uname);
        db.resetGame(uname);
        menu = new javax.swing.JPanel();
        invite = new javax.swing.JButton();
        quit = new javax.swing.JButton();
        menuText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        invite.setText("Invite a Player");
        invite.addActionListener(this);
        
        quit.setText("Quit");
        quit.addActionListener(this);

        menuText.setText(getStats());

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menuText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(invite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(137, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(menuText)
                .addGap(45, 45, 45)
                .addComponent(invite)
                .addGap(52, 52, 52)
                .addComponent(quit)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton source = (JButton) ae.getSource();    
       
        if(source.equals(quit)){
            dispose();
            threadRun = false;
            try {
                db.setOffline(uname);
            } catch (Exception ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(source.equals(invite)){
            try {
                Object options[] = db.getOptions(uname);
                if(options.length > 1){
                String input = (String) JOptionPane.showInputDialog(null, "Players online",
        "Choose a player to play against", JOptionPane.QUESTION_MESSAGE, null, // Use
                                                                        // default
                                                                        // icon
        options, // Array of choices
        options[0]);
                    
                if(input != null && !input.equals("None"))
                    db.invite(uname, input);
                }
                else
                    JOptionPane.showMessageDialog(null, "Sorry nobody else is online");
            } catch (Exception ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void displayJoinerOption(String u) throws Exception{
        int selection = JOptionPane.showConfirmDialog(null, "Play against "+u+"?", ""+u+"?", JOptionPane.YES_NO_OPTION);
        if(selection == 0){
            db.beginGame(uname, u);
            type = "X";
            nextScreen(u);
            threadRun = false;
        }
        else {
            db.denyGame(uname);
            threadRun = false;
        }
    }

    public void nextScreen(String joiner) throws Exception {
        dispose();
        db.beginGame(uname, joiner);
        GameThread t = new GameThread(uname, joiner, type, db);
        t.start();
    }
    
    public boolean active(){
        return threadRun;
    }
    
    public String getStats(){
        return db.getStats(uname);
    }
    
}
