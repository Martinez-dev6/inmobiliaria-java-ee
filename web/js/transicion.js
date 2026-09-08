document.addEventListener('DOMContentLoaded', function () {
    document.body.classList.add('pagina-visible');
});

// Si el navegador restaura la página desde su caché al usar "Atrás",
// DOMContentLoaded no se vuelve a disparar — sin esto, la página
// podría quedar invisible (opacity: 0) al volver con el botón Atrás.
window.addEventListener('pageshow', function (evento) {
    if (evento.persisted) {
        document.body.classList.add('pagina-visible');
    }
});

document.addEventListener('click', function (evento) {
    const enlace = evento.target.closest('a[href]');
    if (!enlace) return;

    const href = enlace.getAttribute('href');

    const esExterno = /^(https?:)?\/\//i.test(href) || href.startsWith('mailto:') || href.startsWith('tel:');
    const esAncla = href.startsWith('#');
    const abreEnPestanaNueva = enlace.target === '_blank';
    const clicEspecial = evento.metaKey || evento.ctrlKey || evento.shiftKey || evento.button === 1;

    if (!href || esExterno || esAncla || abreEnPestanaNueva || clicEspecial) {
        return; // se deja la navegación normal del navegador, sin transición
    }

    evento.preventDefault();
    document.body.classList.remove('pagina-visible');
    setTimeout(function () {
        window.location.href = href;
    }, 220);
});