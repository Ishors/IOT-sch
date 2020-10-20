import java.io.*;

public class FileReader implements DataReceiver {
	private String filename;
	private BufferedReader br;
	private boolean ok;

	public FileReader(String filename) {
		this.filename = filename;
		this.br = new BufferedReader(new InputStreamReader(System.in));
	}

	public void open() {
		InputStream ips;
		try {
			ips = new FileInputStream(this.filename);
			InputStreamReader ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.ok = true;
	}

	public String readDatagram() {
		String datagram;
		try {
			datagram = br.readLine();
			if (datagram == null || datagram.equals("")) {
				this.ok = false;
				return null;
			} else {
				return datagram;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ("Erreur");
	}

	public boolean ready() {
		// TODO Auto-generated method stub
		return this.ok;
	}

	public void close() {
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.ok = false;
	}
}
