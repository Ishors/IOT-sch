import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Service {
	private String name;
	protected PrintWriter pw;

	public Service(String name) {
		this.name = name;
		FileWriter fw;
		try {
			fw = new FileWriter("log_" + this.name + ".txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			this.pw = new PrintWriter(bw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeData(Thing thing) {
		// On r�cup�re la date actuelle et on la formate comme souhait�
		Date now = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		String d = formater.format(now);
		// On �crit les donn�es (date + datagram) dans le fichier journal
		pw.println(d + ";" + thing.toString());
		pw.flush();
	}

	public void close() {
		pw.flush();
		pw.close();
	}
}
