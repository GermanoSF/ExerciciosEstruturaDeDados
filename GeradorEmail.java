/*
O que o programa faz? 

    Basicamente o programa vai simular a criacao de emails institucionais (que vao aparecer no proprio programa) atraves de nomes que estarao em um arquivo de texto, 
    mas para isso ocorrer, os seguintes passos devem ser seguidos:
    
        - O usuario deve digitar o caminho do arquivo, juntamente com a extensao (que deve ser .txt).
        - No arquivo deve conter apenas nomes listados, um embaixo do outro, nome e sobrenome.
        - O arquivo deve conter pelo menos um nome completo (com sobrenome).
        - Caso tenha mais de um nome totalmente igual, sera considerado que se trata da mesma pessoa (O valor no dicionario se repetira), 
        mesmo gerando um email diferente por cada vez que ela apareca na lista (o email e a chave do dicionario).
        - Caso haja nomes incompletos, sera exibida uma mensagem logo abaixo do caminho do arquivo, iformando que nao foi possivel gerar o email para cada nome incompleto.

*OBS: So utilizei IA na parte da sintaxe, com certeza o codigo nao esta bem otimizado, 
talvez tenham algumas redundancias, mas esse foi o primeiro exemplar que pensei,
talvez desenvolva um melhor futuramente. 

Atenciosamente, Germano Spall Figueira.
    
*/
package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class EmailGerador {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		List<String> linhas = new ArrayList<>(); // lista para armazenar as linhas (nomes) do arquivo
		List<String> vetores = new ArrayList<>(); //ira armazenar o primeiro e ultimo nome
		Map<String, String> emails = new HashMap<>(); //dicionario que armazena eamail como chave e nome completo como valor
		
		System.out.print("Digite o caminho do arquivo: ");
		String caminhoA = sc.nextLine(); // Caminho do arquivo
		
		try (BufferedReader br = new BufferedReader(new FileReader(caminhoA))) {
			
			String linha;
			String[] split1;
			
			while ((linha = br.readLine()) != null) { //cada linha do arquivo e passada para uma string
				
				//elimina todos espacos antes, depois e no meio da string linha, armazenando no array de strings split1 cada string entre os espacos
				split1 = linha.trim().split("\\s+");
				
				//se existem menos que 2 indices para split1, entra no if
				if (split1.length < 2) System.out.println("O email no nome de " + linha + " nao pode ser gerado (nome incompleto)");	
					
				//senao entra aqui
				else {
					
					linha = linha.toUpperCase(); 
					linhas.add(linha); // Adiciona a linha lida na lista
					
				}
				
			}
			
			Iterator<String> it;         
			Iterator<String> it2;
			
			it = linhas.iterator();
			
			String[] split2;
			
			while (it.hasNext()) {
				
				//elimina todos espacos antes, depois e no meio da string linha, armazenando no array de strings split2 cada string entre os espacos
				split2 = it.next().trim().split("\\s+"); 
				
				//se existem menos que 2 indices para split2, entra no if
				if (split2.length < 2) {
					
					it.remove(); //remove o da lista o nome incompleto 
					
				}
				
			}
			
			String[] vetor;
			String ite;
			
			int contadorRP; // Serve para contar se existe mais de um primeiro e ultimo nomes iguais
			
			it = linhas.iterator();
			
			while (it.hasNext()) {
				
				//nesse while, e adicionado na lista vetores, o primeiro e o segundo nome (com letra minusculas) separados por um espaco
				ite = (String) it.next();
				vetor = ite.trim().split("\\s+");  
				vetor[0] = vetor[0].toLowerCase();
				vetor[(vetor.length) - 1] = vetor[(vetor.length) - 1].toLowerCase();
				vetores.add(vetor[0] + " " + vetor[(vetor.length) - 1]);
				
			}
			
			it = vetores.iterator();
			
			String email;
			String[] email2;
			
			int posicaoE = 1;  //essa variavel serve para se houver nomes e ultimos sobrenomes repetidos, colocar um numero apos o ultimo sobrenome
			int posicaoLP = 0; // essa  variavel serve de indice dentro do iterator,
							   //para colocar como valor no dicionario, o nome completo que esta em "linhas"
			
			while (it.hasNext()) {
				
				String eQ = it.next();				
				it2 = vetores.iterator();
				contadorRP = 0;
				
				while (it2.hasNext()) { //nesse while e verificado quantos primeiros e ultimos nomes sao iguais (ou se tem so um)
					
					if (eQ.equals(it2.next())) {
						
						contadorRP++;
						
					}
					
				}
				if (contadorRP == 1) { //se entrar no if, e porque so existe 1 nome e ultimo sobrenome para esse caso	
					
					email2 = vetores.get(posicaoLP).split(" ");	//entao cria o email a partir no nome e ...
					email = email2[0] + "." + email2[1] + "@ufn.edu.br";
					emails.put(email, linhas.get(posicaoLP));
					
				}
				else {    //senao, cria um email a partir do nome, ultimo sobrenome e um numero (no caso o valor de posicaoE)
					
					email2 = vetores.get(posicaoLP).split(" ");
					email = email2[0] + "." + email2[1] + posicaoE + "@ufn.edu.br";
					emails.put(email, linhas.get(posicaoLP));
					posicaoE++;
					
				}
				
				posicaoLP++;    // acrescenta o indice que se encontra o iterator
				
			}
			
			System.out.println("\n");
			System.out.println("Emails criados e seus respectivos proprietarios:");
			for (Map.Entry<String,String> em : emails.entrySet()) System.out.println(em.getKey() + "  " + em.getValue()); // mostra todos emails e seus respectivos nomes base
			
		} 
		catch (IOException e) { // Se houver um erro, captura e imprime o rastreamento da excecao
			
			e.printStackTrace();
			
		}
		
		sc.close(); // fecha o fluxo de entrada associada ao Scanner
		
	}

}
