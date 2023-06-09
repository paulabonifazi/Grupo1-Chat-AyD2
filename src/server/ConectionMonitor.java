package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConectionMonitor extends Thread {

	private Socket socketMonitor;
	private String comando;
	private Sincronizacion sincronizacion;
	private HeartBeatServer heartBeat;
	
	public ConectionMonitor(Socket socketMonitor,Sincronizacion sincronizacion) {
		this.socketMonitor = socketMonitor;
		this.sincronizacion = sincronizacion;
	}

	public void run() {

		DataInputStream dis = null;
		try {
			dis = new DataInputStream(socketMonitor.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (Server.isTerminar() == false) {
			try {
				comando = dis.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (comando.equals("PRINCIPAL")) {
				System.out.println("Server principal recibido");
				this.iniciaHeartBeat();
				Server.setPrincipal(true);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.sincronizacion.getSinc().seteaClientes();
			} else if (comando.equals("NUEVO_PUERTO")) {
				try {
					System.out.println("nuevo puerto recibido");
					this.sincronizacion.conectarConPrincipal();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void iniciaHeartBeat() {
		this.heartBeat = new HeartBeatServer(this.socketMonitor);
		this.heartBeat.start();
	}
	
	
}
