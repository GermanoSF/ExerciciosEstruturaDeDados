#include<stdio.h>
#include<time.h>
#include<stdlib.h>

#define TAM 10
//tamanho do vetor 

void popular_exibir(int vetor[],int n){ //popula e exibe os valores do vetor, popula em ordem decrescente, exibe em ordem crescente
	
	if (n>0){
		
		vetor[n-1] = rand()%21;  //popula um indice do vetor com um numero aleatorio entre 0 e 20
		
		popular_exibir(vetor,n-1); //chama novamente a funcao, com n-1
		
		printf("%i\n",vetor[n-1]); //exibe um indice do vetor 
		
	}
	
}

int menor(int vetor[],int n,int valor){ //encontra o menor numero no vetor com logica recursiva
	
	if (n>0){
		
		if (valor>vetor[n-1]){ 
			
			return menor(vetor,n-1,vetor[n-1]);
			
		}
		else{
			
			return menor(vetor,n-1,valor);
			
		}
		
	}
	
	return valor;

}

int main(){
	
	srand(time(NULL));
	int vetor[TAM];
	
	popular_exibir(vetor,TAM);
	
	printf("\n%i",menor(vetor,TAM-1,vetor[TAM-1]));
	
	
}
