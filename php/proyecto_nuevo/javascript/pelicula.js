<script type="text/javascript">
	//Pone los campos en azul al borrar los datos del formulario
	function campos_azules(){
		document.forms[0].titulo.style.background ='#A4E4F5';
		document.forms[0].duracion.style.background ='#A4E4F5';
		document.forms[0].director.style.background ='#A4E4F5';
		document.forms[0].actor.style.background ='#A4E4F5';
		document.forms[0].sinopsis.style.background ='#A4E4F5';
		document.forms[0].genero.style.background ='#A4E4F5';
	}
	
	//Validar que los campos obligatorios del formulario estan rellenos
	function valida_envia(){
	
		//Validar que los campos obligatorios del formulario estan rellenos
		if (document.forms[0].titulo.value.length==0){
			alert("Introduzca el Titulo, por favor.")
			document.forms[0].titulo.style.background ='#0A416B';
			document.forms[0].titulo.style.color ='white';
			document.forms[0].titulo.focus()
			return 0;
		}
		else {
			document.forms[0].titulo.style.background ='#A4E4F5';
			document.forms[0].titulo.style.color ='#0F5B96';
		}
		
		if (document.forms[0].duracion.value.length==0){
			alert("Introduzca la Duración, por favor.")
			document.forms[0].duracion.style.background ='#0A416B';
			document.forms[0].duracion.style.color ='white';
			document.forms[0].duracion.focus()
			return 0;
		}
		else {
			document.forms[0].duracion.style.background ='#A4E4F5';
			document.forms[0].duracion.style.color ='#0F5B96';
		}
		
		if (document.forms[0].sinopsis.value.length==0){
			alert("Introduzca la Sinopsis, por favor.")
			document.forms[0].sinopsis.style.background ='#0A416B';
			document.forms[0].sinopsis.style.color ='white';
			document.forms[0].sinopsis.focus()
			return 0;
		}
		else {
			document.forms[0].sinopsis.style.background ='#A4E4F5';
			document.forms[0].sinopsis.style.color ='#0F5B96';
		}
		document.forms[0].submit();
	}
</script>