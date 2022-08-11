package excecao.personalizadaA;

import excecao.Aluno;

public class TesteValidacao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Aluno aluno1 = new Aluno("", -7);
			Validar.aluno(aluno1);
		} catch (StringVaziaException e) {
			System.out.println(e.getMessage());
		}

		
		try {
			Aluno aluno2 = new Aluno(null, 23);
			Validar.aluno(aluno2);
		} catch (StringVaziaException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Aluno aluno3 = new Aluno("Gui", -5);
			Validar.aluno(aluno3);
		} catch (NumeroForaIntervaloException e) {
			System.out.println(e.getMessage());
		}
		try {
			Aluno aluno4 = new Aluno("Gui", 15);
			Validar.aluno(aluno4);
		} catch (NumeroForaIntervaloException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Aluno aluno5 = new Aluno("", -9);
			Validar.aluno(aluno5);
		} catch (StringVaziaException e) {
			System.out.println(e.getMessage());
		} catch(NumeroForaIntervaloException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Aluno aluno6 = new Aluno(null, 19);
			Validar.aluno(aluno6);
		} catch (StringVaziaException | NumeroForaIntervaloException e) {
			System.out.println(e.getMessage());
		} 
		
		
		System.out.println("FIM");
		
	}

}
