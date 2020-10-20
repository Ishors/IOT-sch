interface DataReceiver {
	void open();
	String readDatagram();
	boolean ready();
	void close();
}
