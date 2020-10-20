import java.util.Date;

public class QuantifiedSelf extends Service {

	public QuantifiedSelf(String name) {
		super(name);
	}

	public void writeData(Thing thing) {
		// Ici on substitue la m�thode writedData de la classe Service
		// On r�cup�re la date actuelle
		Date now = new Date();
		long time = now.getTime();
		// On v�rifie que le datagram contient bien la cl� "geo", qui "caract�rise" les
		// services de type QuantifiedSelf
		if (thing.existData("geo")) {
			// Et on �crit les donn�es (date + datagram) dans le fichier journal
			pw.println(time + ";" + thing.toString());
			pw.flush();
		}
	}
}
