/*
 * Comportamiento del shell: abrir y cerrar la barra lateral en pantallas
 * pequeñas, donde deja de ser fija y pasa a deslizarse sobre el contenido.
 */
document.addEventListener('DOMContentLoaded', function () {

    const lateral = document.getElementById('barraLateral');
    const velo = document.getElementById('veloLateral');
    const boton = document.getElementById('botonMenu');
    if (!lateral || !velo || !boton) return;

    function abrir() {
        lateral.classList.add('abierta');
        velo.classList.add('visible');
    }

    function cerrar() {
        lateral.classList.remove('abierta');
        velo.classList.remove('visible');
    }

    boton.addEventListener('click', function () {
        if (lateral.classList.contains('abierta')) {
            cerrar();
        } else {
            abrir();
        }
    });

    velo.addEventListener('click', cerrar);

    document.addEventListener('keydown', function (evento) {
        if (evento.key === 'Escape') cerrar();
    });
});
