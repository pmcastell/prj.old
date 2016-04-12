<script type="text/javascript">
	//Pone los campos en azul al borrar los datos del formulario
	function campos_azules(){
		document.forms[0].nombre.style.background ='#A4E4F5';
		document.forms[0].apellido1.style.background ='#A4E4F5';
		document.forms[0].clave.style.background ='#A4E4F5';
		document.forms[0].saldo.style.background ='#A4E4F5';
	}
	
	//Validar que los campos obligatorios del formulario estan rellenos
	function valida_envia(){
	
		//validar numero de caracteres del NIF
		if (document.forms[0].numero.value.length != 8) {
			alert("Compruebe que el campo NIF tiene 8 numeros");
			document.forms[0].numero.focus();
			return false;
		}
		//validar numero de caracteres del TELEF
		if (document.forms[0].telef.value.length != 9) {
			alert("Comprueba que el campo TLF tiene 9 caracteres");
			document.forms[0].telef.focus();
			return false;
		}
	
		//Validar que los campos obligatorios del formulario estan rellenos
		if (document.forms[0].nombre.value.length==0){
			alert("Introduzca su Nombre, por favor.")
			document.forms[0].nombre.style.background ='#0A416B';
			document.forms[0].nombre.style.color ='white';
			document.forms[0].nombre.focus()
			return 0;
		}
		else {
			document.forms[0].nombre.style.background ='#A4E4F5';
			document.forms[0].nombre.style.color ='#0F5B96';
		}
		
		if (document.forms[0].apellido1.value.length==0){
			alert("Introduzca su Apellido 1, por favor.")
			document.forms[0].apellido1.style.background ='#0A416B';
			document.forms[0].apellido1.style.color ='white';
			document.forms[0].apellido1.focus()
			return 0;
		}
		else {
			document.forms[0].apellido1.style.background ='#A4E4F5';
			document.forms[0].apellido1.style.color ='#0F5B96';
		}
		
		if (document.forms[0].clave.value.length==0){
			alert("Introduzca su Clave, por favor.")
			document.forms[0].clave.style.background ='#0A416B';
			document.forms[0].clave.style.color ='white';
			document.forms[0].clave.focus()
			return 0;
		}
		else {
			document.forms[0].clave.style.background ='#A4E4F5';
			document.forms[0].clave.style.color ='#0F5B96';
		}
		
		if (document.forms[0].saldo.value.length==0){
			alert("Introduzca su Saldo, por favor.")
			document.forms[0].saldo.style.background ='#0A416B';
			document.forms[0].saldo.style.color ='white';
			document.forms[0].saldo.focus()
			return 0;
		}
		else {
			document.forms[0].saldo.style.background ='#A4E4F5';
			document.forms[0].saldo.style.color ='#0F5B96';
			
		}
		document.forms[0].submit();
	}
</script>