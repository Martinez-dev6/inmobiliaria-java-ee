/*
 * Galería de la ficha de propiedad.
 *
 * - Clic en una miniatura -> la pone como imagen principal.
 * - Clic en la imagen principal -> abre el visor a pantalla completa.
 * - Dentro del visor: flechas < >, teclado (izquierda / derecha / Esc),
 *   deslizamiento táctil y clic en el fondo para cerrar.
 */
document.addEventListener('DOMContentLoaded', function () {

    const galeria = document.getElementById('galeria');
    const visor = document.getElementById('lightbox');
    if (!galeria || !visor) return;

    const miniaturas = Array.from(galeria.querySelectorAll('.galeria-miniatura'));
    const fuentes = miniaturas.map(function (m) { return m.dataset.src; });
    if (fuentes.length === 0) return;

    const imagenPrincipal = document.getElementById('galeriaImagen');
    const contadorGaleria = document.getElementById('galeriaContador');
    const imagenVisor = document.getElementById('lightboxImagen');
    const contadorVisor = document.getElementById('lightboxContador');
    const marco = document.getElementById('galeriaPrincipal');

    let indice = 0;

    // Con una sola foto no tiene sentido mostrar la tira ni las flechas.
    if (fuentes.length < 2) {
        galeria.classList.add('galeria-unica');
        visor.classList.add('lightbox-unica');
    }

    function mostrarEnGaleria(nuevo) {
        indice = (nuevo + fuentes.length) % fuentes.length;
        imagenPrincipal.src = fuentes[indice];
        miniaturas.forEach(function (m, i) {
            m.classList.toggle('activa', i === indice);
        });
        if (contadorGaleria) {
            contadorGaleria.textContent = (indice + 1) + ' / ' + fuentes.length;
        }
    }

    function mostrarEnVisor(nuevo) {
        mostrarEnGaleria(nuevo);
        imagenVisor.src = fuentes[indice];
        if (contadorVisor) {
            contadorVisor.textContent = (indice + 1) + ' / ' + fuentes.length;
        }
    }

    function abrirVisor() {
        mostrarEnVisor(indice);
        visor.classList.add('abierto');
        visor.setAttribute('aria-hidden', 'false');
        document.body.classList.add('sin-desplazamiento');
    }

    function cerrarVisor() {
        visor.classList.remove('abierto');
        visor.setAttribute('aria-hidden', 'true');
        document.body.classList.remove('sin-desplazamiento');
    }

    miniaturas.forEach(function (m, i) {
        m.addEventListener('click', function () { mostrarEnGaleria(i); });
    });

    marco.addEventListener('click', abrirVisor);
    marco.addEventListener('keydown', function (evento) {
        if (evento.key === 'Enter' || evento.key === ' ') {
            evento.preventDefault();
            abrirVisor();
        }
    });

    visor.addEventListener('click', function (evento) {
        const mover = evento.target.closest('[data-lightbox-mover]');
        if (mover) {
            mostrarEnVisor(indice + parseInt(mover.dataset.lightboxMover, 10));
            return;
        }
        // Clic en el fondo (no sobre la imagen) o en el botón de cerrar.
        if (evento.target.closest('[data-lightbox-cerrar]') || evento.target === visor) {
            cerrarVisor();
        }
    });

    document.addEventListener('keydown', function (evento) {
        if (!visor.classList.contains('abierto')) return;
        if (evento.key === 'Escape') cerrarVisor();
        if (evento.key === 'ArrowRight') mostrarEnVisor(indice + 1);
        if (evento.key === 'ArrowLeft') mostrarEnVisor(indice - 1);
    });

    // Deslizamiento en móvil.
    let inicioX = null;
    visor.addEventListener('touchstart', function (evento) {
        inicioX = evento.changedTouches[0].clientX;
    }, { passive: true });
    visor.addEventListener('touchend', function (evento) {
        if (inicioX === null) return;
        const recorrido = evento.changedTouches[0].clientX - inicioX;
        if (Math.abs(recorrido) > 50) {
            mostrarEnVisor(indice + (recorrido < 0 ? 1 : -1));
        }
        inicioX = null;
    }, { passive: true });

    mostrarEnGaleria(0);
});
