﻿<script type="text/javascript">
//Pone los campos en azul al borrar los datos del formulario
	function campos_azules(){
		document.forms[0].nombre_actor_director.style.background ='#A4E4F5';
	}
	
//Validar que los campos obligatorios del formulario estan rellenos
	function valida_envia(){
		//Validar que los campos obligatorios del formulario estan rellenos
		if (document.forms[0].nombre_actor_director.value.length==0){
			alert("Introduzca el Actor/Director, por favor.")
			document.forms[0].nombre_actor_director.style.background ='#0A416B';
			document.forms[0].nombre_actor_director.style.color ='white';
			document.forms[0].nombre_actor_director.focus()
			return 0;
		}
		else {
			document.forms[0].nombre_actor_director.style.background ='#A4E4F5';
			document.forms[0].nombre_actor_director.style.color ='#0F5B96';
		}
		document.forms[0].submit();
	}
</script>