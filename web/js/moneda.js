/*
 * Campos de dinero legibles: el usuario escribe y ve 100.000.000 en vez de
 * 100000000, pero al servidor sigue viajando el número limpio.
 *
 * Uso: <input type="text" inputmode="numeric" data-moneda name="precio" value="...">
 * Antes de enviar el formulario se quitan los puntos, así que el controlador
 * recibe exactamente lo mismo que antes y no hay que tocar el parseo en Java.
 */
document.addEventListener('DOMContentLoaded', function () {

    const campos = Array.from(document.querySelectorAll('[data-moneda]'));
    if (campos.length === 0) return;

    function soloDigitos(texto) {
        return (texto || '').replace(/\D/g, '');
    }

    function conSeparadores(digitos) {
        if (!digitos) return '';
        // Se quitan los ceros de más a la izquierda ("007" -> "7").
        const limpio = digitos.replace(/^0+(?=\d)/, '');
        return limpio.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    }

    campos.forEach(function (campo) {
        campo.value = conSeparadores(soloDigitos(campo.value));

        campo.addEventListener('input', function () {
            // Se recuerda cuántos dígitos había a la izquierda del cursor para
            // devolverlo a su sitio después de reformatear.
            const posicion = campo.selectionStart;
            const digitosAntes = soloDigitos(campo.value.slice(0, posicion)).length;

            campo.value = conSeparadores(soloDigitos(campo.value));

            let contados = 0;
            let nuevaPosicion = campo.value.length;
            for (let i = 0; i < campo.value.length; i++) {
                if (/\d/.test(campo.value[i])) contados++;
                if (contados === digitosAntes) { nuevaPosicion = i + 1; break; }
            }
            if (digitosAntes === 0) nuevaPosicion = 0;
            campo.setSelectionRange(nuevaPosicion, nuevaPosicion);
        });

        const formulario = campo.form;
        if (formulario && !formulario.dataset.monedaEnlazada) {
            formulario.dataset.monedaEnlazada = 'si';
            formulario.addEventListener('submit', function () {
                formulario.querySelectorAll('[data-moneda]').forEach(function (c) {
                    c.value = soloDigitos(c.value);
                });
            });
        }
    });
});
