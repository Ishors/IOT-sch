interface DataSender {
    public void open () ;
    public void writeDatagram (String datagram) ;
    public boolean ready () ;
    public void close () ;
}
