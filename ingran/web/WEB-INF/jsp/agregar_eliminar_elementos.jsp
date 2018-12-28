<script charset="UTF-8">
    $(document).ready(function () {
        var x = 0;
    <%=request.getParameter("indicex")%>;
        var maxField = 10;
        var addButton = $('.add_button');
        var wrapper = $('.field_wrapper');
        var y = 0;
    <%=request.getParameter("indicey")%>;
        var list = [];

        $(addButton).click(function () {
            if (x < maxField) {
                x++;
                var fieldHTML = '<div class="col-md-12"><div class="col-md-2"><div class="form-group"><input name="planilla_hora_detalle[' + y + '].fase.fase" onfocusout="seleccionarFase(' + y + ')" list="listFase" class="form-control" id="planilla_hora_detalle' + y + '.fase.fase" required="true" type="text" placeholder="Fase"></div></div><div class="col-md-2"><div class="form-group"><input name="planilla_hora_detalle[' + y + '].fase.nombre" class="form-control" id="planilla_hora_detalle' + y + '.fase.nombre" required="true" type="text" placeholder="Descripcion" readonly="true"></div></div><div class="col-md-2"><div class="form-group"><input name="planilla_hora_detalle[' + y + '].hora_inicio" class="form-control" id="planilla_hora_detalle' + y + '.hora_inicio" required="true" onchange="calc(' + y + ');" type="time"></div></div><div class="col-md-2"><div class="form-group"><input name="planilla_hora_detalle[' + y + '].hora_fin" class="form-control" id="planilla_hora_detalle' + y + '.hora_fin" required="true" onchange="calc(' + y + ');" type="time"></div></div><div class="col-md-2"><div class="form-group"><input name="planilla_hora_detalle[' + y + '].concepto.descripcion" list="listConcepto" class="form-control" id="planilla_hora_detalle' + y + '.concepto.descripcion" required="true" type="text" placeholder="Tipo de Hora"></div></div><div class="col-md-2"><div class="form-group"><input name="planilla_hora_detalle[' + y + '].cantidad_hora" class="form-control" id="planilla_hora_detalle' + y + '.cantidad_hora" required="true" type="text" readonly="readonly"></div></div></div>';
                //var fieldHTML = '<div class="col-md-12"><div class="col-md-1"><a href="javascript:void(0);" class="remove_button btn btn-danger" id="" title="Remove field">Eliminar</a></div><div class="col-md-11"><div class="col-md-2"><div class="form-group"><form:input path="planilla_hora_detalle[' + y + '].fase.fase" list="listFase" class="form-control" placeholder="Tipo de Hora" type="text" required="true"/><form:errors path="planilla_hora_detalle[' + y + '].fase.fase"/></div></div><div class="col-md-4"><div class="form-group"><form:input path="planilla_hora_detalle[' + y + '].fase.nombre" list="listDescripcionFase" class="form-control" placeholder="Tipo de Hora" type="text" required="true"/><form:errors path="planilla_hora_detalle[' + y + '].fase.nombre"/></div></div><div class="col-md-1"><div class="form-group"><form:input path="planilla_hora_detalle[' + y + '].hora_inicio" onchange="calc(0);" class="form-control" placeholder="Tipo de Hora" type="time" required="true"/><form:errors path="planilla_hora_detalle[' + y + '].hora_inicio"/></div></div><div class="col-md-1"><div class="form-group"><form:input path="planilla_hora_detalle[' + y + '].hora_fin" onchange="calc(0);" class="form-control" placeholder="Tipo de Hora" type="time" required="true"/><form:errors path="planilla_hora_detalle[' + y + '].hora_fin"/></div></div><div class="col-md-2"><div class="form-group"><form:input path="planilla_hora_detalle[' + y + '].tipo_de_hora.tipo_de_hora" class="form-control" placeholder="Tipo de Hora" type="text" required="true"/><form:errors path="planilla_hora_detalle[' + y + '].tipo_de_hora.tipo_de_hora"/></div></div><div class="col-md-2"><div class="form-group"><form:input path="planilla_hora_detalle[' + y + '].horas" class="form-control" placeholder="Horas" type="text" required="true" readonly="true"/><form:errors path="planilla_hora_detalle[' + y + '].horas"/></div></div></div></div>';
                $(wrapper).append(fieldHTML);
                list.push("#" + y);
                document.getElementById("lista").value = list;
                y++;
            }
        });
        $(wrapper).on('click', '.remove_button', function (e) {
            e.preventDefault();
            h = list.indexOf("#" + $(this).attr('id'));
            list.splice(h, 1);
            $($(this).parent('div')).parent('div').remove();
            //$(this).closest('div').remove();
            x--;
            document.getElementById("lista").value = list;
        });
    });
</script>