 class SmartHome extends Service {

	public SmartHome(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void writeData(Thing thing) {
		// Ici on compl�te le code de la m�thode writeData de la classe Service
		// En rajoutant une condition � l'appel de la m�thode
		// Cette condition est l'existence de la key "sta" dans le datagram associ� au "thing"
		if (thing.existData("sta")) {
			// Si la condition est remplie, on appelle la m�thode writeData
			super.writeData(thing);
		}
		
	}
}