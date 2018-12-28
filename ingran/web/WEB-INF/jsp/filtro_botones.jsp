
<div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
    <div class="btn-group" role="group" aria-label="First Group">
        <input type="button" class="btn btn-secondary" value="Quitar Cuadricula" onclick="tf.RemoveGrid();" />
        <input type="button" class="btn btn-secondary" value="Poner Cuadricula" onclick="tf.AddGrid();" />
    </div>
    <div class="btn-group" role="group" aria-label="Second Group">
        <input type="button" class="btn btn-secondary" value="Agregar Paginado" 
               onclick="
                           if (tf.paging)
                               return;
                           tf.AddPaging(true);" />
        <input type="button" class="btn btn-secondary" value="Quitar Paginado" 
               onclick="
                           if (!tf.paging)
                               return;
                           tf.paging = false; //behaviour is removed here
                           tf.RemovePaging();
                           tf.Filter();" />
    </div>
    <div class="btn-group" role="group" aria-label="Third Group">
        <input type="button" class="btn btn-secondary" value="Quitar Fondo Entre Filas" onclick="tf.alternateBgs = false;
                    tf.RemoveAlternateRows();" />
        <input type="button" class="btn btn-secondary" value="Poner Fondo Entre Filas" onclick="tf.alternateBgs = true;
                    tf.SetAlternateRows();" />
    </div>
</div>