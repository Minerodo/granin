function calc(x) {
    inicio_object = "planilla_hora_detalle" + x + ".hora_inicio";
    final_object = "planilla_hora_detalle" + x + ".hora_fin";

    inicio = document.getElementById(inicio_object).value;
    fin = document.getElementById(final_object).value;

    inicioMinutos = parseInt(inicio.substr(3, 2));
    inicioHoras = parseInt(inicio.substr(0, 2));

    finMinutos = parseInt(fin.substr(3, 2));
    finHoras = parseInt(fin.substr(0, 2));

    transcurridoMinutos = finMinutos - inicioMinutos;
    transcurridoHoras = finHoras - inicioHoras;

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

    total_object = "planilla_hora_detalle" + x + ".cantidad_hora";

    document.getElementById(total_object).value = horas + ":" + minutos;
    horasTotales();
}

function horasTotales() {
    var horas = 0;
    var minutos = 0;
    var lista_completa = document.getElementById("lista").value;
    while (lista_completa.indexOf("#") !== -1) {
        lista_completa = lista_completa.replace("#", "");
    }
    var lista = lista_completa.split(",");
    for (var i = 0; i < lista.length; i++) {
        var hora = document.getElementById("planilla_hora_detalle" + lista[i] + ".cantidad_hora").value;
        var hora_horas = parseInt(hora.substr(0, 2));
        var hora_minutos = parseInt(hora.substr(3, 2));

        horas = horas + hora_horas;
        minutos = minutos + hora_minutos;
        while (minutos > 59) {
            horas = horas + 1;
            minutos = minutos - 60;
        }
    }

    var horas_totales = horas.toString();
    var minutos_totales = minutos.toString();

    if (horas_totales.length < 2) {
        horas_totales = "0" + horas_totales;
    }

    if (minutos_totales.length < 2) {
        minutos_totales = "0" + minutos_totales;
    }

    document.getElementById("cantidad_de_horas").value = horas_totales + ":" + minutos_totales;
}