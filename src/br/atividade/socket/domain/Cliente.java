package br.atividade.socket.domain;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Cliente {

	private static JFileChooser escolher;
	private static BufferedReader arqBuffer;
	private static ArrayList<String> linhasArray = new ArrayList<>();
	private static String linha;
	private static int resultado;
	private Socket cliente;

	public void executar() throws FileNotFoundException, IOException {
		criarConexao();
		escolherArquivo();
		cliente.close();
	}

	private void criarConexao() throws IOException {
		cliente = new Socket("127.0.0.1", 12345);
		JOptionPane.showMessageDialog(null, "O cliente se conectou ao servidor!");
	}

	private void escolherArquivo() throws IOException {
		escolher = new JFileChooser("C:/Users/User/Desktop");
		resultado = escolher.showOpenDialog(null);

		// Caso o arquivo seja escolhido corretamente, executa \/
		if (resultado == JFileChooser.APPROVE_OPTION) {

			// Pega o arquivo selecionado e adiciona linha por linha em uma
			// variável do tipo String .
			arqBuffer = new BufferedReader(new FileReader(escolher.getSelectedFile()));
			linha = arqBuffer.readLine() + "\n";
			linhasArray.add(linha);

			while (linha != null) {
				linha = arqBuffer.readLine();
				linhasArray.add(linha);
			}
			arqBuffer.close();

			// Exclue o valor nulo na última posição do array
			linhasArray.remove(linhasArray.size() - 1);
			linha = linhasArray.get(0);

			for (int i = 1; i < linhasArray.size(); i++) {
				linha += linha = linhasArray.get(i) + "\n";
			}

			// Manda o conteúdo do arquivo para o servidor.
			DataOutputStream saidaCliente = new DataOutputStream(cliente.getOutputStream());
			saidaCliente.writeUTF(linha);
			saidaCliente.flush();
			saidaCliente.close();
		}
	}

}