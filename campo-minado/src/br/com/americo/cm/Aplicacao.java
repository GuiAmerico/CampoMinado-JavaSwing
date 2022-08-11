package br.com.americo.cm;

import br.com.americo.cm.modelo.Tabuleiro;
import br.com.americo.cm.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
		System.out.println(tabuleiro);
	}

}
