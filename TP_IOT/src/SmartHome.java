 class SmartHome extends Service {

	public SmartHome(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void writeData(Thing thing) {
		// Ici on complète le code de la méthode writeData de la classe Service
		// En rajoutant une condition à l'appel de la méthode
		// Cette condition est l'existence de la key "sta" dans le datagram associé au "thing"
		if (thing.existData("sta")) {
			// Si la condition est remplie, on appelle la méthode writeData
			super.writeData(thing);
		}
		
	}
}