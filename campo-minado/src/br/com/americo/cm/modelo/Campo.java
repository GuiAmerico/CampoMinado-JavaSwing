package br.com.americo.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Campo {
	private final int linha;
	private final int coluna;
	
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream()
		.forEach(ob -> ob.eventoOcorreu(this, evento));
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = this.linha != vizinho.linha;
		boolean colunaDiferente = this.coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		boolean linhaIgual = this.linha == vizinho.linha;
		boolean colunaIgual = this.coluna == vizinho.coluna;
		boolean paralela = linhaDiferente && colunaIgual ||
				linhaIgual && colunaDiferente;;
				
		
		if(Math.abs(this.linha - vizinho.linha) + 
		   Math.abs(this.coluna - vizinho.coluna) == 2
		   && diagonal) {
			
			vizinhos.add(vizinho);
			return true;
		
		}else if(Math.abs(this.linha - vizinho.linha) + 
				 Math.abs(this.coluna - vizinho.coluna) == 1
				 && paralela) {
			
			vizinhos.add(vizinho);
			return true;
		
		}else {
		
			return false;
		}
			
	}
	
	
	public void alternarMarcacao() {
		if(!aberto) {
			
			marcado = !marcado;
			
			if(marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			}else {
				notificarObservadores(CampoEvento.DESMARCAR);
				
			}
			
		}
	}
	public boolean abrir(){
		if(this.marcado == false && this.aberto == false) {
			
			
			if(minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
				
			}
			setAberto(true);
			notificarObservadores(CampoEvento.ABRIR);
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
			
		}else {
		
		return false;
		}
		
	}
	
	public boolean vizinhancaSegura() {
		Predicate<Campo> minados = campo -> campo.minado == true;
		
		return vizinhos.stream().noneMatch(minados);
		
		
	}
	
	public boolean isMarcado() {
		
		return marcado;
	}
	
	void minar() {
		minado = true;	
		
	}
	public boolean isMinado() {
		return minado;
	
	}
	
	
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
		if(aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}

	public boolean isAberto() {
			return aberto;
		
	}

	public boolean isFechado() {
		return !aberto;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcacado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	public int minasNaVizinhaca() {
		return (int) vizinhos.stream()
				.filter(v -> v.minado)
				.count();

	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
		
	}
	
}
