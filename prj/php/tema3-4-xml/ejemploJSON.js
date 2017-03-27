/**
 * 
 */
function prueba() {
	var a,cadena,a2,a3,p1,p2,p3,i,id,pueblo;


	a={ "poblaciones": [
	                        	{"poblacion": { "id": "0", "text": "Alcobendas"}},
	                        	{"poblacion": { "id": "1", "text": "Miraflores de la Sierra"}},
	                        	{"poblacion": { "id": "2", "text": "San Fernando de Henares"}}
	                        ]
		   };
	cadena='{ "poblaciones": [{"poblacion": { "id": "0", "text": "Alcobendas"}},{"poblacion": { "id": "1", "text": "Miraflores de la Sierra"}},{"poblacion": { "id": "2", "text": "San Fernando de Henares"}}]}';
	a2=eval("("+cadena+")");
	a3=JSON.parse(cadena);
	
	
	b=a.poblaciones;
	p1=a.poblaciones[0];
	p2=a.poblaciones[1];
	p3=a.poblaciones[2];
	for(i=0;i<a.poblaciones.length;i++) {
		id=a.poblaciones[i].poblacion.id;
		pueblo=a.poblaciones[i].poblacion.text;
	}
	
	
}

prueba();