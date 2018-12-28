var url = "";
var url_sin_parametros = "";
var name_url = [];
var value_url = [];

function agregar_desde_lista(id, name, lista, recargar) {
    var dato = document.getElementById(id).value;
    var value = $('#' + lista + ' [value="' + dato + '"]').data('ejemplo');

    agregar_gets(name, value);
    actualizar(recargar);
}

function agregar(id, name, recargar) {
    var value = document.getElementById(id).value;

    agregar_gets(name, value);

    actualizar(recargar);
}

function actualizar(recargar) {
    if (recargar) {
        var redireccionamiento = url_sin_parametros + "?";
        for (var i = 0; i < name_url.length; i++) {
            if (i === 0) {
                redireccionamiento = redireccionamiento + name_url[i] + "=" + value_url[i];
            } else {
                redireccionamiento = redireccionamiento + "&" + name_url[i] + "=" + value_url[i];
            }
        }
        //funciona como una redirección HTTP
        //window.location.replace(redireccionamiento);
        //funciona como si dieras clic en un enlace
        window.location.href = redireccionamiento;
    }
}

function agregar_gets(name_nuevo, value_nuevo) {
    //Obtener la URL
    if (url === "") {
        url = window.location.href;
        if (tieneGetsElUrl()) {
            //Separacion de URL y parametros de GETS
            var resultado_url = url.split("?");
            url_sin_parametros = resultado_url[0];
            //Separacion de parametros de GETS
            var parametros = resultado_url[1].split("&");
            //Separacion de parametros y valores
            for (i = 0; i < parametros.length; i++) {
                var name_value = parametros[i].split("=");
                name_url.push(name_value[0]);
                value_url.push(name_value[1]);
            }
        } else {
            url_sin_parametros = url;
        }
    }

    if (tieneGetsElUrl()) {
        if (existe(name_nuevo)) {
            cambiar(name_nuevo, value_nuevo);
        } else {
            name_url.push(name_nuevo);
            value_url.push(value_nuevo);
        }
    } else {
        name_url.push(name_nuevo);
        value_url.push(value_nuevo);
    }
}

function tieneGetsElUrl() {
    var exito = false;
    if (url.indexOf("?") !== -1) {
        exito = true;
    }
    return exito;
}

function existe(name_nuevo) {
    var existe = false;
    for (var i = 0; i < name_url.length; i++) {
        if (name_url[i] === name_nuevo) {
            existe = true;
        }
    }
    return existe;
}

function cambiar(name_nuevo, value_nuevo) {
    for (var i = 0; i < name_url.length; i++) {
        if (name_url[i] === name_nuevo) {
            value_url[i] = value_nuevo;
        }
    }
}