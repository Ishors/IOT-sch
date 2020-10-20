import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;

public class Platform {
	private Map<String, Thing> mapThings;
	private List<Service> arrServices;

	public Platform() {
		this.arrServices = new ArrayList<Service>();
		this.mapThings = new HashMap<String, Thing>();
	}

	public void addThing(Thing thing) {
		mapThings.put(thing.getMacAdress(), thing);
	}

	public void addService(Service service) {
		arrServices.add(service);
	}

	public void run(DataReceiver dataReceiver) {
		String datagram;

		while (dataReceiver.ready()) {
			// On lit le datagram contenu dans le dataReceiver (FileReader ou keyboardInput
			// ici)
			datagram = dataReceiver.readDatagram();
			// Si le datagram n'est pas null ou vide, alors :
			if (!datagram.equals("null") && datagram != null && !datagram.isEmpty()) {
				// On r�cup�re les 17 premiers caract�res du dtagram qui correspondent �
				// l'adresse mac
				String mac = datagram.substring(0, 17);
				// On r�cup�re ensuite le thing associ� � cette adresse mac
				Thing theThing = this.mapThings.get(mac);
				if (theThing == null) {
					// Si l'adresse mac ne correspond � aucunes de celles enregistr�es dans la
					// mapThing, alors on renvoie :
					System.out.println("Mac address unknown: " + mac);
				} else {
					// Sinon on remplit notre mapData et on envoie les donn�es dans les ervices
					// auxquels la thing a souscrit
					theThing.setFromDatagram(datagram.substring(18));
					theThing.update();
					theThing.resetData();
				}
			}
		}
	}

	public void close() {
		// On parcourt notre arrServices
		for (int i = 0; i < this.arrServices.size(); i++) {
			Service service = this.arrServices.get(i);
			// Puis on demande � chaque service de s'�teindre
			service.close();
		}
	}

	public void loadFromDatabase() {
		// Connexion � la BDD
		HashMap<String, Service> mapIds = new HashMap<String, Service>();
		try {
			System.out.println("Chargement de pilote JDBC<-->MySQL ...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("pilote charg�.");
			String utilisateurBDD = "root"; // Utilisateur de la BD
			String motdepasseBDD = ""; // Password de l'utilisateur de la BD
			String nomBDD = "platform_iot"; // Nom de la BD sur laquelle nous allons acceder
			String urlBDD = "jdbc:mysql://localhost/" + nomBDD;
			// String urlBDD = "jdbc:mysql://localhost:8889/"+nomBDD; // Pour MacOS
			try {
				Connection connexion = (Connection) DriverManager.getConnection(urlBDD, utilisateurBDD, motdepasseBDD);
				System.out.println("Connexion �tablie.");
				// Une fois connect�s on envoie une requ�te pour r�cup�rer les donn�es de la
				// table service
				String requete_service = new String("SELECT * FROM service;");
				Statement statement_service = connexion.createStatement();
				ResultSet rs_service = statement_service.executeQuery(requete_service);
				// On parcourt la table
				while (rs_service.next()) {
					// On recup�re les diff�rents attributs associ�s au service
					Service service;
					String type_service = rs_service.getString("type");
					String name_service = rs_service.getString("name");
					String id_service = rs_service.getString("id");
					// Si le service dans la BDD est une "smarthome" ou "quantifieldself", on les
					// cr�e, sinon on cr�e un "service" par d�faut
					if (type_service != null) {
						if (type_service.equals("smarthome")) {
							service = new SmartHome(name_service);
						} else if (type_service.equals("quantifiedself")) {
							service = new QuantifiedSelf(name_service);
						} else {
							service = new Service(name_service);
						}
					} else {
						service = new Service(name_service);
					}
					// On ajoute maintenant le service � la platforme
					this.addService(service);
					mapIds.put(id_service, service);
				}

				// Ici on r�cup�re les donn�es de la table thing
				String requete_thing = new String("SELECT * FROM thing;");
				Statement statement_thing = connexion.createStatement();
				ResultSet rs_thing = statement_thing.executeQuery(requete_thing);
				// On la parcourt
				while (rs_thing.next()) {
					Thing thing;
					String type_thing = rs_thing.getString("type");
					String mac_thing = rs_thing.getString("mac");
					String id_thing = rs_thing.getString("id_user");
					String parameter_thing = rs_thing.getString("parameter");
					// Si le parameter_thing indique un thingtempo, alors on r�cup�re ses
					// informations pour instancier un thingtempo
					if (parameter_thing != null && type_thing.equals("thingtempo")) {
						// Les deux lignes qui suivent permettent de r�cup�rer la valeur du param�tre
						// delay dans la table pour pouvoir la mettre en param�tre du constructeur
						// ThingTempo
						BigDecimal bd = new BigDecimal(parameter_thing);
						Long delay = bd.longValue();
						thing = new ThingTempo(mac_thing, id_thing, delay);
					} else {
						// Sinon on cr�e un "thing" par d�faut
						thing = new Thing(mac_thing, id_thing);
					}
					// On ajoute le thing � la plateforme
					this.addThing(thing);

					// Ici la requ�te cherche les ids des services auxquels le thing est abonn�
					String requete_service_souscrit = new String(
							"SELECT id_service FROM subscribe WHERE id_user IN (SELECT id_user FROM thing WHERE mac='"
									+ mac_thing + "');");
					Statement statement_service_souscrit = connexion.createStatement();
					ResultSet rs_service_souscrit = statement_service_souscrit.executeQuery(requete_service_souscrit);
					while (rs_service_souscrit.next()) {
						// Une fois r�cup�r�, on utilise la m�thode subscribe pour souscrire le thing
						// aux services enregistr�s
						String id_service_souscrit = rs_service_souscrit.getString("id_service");
						Service service_souscrit = mapIds.get(id_service_souscrit);
						thing.subscribe(service_souscrit);
					}
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
	}
}
