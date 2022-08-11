package br.com.americo.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador{
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();
	

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarOsVizinhos();
		sortearAsMinas();
	}
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	private void notificarObservadores(boolean resultado) {
		observadores.stream()
		.forEach(ob -> ob.accept(new ResultadoEvento(resultado)));
	}
	
	public void abrir(int linha, int coluna) {

		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.abrir());;
	
	}
		public void marcar(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());;
	}


	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.registrarObservador(this);
				campos.add(campo);
				
			}
			
		}
		
	}
	private void associarOsVizinhos() {
		for(Campo c1 : campos) {
			for(Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	private void sortearAsMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = campo -> campo.isMinado();
		do {
		int aleatorio = (int) (Math.random() * campos.size());
		campos.get(aleatorio).minar();
		minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcacado());
		
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearAsMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("   ");
		for (int iColuna = 0; iColuna < colunas; iColuna++) {
			sb.append(" ");
			sb.append(iColuna);
			sb.append(" ");
		}
		sb.append("\n");
		
		int i = 0;
		for (int iLinha = 0; iLinha < linhas; iLinha++) {
			sb.append(" ");
			sb.append(iLinha);
			sb.append(" ");
			for (int coluna = 0; coluna < colunas; coluna++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
			
		}
		return sb.toString();
	}

	@Override
	public void eventoOcorreu(Campo c, CampoEvento evento) {
		if(evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		}else if(objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	private void mostrarMinas() {
		campos.stream()
		.filter(c -> c.isMinado())
		.filter(c -> !c.isMarcado())
		.forEach(c -> c.setAberto(true));
		
	}
	public int getLinhas() {
		return linhas;
	}
	public int getColunas() {
		return colunas;
	}

	
}
