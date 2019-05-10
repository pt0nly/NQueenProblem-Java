

public class QueenSolver {

	// Método principal (main)
	public static void main(String[] args) {
		// Estrutura 'Bag' do tipo String, que irá conter as soluções encontradas
		Bag<String> bagQueenSol = new Bag<String>();
		
		/**
		 * ALTERAR ESTE VALOR PARA TESTAR DIFERENTES DIMENSOES
		 */
		// 'n': dimensão do tabuleiro a ser testado
		int n = 4;

		System.out.print("Rainha" + n + "x" + n + ":\n");

		/*
		 * Executa o método 'solve' com parametro 'n'
		 * que irá retornar um Bag com as soluções encontradas para 'n'
		 **/
		bagQueenSol = solve(n);

		System.out.println("Soluções para Rainha " + n + "x" + n + ": ");

		/*
		 * Faz o display das soluções encontradas
		 * */
		for (String nb: bagQueenSol) {
			System.out.println(nb);
		}

		System.out.println("\nThe End!");
	}

	/*
	 * Método 'isSafe' com entrada da Stack em uso, do tipo 'Queen',
	 * e qual a linha a ser testada
	 * 
	 * retorna 'true' (posição segura) ou 'false' (posição sujeita a ataque)
	 * */
	public static boolean isSafe(Stack<Queen> st, long row) {
		// Se a stack estiver vazia, a posição é considerada segura
		if (st.isEmpty())
			return true;

		// Obtém a estrutura do último elemento inserido na Stack, sem o remover
		Queen tmp = st.top();

		/*
		 * Aqui utilizam-se máscaras de bits para confirmar posições seguras e de ataque.
		 * 
		 * getRowMask(): obtém a máscara cumulativa das posições de ataque em linha.
		 * getDiagonalUpMask(): obtém a máscara cumulativa das posições de ataque na
		 * 		diagonal para cima.
		 * getDiagonalDownMask(): obtém a máscara cumulativa das posições de ataque na
		 * 		diagonal para baixo.
		 * 
		 * '|': é operador bitwise OR
		 * 
		 * testRow: irá conter todas as posições de ataque e as seguras para a coluna actual.
		 * 
		 * thisRow: é a máscara da linha actual a ser testada
		 * */
		long testRow = tmp.getRowMask() | tmp.getDiagonalUpMask() | tmp.getDiagonalDownMask(),
			thisRow = 1 << (row - 1);

		/*
		 * '~': é operador bitwise NOT
		 * '&': é operador bitwise AND
		 * 
		 * ~testRow: é o testRow com os bits invertidos
		 * 
		 * (~testRow & thisRow): resulta nos bits comuns entre ~testRow e thisRow,
		 * 		e caso o resulta seja idêntico a 'thisRow', a posição é segura,
		 * 		caso contrário é de ataque.
		 * */
		if ((~testRow & thisRow) == thisRow)
			return true;

		return false;
	}

	/*
	 * Método 'backTrack'
	 * 
	 * Efectua um backtracking até que ocorra uma de duas condições:
	 * 	1)-A coluna anterior estar completamente testada
	 * 	2)-Encontrada uma posição que possa ser testada
	 * 
	 * Retorna uma class com valores actualizados para:
	 * 'done', 'st', 'colDone', 'col' e 'row'
	 * */
	private static BackTracking backTrack(long colDone, long col, long row, Stack<Queen> st, int n) {
		Queen tmp;
		
		while (colDone < (col - 1)) {
			/*
			 * Retrocede uma coluna.
			 * tmp: Obtém último elemento da Stack e remove-o da mesma.
			 * row: Obtém linha de 'tmp' e calcula a seguinte.
			 * */
			col--;
			tmp = st.pop();
			row = tmp.getRow() + 1;
			
			/*
			 * Testa se a linha é inferior ou igual ao máximo (N),
			 * e em caso afirmativo termina o backtracking e continua a testar posições.
			 * */ 
			if (row <= n)
				break;
		}
		
		/*
		 * Verifica se ao terminar o backtracking, terminou também
		 * o algoritmo (no caso da linha actual ser superior a 'n'.
		 * 
		 * E em caso afirmativo, envia o sinal de término do algoritmo.
		 * */
		
		return new BackTracking(colDone, col, row, (row > n ? true : false), st);
	}
	
