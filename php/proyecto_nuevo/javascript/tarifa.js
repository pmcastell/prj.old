<script type="text/javascript">
//Pone los campos en azul al borrar los datos del formulario
	function campos_azules(){
		document.forms[0].minutos.style.background ='#A4E4F5';
		document.forms[0].coste.style.background ='#A4E4F5';
	}
	
//Validar que los campos obligatorios del formulario estan rellenos
	function valida_envia(){
		//Validar que los campos obligatorios del formulario estan rellenos
		if (document.forms[0].minutos.value.length==0){
			alert("Introduzca la Duración, por favor.")
			document.forms[0].minutos.style.background ='#0A416B';
			document.forms[0].minutos.style.color ='white';
			document.forms[0].minutos.focus()
			return 0;
		}
		else {
			document.forms[0].minutos.style.background ='#A4E4F5';
			document.forms[0].minutos.style.color ='#0F5B96';
		}
		
		if (document.forms[0].coste.value.length==0){
			alert("Introduzca el Coste, por favor.")
			document.forms[0].coste.style.background ='#0A416B';
			document.forms[0].coste.style.color ='white';
			document.forms[0].coste.focus()
			return 0;
		}
		else {
			document.forms[0].coste.style.background ='#A4E4F5';
			document.forms[0].coste.style.color ='#0F5B96';
		}
		document.forms[0].submit();
	}
</script>