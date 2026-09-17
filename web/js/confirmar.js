/*
 * Confirmación para acciones que no se deshacen solas (dar de baja una
 * propiedad, cancelar una cita, rechazar una solicitud, quitar un rol...).
 *
 * En vez de repetir onsubmit="return confirm(...)" en cada formulario, basta
 * con poner data-confirmar="mensaje" en el <form>, o en el <button> cuando un
 * mismo formulario tiene varios botones y sólo uno es delicado.
 */
document.addEventListener('DOMContentLoaded', function () {

    // El botón pulsado no llega en el evento submit, así que se recuerda aquí.
    let ultimoBoton = null;

    document.addEventListener('click', function (evento) {
        const boton = evento.target.closest('button[type="submit"], input[type="submit"]');
        if (boton) {
            ultimoBoton = boton;
        }
    }, true);

    document.addEventListener('submit', function (evento) {
        const formulario = evento.target;
        const boton = (ultimoBoton && formulario.contains(ultimoBoton)) ? ultimoBoton : null;

        const mensaje = (boton && boton.dataset.confirmar) || formulario.dataset.confirmar;
        if (!mensaje) return;

        if (!window.confirm(mensaje)) {
            evento.preventDefault();
        }
    });
});
