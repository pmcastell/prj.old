#include <stdio.h>

int main3(int argc, char**argv) {
	int i;

	for(i=1;i<=10;i++) {
		printf("El cuadrado de %d es %d.\n",i,i*i);
		getchar();
	}
	i=1;
	while(i<=10) {
		printf("El cuadrado de %d es %d",i,i*i);
		if (i==5)
			printf("%d",i);
		i++;
	}

	printf("\nIntroduce día: ");
	scanf("%d",&i);
	switch(i) {
	case 1:
		printf("Lunes");
		break;
	case 2:
		printf("Martes");
		break;
	case 3: case 4: case 5: case 6: case 7:
		printf("Es después del martes");
		break;
	}
	return 0;
}
