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
		// On récupère la data actuelle
		Date now = new Date();
		long time = now.getTime();
		/*Et on vérifie que le temps écoulé depuis la dernière update est supérieur au
		 delay imposé à la construction de l'objet ThingTempo*/
		/*Le delay étant demandé en secondes, on le passe en millisecondes par
		 soucis de concordance des unités*/
		if (time > lastUpdate + (delay * 1000)) {
			super.update();
			this.lastUpdate = time;
		}
	}

}
