package back;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import controladores.ControladorVistaChat;
import controladores.ControladorVistaConecta;
import controladores.ControladorVistaInicial;
import exception.CreaChatException;
import exception.IniciarException;
import exception.UserNotAvailableException;
import front.IVistaChat;

public class Cliente {

	private IVistaChat vistaChat = null;
	private Socket socket;
	private ServerSocket serverSocket;
	private SendMessage sendMessage;
	private ReceiveMessage receiveMessage = null;
	private ControladorVistaConecta contConecta = null;
	private ControladorVistaChat contChat = null;
	private ControladorVistaInicial contInicial = null;
	private Conexion conexion;

	private String nickname;
	private int puerto;
	private String iP;
	private String contrasena;
	private String error;
	private char comando;
	private int comand;
	private DataInputStream dis;
	private DataOutputStream dos;

	public Cliente() {
	}

	public Cliente(String nickname, int puerto, String iP) throws IOException {
		super();
		this.nickname = nickname;
		this.puerto = puerto;
		this.iP = iP;
		this.conexion = new Conexion();
		// esto va en Conexion
		conexion.setCliente(this);
		conexion.cambiaServer();
		// Conexion.getInstance().agregarSocket(iP, puerto);
		// Conexion.getInstance().agregarSocket(iP, puerto+1);

	} 

	public void conectarServeryMonitor() throws IOException {

//		Conexion.getInstance().getDos().writeUTF("1" + this.nickname);
		conexion.registrar(this.nickname);
		conexion.conectaMonitor();
		conexion.escuchaMonitor();
	}

	
	
	public void recibirMensajes() {

		this.receiveMessage = new ReceiveMessage(this.conexion);
		this.receiveMessage.recibirMensajes();
		//this.receiveMessage.start();
	}

	public void conectarReceptor(String nickNameReceptor)
			throws IOException, UserNotAvailableException, CreaChatException {

		this.sendMessage.enviaNickName(nickNameReceptor);
	}

	public SendMessage getMessageManager() {
		return this.sendMessage;
	}

	public void setVista(IVistaChat v) {
		this.vistaChat = v;
	}

//	public Socket getsocket() {
//		return this.socket;
//	}

	public ReceiveMessage getReceiveMessage() {
		return receiveMessage;
	}

	public void setContConecta(ControladorVistaConecta contConecta) {
		this.contConecta = contConecta;
		this.receiveMessage.setContConecta(contConecta);
	}

	public void setContChat(ControladorVistaChat cont) {
		this.contChat = contChat;
		this.receiveMessage.setContChat(cont);

	}

	public ControladorVistaInicial getContInicial() {
		return contInicial;
	}

	public void setContInicial(ControladorVistaInicial contInicial) {
		this.contInicial = contInicial;
		this.receiveMessage.setContInicial(contInicial);
	}

	public void setReceiveMessage(ReceiveMessage conectionHandler) {
		this.receiveMessage = conectionHandler;
	}

	public String getNickname() {
		return nickname;
	}

	public void creaConectionHandler() {
		this.sendMessage = new SendMessage(this.conexion);
		this.recibirMensajes();
	}

	public ControladorVistaConecta getContConecta() {
		return contConecta;
	}

	public ControladorVistaChat getContChat() {
		return contChat;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public String getiP() {
		return iP;
	}

	public void setiP(String iP) {
		this.iP = iP;
	}

//------------------------------------------------------------------------------------------------------
}
