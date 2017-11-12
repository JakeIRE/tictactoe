
package SoapConnector;

import javax.xml.ws.WebServiceRef;
import tttWebData.TicTacToeWebService;
        
        
public class Soap {
    @WebServiceRef(wsdlLocation="http://localhost:8080/"
            + "TicTacToeWebClient/TicTacToeWebService?wsdl")
    static TicTacToeWebClient web;
    
    private TicTacToeWebService port;
    public Soap(){
       port = web.getTicTacToeWebServicePort();
   }
    
    public void beginGame(){
        
    }
    
    public String register(String uname, String pass){
        return port.register(uname, pass);
    }
    
    public String loginVerify(String uname, String pass){
        return port.loginVerify(uname, pass);
    }
    
    public Object[] getOptions(String u){
        return port.getOptions(u);
    }
    
    public void setOnline(String u){
        port.setOnline(u);
    }
    
    public void setOffline(String u){
        port.setOffline(u);
    }
    
    public String getJoiner(String u){
        return port.getJoiner(u);
    }
    
    public void beginGame(String uname, String joiner){
        port.beginGame(uname, joiner);
    }
    
    public void denyGame(String uname){
        port.denyGame(uname);
    }
    
    public String checkGameStart(String u){
        return port.checkGameStart(u);
    }
    
    public void invite(String uname, String invite){
        port.invite(uname, invite);
    }
    
    public String getTurn(String joiner){
        return port.getTurn(joiner);
    }
    
     public void setMove(String move, String uname)  {
        port.setMove(move, uname);
    }

    public void setDraw(String uname){
         port.setDraw(uname);
    }
    
    public void setWin(String uname){
         port.setWin(uname);
    }
    
    public void setLoss(String uname){
        port.setLoss(uname);
    }

    public void resetGame(String uname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getStats(String uname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
