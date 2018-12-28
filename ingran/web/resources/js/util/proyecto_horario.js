function calcular() {
    inicio = document.getElementById("hora_entrada").value;
    fin = document.getElementById("hora_salida").value;

    inicioMinutos = parseInt(inicio.substr(3, 2));
    inicioHoras = parseInt(inicio.substr(0, 2));

    finMinutos = parseInt(fin.substr(3, 2));
    finHoras = parseInt(fin.substr(0, 2));

    recesoMinutos = parseInt(document.getElementById("tiempo_receso_minutos").value);
    recesoHoras = parseInt(document.getElementById("tiempo_receso_horas").value);

    transcurridoMinutos = finMinutos - inicioMinutos - recesoMinutos;
    transcurridoHoras = finHoras - inicioHoras - recesoHoras;

    if (transcurridoMinutos < 0) {
        transcurridoHoras--;
        transcurridoMinutos = 60 + transcurridoMinutos;
    }

    minutos = transcurridoMinutos.toString();
    horas = transcurridoHoras.toString();

    if (minutos.length < 2) {
        minutos = "0" + minutos;
    }

    if (horas.length < 2) {
        horas = "0" + horas;
    }

    document.getElementById("horas_efectivas").value = horas + ":" + minutos;
}