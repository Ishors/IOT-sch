import java.util.Date;

public class ThingTempo extends Thing {
	private long delay;
	private long lastUpdate;

	public ThingTempo(String mac, String id, long delay) {
		// TODO Auto-generated constructor stub
		super(mac, id);
		this.delay = delay;
	}

	public void update() {
		// On r�cup�re la data actuelle
		Date now = new Date();
		long time = now.getTime();
		/*Et on v�rifie que le temps �coul� depuis la derni�re update est sup�rieur au
		 delay impos� � la construction de l'objet ThingTempo*/
		/*Le delay �tant demand� en secondes, on le passe en millisecondes par
		 soucis de concordance des unit�s*/
		if (time > lastUpdate + (delay * 1000)) {
			super.update();
			this.lastUpdate = time;
		}
	}

}
