package br.com.americo.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.americo.cm.excecao.ExplosaoException;
import br.com.americo.cm.excecao.SairException;
import br.com.americo.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				cicloDoJogo();
				
				System.out.print("Outra Partida? [ S / n ] ");
				String resposta = entrada.next();
				if(resposta.equalsIgnoreCase("n")) {
					continuar = false;
				}else {
					tabuleiro.reiniciar();
				}
			}
		}catch (SairException e) {
			System.out.println("Tchau!!!");
		}finally {
			entrada.close();
		}
	}

	private void cicloDoJogo() {
		try {
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				
				String digitado = 
						capturarValorDigitado("Digite (linha, coluna) ");
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
				.map(s -> Integer.parseInt(s.trim())).iterator();
				
				
				digitado = capturarValorDigitado("1 - Abrir / 2 - (Des)Marcar");
				if(digitado.equals("1")) {
					tabuleiro.abrir(xy.next(), xy.next());
				}else if("2".equals(digitado)) {
					tabuleiro.marcar(xy.next(), xy.next());
				}
			
			}
				
				
			System.out.println("Você Ganhou!!!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você Perdeu!!!");
		}
		
	}
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		if(digitado.equalsIgnoreCase("sair")) {
			 throw new SairException();
			 
		}
		return digitado;
	}
}
