function seleccionarFase(indice) {
    var dato = document.getElementById('planilla_hora_detalle' + indice + '.fase.fase').value;
    var value = $('#listFase [value="' + dato + '"]').data('ejemplo');

    document.getElementById('planilla_hora_detalle' + indice + '.fase.nombre').value = value;
}