import java.io.*;

public class KeyboardInput implements DataReceiver {
	private boolean ok;

	public KeyboardInput() {
		this.ok = false;
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub
		this.ok = true;
	}

	@Override
	public String readDatagram() {
		// TODO Auto-generated method stub

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("MAC ADRESS: ");
			String mac = br.readLine();
			if (mac.equals("quit")) {
				this.ok = false;
				return null;
			} else {
				System.out.println("DATAGRAM: ");
				String datagram = br.readLine();
				String chaine = mac + ";" + datagram;
				return chaine;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Erreur";
	}

	@Override
	public boolean ready() {
		// TODO Auto-generated method stub
		return this.ok;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.ok = false;
	}
}
