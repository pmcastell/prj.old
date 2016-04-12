function letraNif(dni) {
	return "TRWAGMYFPDXBNJZSQVHLCKE"[dni % 23];
}

document.write(letraNif(25126190));