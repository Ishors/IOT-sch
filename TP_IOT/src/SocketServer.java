import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class SocketServer implements DataReceiver {

    private ServerSocket myService ;
    private Socket serviceSocket ;
    private BufferedReader br ;
    private boolean ok ; 

    public SocketServer (int port) {
        try {
            this.myService = new ServerSocket(port);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        this.ok = false ;
    }
    public void open () {
        try {
            System.out.println("[SRV] Server waiting for new connexion...");
            this.serviceSocket = this.myService.accept();
            System.out.println("[SRV] Client connected.");
            this.br = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
            this.ok = true ;
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public String readDatagram () {
        String res = null ;
        try {
            System.out.println("[SRV] Waiting for client data...");
            res = this.br.readLine() ;
            System.out.println("SERVER READ: "+res);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        if (res.equals("disconnect")) {
            /* Close client session */
            try {
                System.out.println("[SRV] Close client connexion.");
                this.br.close();
                this.serviceSocket.close();
            }
            catch (IOException e) {
                System.out.println(e);
            }
            /* Wait for new client */
            this.open() ;
            res = null ;
        }
        return res ;
    }
    public boolean ready () {
        return this.ok ;
    }
    public void close () {
        try {
            this.br.close();
            this.serviceSocket.close();
            this.myService.close();
        } 
        catch (IOException e) {
            System.out.println(e);
        }
    }

}