	/*
	 * Método 'calcMask'
	 * 
	 * Calcula:
	 * rowMask: máscara de bits Horizontal
	 * diagUpMask: máscara de bits Diagonal-Cima
	 * diagDownMask: máscara de bits Diagonal-Baixo
	 * 
	 * retorna tipo 'Queen'
	 * 
	 * */
	private static Queen calcMask(long col, long row, Stack<Queen> st) {
		long rowMask = 1 << (row - 1),
			diagUpMask = rowMask << 1,
			diagDownMask = rowMask >>> 1;
		Queen tmp;
		
		if (col > 1) {
			/*
			 * Quando a coluna actual não é a primeira:
			 * 
			 * tmp: copia o último elemento da Stack, sem o remover.
			 * 
			 * diagUpMask: move os bits de 'tmp' da diagonal-cima,
			 * 		uma posição para a esquerda (para simular a diagonal cima).
			 * 		E junta-se aos bits já existentes (cumulativo) das colunas anteriores.
			 *  
			 * diagDownMask: move os bits de 'tmp' da diagonal-baixo,
			 * 		uma posição para a direita SEM SINAL (daí o >>> em vez de >>) (para simular a diagonal baixo).
			 * 		E junta-se aos bits já existentes (cumulativo) das colunas anteriores.
			 * 
			 *  rowMask: junta os bits Horizontal de 'tmp' aos já existentes (cumulativo).
			 * */
			tmp = st.top();
			diagUpMask |= tmp.getDiagonalUpMask() << 1;
			diagDownMask |= tmp.getDiagonalDownMask() >>> 1;
			rowMask |= tmp.getRowMask();
		}
		
		return new Queen(row, rowMask, diagUpMask, diagDownMask);
	}
	
