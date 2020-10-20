import java.util.Date;

public class QuantifiedSelf extends Service {

	public QuantifiedSelf(String name) {
		super(name);
	}

	public void writeData(Thing thing) {
		// Ici on substitue la méthode writedData de la classe Service
		// On récupère la date actuelle
		Date now = new Date();
		long time = now.getTime();
		// On vérifie que le datagram contient bien la clé "geo", qui "caractérise" les
		// services de type QuantifiedSelf
		if (thing.existData("geo")) {
			// Et on écrit les données (date + datagram) dans le fichier journal
			pw.println(time + ";" + thing.toString());
			pw.flush();
		}
	}
}
