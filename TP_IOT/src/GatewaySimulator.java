
public class GatewaySimulator {
	public static void main(String args[]) {
		FileReader f = new FileReader("simu.txt");
		SocketClient s = new SocketClient("127.0.0.1", 51291);
		f.open();
		s.open();
		while (f.ready()) {
			// Avant d'appeler notre méthode writeDatagram on vérifie que la ligne lue dans
			// le fichier n'est pas vide
			if (f.readDatagram() != null || f.readDatagram().equals("Erreur")) {
				s.writeDatagram(f.readDatagram());
			}
		}
		f.close();
		s.close();
	}
}
