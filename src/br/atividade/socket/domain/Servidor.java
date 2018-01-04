package br.atividade.socket.domain;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Servidor {
	private Cliente cl = new Cliente();
	private String recebido = "";
	private ServerSocket servidor;
	private Socket cliente;
	private DataInputStream dadosEntrada;

	public void executar() throws UnknownHostException, IOException {
		criarConexao();
		recebeDados();
		criarArquivo();
		dadosEntrada.close();
		servidor.close();
	}

	private void criarConexao() throws IOException {
		servidor = new ServerSocket(12345);
		JOptionPane.showMessageDialog(null, "Servidor: Porta 12345 aberta!");
	cl.executar();
		cliente = servidor.accept();
		JOptionPane.showMessageDialog(null, "Servidor: Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
	}

	// recebe dados do cliente
	private void recebeDados() throws IOException {
		dadosEntrada = new DataInputStream(cliente.getInputStream());

		while (true) {
			if (dadosEntrada.available() > 0) {
				recebido = dadosEntrada.readUTF() + "\n";
				break;
			}
		}
	}

	// Cria o arquivo recebido.txt
	private void criarArquivo() throws IOException {
		if (recebido != null)

		{
			File arquivo = new File("C://Users//User//Desktop//recebido.txt");

			// Caso não exista um arquivo.
			if (!arquivo.exists()) {
				arquivo.createNewFile();
				FileWriter fw = new FileWriter(arquivo.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(recebido);
				bw.close();
				JOptionPane.showMessageDialog(null, "Arquivo criado com sucesso.");

				// Caso já exista um arquivo.
			} else {
				int resposta = JOptionPane.showConfirmDialog(null, "Já existe arquivo com este nome. Quer alterá-lo?");
				if (resposta == JOptionPane.YES_OPTION) {
					FileWriter fw = new FileWriter(arquivo.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write("\n" + recebido);
					bw.close();
					JOptionPane.showMessageDialog(null, "Arquivo alterado com sucesso.");
				}else {
					JOptionPane.showMessageDialog(null, "Ação cancelada.");
				}
			}
		}
	}
}