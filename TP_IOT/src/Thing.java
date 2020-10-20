import java.util.*;

public class Thing {
	private String userId;
	private String macAdress;
	private Map<String, String> mapData;
	private List<Service> arrServices;

	public Thing(String mac, String id) {
		this.arrServices = new ArrayList<Service>();
		this.mapData = new HashMap<String, String>();
		this.macAdress = mac;
		this.userId = id;
	}

	public String getMacAdress() {
		return macAdress;
	}

	public String getUserId() {
		return userId;
	}

	public void putData(String key, String dat) {
		mapData.put(key, dat);
	}

	public String getData(String Key) {
		return (mapData.get(Key));
	}

	public void setFromDatagram(String datagram) {
		// Chaque indice de notre tabSplit va contenir ce qu'il y a entre deux ";"
		// du datagram en entr�e
		String[] tabSplit = datagram.split(";");
		for (int i = 0; i < tabSplit.length; i++) {
			// On parcourt notre tabSplit et chaque �lement de la liste est divis� en deux
			// autres �l�ments : la cl� "key" et le datagram "dat" qui sont ajout�s � notre
			// Hashmap via la m�thode putData
			String key = tabSplit[i].substring(0, 3);
			String dat = tabSplit[i].substring(4);
			this.putData(key, dat);
		}
	}

	public boolean existData(String key) {
		return mapData.containsKey(key);
	}

	public void resetData() {
		mapData.clear();
	}

	public void subscribe(Service service) {
		// Cette m�thode souscrit notre objet au service donn� en entr�e
		arrServices.add(service);
	}

	public String toString() {
		// Concat contient l'adresse mac et l'id de l'objet utilis�
		// pour appeler la m�thode
		String concat = macAdress + ";" + userId;
		// Ici on parcourt notre Map
		Iterator<Map.Entry<String, String>> it = this.mapData.entrySet().iterator();
		// Et tant qu'il reste un �l�ment dans la map
		while (it.hasNext()) {
			Map.Entry<String, String> couple = it.next();
			// On r�cup�re la value(= datagram) de chaque couple (key + dat)
			String dat = (String) couple.getValue();
			// Et on l'ajoute � notre concatenation de l'adresse mac et de l'id
			concat = concat + ";" + dat;

		}
		return concat;
	}

	public void update() {
		// Cette m�thode parcourt notre arrServices pour �crire les donn�es de l'objet
		// dans chaque service auquel il a souscrit gr�ce � la m�thode writeData
		for (int i = 0; i < this.arrServices.size(); i++) {
			Service service = this.arrServices.get(i);
			service.writeData(this);
		}
	}
}
