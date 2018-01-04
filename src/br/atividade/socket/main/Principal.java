package br.atividade.socket.main;

import java.io.IOException;
import java.net.UnknownHostException;

import br.atividade.socket.domain.Servidor;

public class Principal {
	
	private static Servidor serv = new Servidor();
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		serv.executar();
	}
}