	/*
	 * Método 'solve' com entrada 'n'.
	 * 
	 * Este método contém o algoritmo de pesquisa de todas as soluções possíveis para
	 * posicionar N rainhas num tabuleiro de xadrez NxN, sem que nenhuma se consiga atacar.
	 * 
	 * Retorna um Bag de Strings, com todas as soluções encontradas.
	 * */
	public static Bag<String> solve(int n) {
		/*
		 * Estrutura 'Bag' do tipo String, que irá acumular todas as soluções encontradas e
		 * será a variável de retorno do método
		 * */
		Bag<String> bagQueens = new Bag<String>();
		/*
		 * 's': É a variável usada como Stack do tipo QueenStruct.
		 * 'QueenStruct': é uma Class que contém a linha testada, e 3 máscaras de bit para
		 * 		controlo de posições de ataque/segurança.
		 * */
		Stack<Queen> s = new Stack<Queen>();

		/*
		 * Se N for maior ou igual a 4, então executa o algoritmo de pesquisa,
		 * caso contrário retorna para a consola uma mensagem a dizer que o mínimo é N=4.
		 * 
		 * 		(Na verdade N=1 também é possível, mas como é apenas uma casa,
		 * 		não foi aqui considerado para o caso).
		 * */
		if (n >= 4) {
			/*
			 * Este algoritmo irá percorrer da coluna da esquerda, para a direita
			 * 
			 * 	done: variável de controlo de fim de ciclo do algoritmo.
			 * 
			 *  colDone: irá guardar o número da última coluna da esquerda completamente testada,
			 *  	de modo a evitar que faça mais backtracking desnecessáriamente.
			 *  
			 *  col: variável de controlo da coluna actual.
			 *  row: variável de controlo da linha actual.
			 *  count: contador de total de soluções encontradas.
			 *  
			 *  rowMask: máscara de bits Horizontal, para controlo de posições atacantes
			 *  diagUpMask: máscara de bits diagonal-cima, para controlo de posições atacantes
			 *  diagDownMask: máscara de bits diagonal-baixo, para controlo de posições atacantes
			 *  
			 *  tmp: variável do tipo 'Queen' para adicionar à Stack quando uma
			 *  	posição segura for encontrada.
			 *  
			 *   initTime: variável para cálculo do tempo utilizado pelo algoritmo para os cálculos.
			 * */
			
			
			boolean done = false;
			long colDone = 0,
				col = 1,
				row = 1;
			Queen tmp;
			BackTracking bkt;
			long initTime = System.nanoTime();
			
			// Cria nova estrutura, com dados actuais
			tmp = calcMask(col, row, s);
			
			while (!done) {
				// Verifica se esta linha está numa posição segura ou de ataque
				if (isSafe(s, row)) {
					// A posição é segura.
					
					// Cria nova estrutura, com dados actuais, e guarda na Stack
					tmp = calcMask(col, row, s);
					s.push(tmp);

					if (col < n) {
						/*
						 * Coluna actual é menor que N.
						 * 
						 * Avançamos uma coluna, e posicionamos na linha 1 (primeira).
						 * */
						col++;
						row = 1;
					} else {
						// Solução encontrada. (E coluna actual igual a N).
						
						// Adicionamos a solução ao saco (Bag)
						bagQueens.add( s.toString() );
											
						/*
						 * Continuamos a testar até termos todo o tabuleiro testado.
						 * 
						 * Algumas posições não são necessárias testar, pois certas condições garantem-nos isso,
						 * 		logo, conseguimos eliminar ciclos.
						 * 
						 * tmp: Obtém o último elemento da Stack, e remove-o da mesma.
						 * 
						 * row: Obtém a linha de tmp e avança uma linha (esta já tinha sido testada).
						 * */
						tmp = s.pop();
						row = tmp.getRow() + 1;

						if (row > n) {
							// Aqui a linha actual (row) é superior a N.

							/*
							 * Antes de efectuar o backtracking, verifica se a coluna anterior
							 * 	já foi completamente testada, e em caso afirmativo, termina o algoritmo.
							 * */
							if (colDone == (col - 1))
								done = true;
							else {
								// Coluna anterior ainda não foi testada na totalidade.

								// Efectua um backtracking.
								bkt = backTrack(colDone, col, row, s, n);
								
								// Actualiza variáveis
								s = bkt.getStack();
								col = bkt.getCol();
								row = bkt.getRow();
								done = bkt.getDone();
							}
						}
					}
					
					// Verifica se esta coluna ficou completamente testada, e em caso afirmativo, actualiza a flag 'colDone'.
					if ((row == n) && (colDone == (col - 1)))
						colDone++;
				} else {
					// Esta posição foi considerada insegura (sujeita a ataque).
					
					if (row < n) {
						// A linha mantém-se abaixo de 'n', então avançamos uma linha.
						row++;
					} else {
						// Chegámos à última linha possível desta coluna.

						/* 
						 * Vamos verificar se a coluna anterior está disponível e efectuar o backtracking,
						 * ou se terminamos o algoritmo
						 * */
						if (colDone < (col-1)) {
							// Coluna anterior ainda não foi testada na totalidade.

							// Efectua um backtracking.
							bkt = backTrack(colDone, col, row, s, n);
							
							// Actualiza variáveis
							s = bkt.getStack();
							col = bkt.getCol();
							row = bkt.getRow();
							done = bkt.getDone();
						} else
							// O algoritmo esgotou todas as hipóteses, marcamos para finalizar o algoritmo.
							done = true;
					}
				}

				// Verificamos se o algoritmo esgotou todas as hipóteses, e em caso afirmativo, marcamos para finalizar o algoritmo.
				if (colDone >= n)
					done = true;
			}
			

			// endTime: usado para cálculo de tempo decorrido pelo algoritmo
			long endTime = System.nanoTime();

			/*
			 * Faz o output de mensagem para a consola, com número de soluções encontradas
			 * e tempo decorrido em segundos.
			 * */
			System.out.print("Soluções=" + bagQueens.size() + " ::\t=>\tTempo=" + ((double)(endTime - initTime) / 1000000000) + "s\n\n\n");

		} else
			System.out.print("\nMinimum is 4!!\n\n");

		return bagQueens;
	}

}
