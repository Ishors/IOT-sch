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
		// du datagram en entrée
		String[] tabSplit = datagram.split(";");
		for (int i = 0; i < tabSplit.length; i++) {
			// On parcourt notre tabSplit et chaque élement de la liste est divisé en deux
			// autres éléments : la clé "key" et le datagram "dat" qui sont ajoutés à notre
			// Hashmap via la méthode putData
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
		// Cette méthode souscrit notre objet au service donné en entrée
		arrServices.add(service);
	}

	public String toString() {
		// Concat contient l'adresse mac et l'id de l'objet utilisé
		// pour appeler la méthode
		String concat = macAdress + ";" + userId;
		// Ici on parcourt notre Map
		Iterator<Map.Entry<String, String>> it = this.mapData.entrySet().iterator();
		// Et tant qu'il reste un élément dans la map
		while (it.hasNext()) {
			Map.Entry<String, String> couple = it.next();
			// On récupère la value(= datagram) de chaque couple (key + dat)
			String dat = (String) couple.getValue();
			// Et on l'ajoute à notre concatenation de l'adresse mac et de l'id
			concat = concat + ";" + dat;

		}
		return concat;
	}

	public void update() {
		// Cette méthode parcourt notre arrServices pour écrire les données de l'objet
		// dans chaque service auquel il a souscrit grâce à la méthode writeData
		for (int i = 0; i < this.arrServices.size(); i++) {
			Service service = this.arrServices.get(i);
			service.writeData(this);
		}
	}
}
