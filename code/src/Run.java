
public class Run {
	public static void main(String args[]) {
		System.out.println("Welcome on IoT central platform");
		
		/*Thing t1 = new Thing("f0:de:f1:39:7f:17", "1");
		t1.setFromDatagram("geo 43.433331 -1.58333;pul 128;bat 90.0");
		ThingTempo t2 = new ThingTempo("f0:de:f1:39:7f:18", "1", 60);
		
		Service s1 = new Service("mon_service");
		Service s2 = new Service("Service_2");
		Service s3 = new Service("Service_3");
		Service s4 = new Service("Service_4");
		Service s5 = new Service("Service_5");
		t1.subscribe(s1);
		t1.subscribe(s2);
		t1.subscribe(s3);
		t1.subscribe(s4);
		t1.subscribe(s5);
		t2.subscribe(s3);
		t2.subscribe(s4);
		
		Service sh = new SmartHome("myKWHome");
		Service qs = new QuantifiedSelf("RUNstats");
		
		p.addService(s1);
		p.addService(s2);
		p.addService(s3);
		p.addService(s4);
		p.addService(s5);
		p.addService(sh);
		p.addService(qs);
		p.addThing(t1);
		p.addThing(t2);*/
		
		Platform p = new Platform();
		p.loadFromDatabase();
		SocketServer s = new SocketServer(51291);
		s.open();
		p.run(s);
		s.close();
		
		/*FileReader f = new FileReader("simu.txt");
		f.open();
		p.run(f);
		f.close();*/
		
		/*KeyboardInput k = new KeyboardInput();
		k.open();
		p.run(k);
		k.close();*/
		
		
		System.out.println("bye.");
	}
}
