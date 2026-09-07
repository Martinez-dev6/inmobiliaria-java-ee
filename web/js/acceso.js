document.addEventListener('DOMContentLoaded', function () {
    const contenedor = document.getElementById('contenedorAcceso');

    const activar = () => contenedor.classList.add('derecha-activa');
    const desactivar = () => contenedor.classList.remove('derecha-activa');

    document.getElementById('btnIrRegistro').addEventListener('click', activar);
    document.getElementById('btnIrLogin').addEventListener('click', desactivar);
    document.getElementById('linkIrRegistroMovil').addEventListener('click', function (e) {
        e.preventDefault();
        activar();
    });
    document.getElementById('linkIrLoginMovil').addEventListener('click', function (e) {
        e.preventDefault();
        desactivar();
    });

    if (contenedor.dataset.animarRegreso === 'true') {
        setTimeout(desactivar, 300);
    }
});