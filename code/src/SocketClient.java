
import java.net.Socket;
import java.io.PrintStream;


public class SocketClient implements DataSender {

    private int port ;
    private String ipAddress ;
    private Socket myClient;
    private PrintStream ps;

    
    public SocketClient (String ipAddress, int port) {
        this.port = port ;
        this.ipAddress = ipAddress ;
    }
    public void open () {
        try {
            this.myClient = new Socket(this.ipAddress, this.port);
            this.ps = new PrintStream(this.myClient.getOutputStream());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void writeDatagram (String datagram) {
        try {
            this.ps.println(datagram);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public boolean ready () {
        return true ;
    }
    public void close () {
        try {
            this.ps.println("disconnect");
            this.ps.close();
            this.myClient.close();
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
