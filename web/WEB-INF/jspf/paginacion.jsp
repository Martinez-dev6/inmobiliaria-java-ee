<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
    Paginador compartido. El controlador deja en el request:
      paginaActual    = página que se está viendo (1..n)
      totalPaginas    = número de páginas
      totalRegistros  = total de filas sin paginar
      urlPaginacion   = URL base ya con los filtros y lista para concatenar
                        "pagina=N" (termina en "?" o en "&")
    Si sólo hay una página no se pinta nada.
--%>
<c:if test="${totalPaginas > 1}">
    <c:set var="desde" value="${paginaActual - 2 < 1 ? 1 : paginaActual - 2}" />
    <c:set var="hasta" value="${paginaActual + 2 > totalPaginas ? totalPaginas : paginaActual + 2}" />

    <nav class="paginacion-hogar" aria-label="Paginación de resultados">
        <span class="paginacion-resumen">
            Página ${paginaActual} de ${totalPaginas} · ${totalRegistros} registro(s)
        </span>

        <div class="paginacion-botones">
            <a class="pagina ${paginaActual == 1 ? 'desactivada' : ''}"
               href="${urlPaginacion}pagina=${paginaActual - 1}" aria-label="Página anterior">
                <i class="bi bi-chevron-left"></i>
            </a>

            <c:if test="${desde > 1}">
                <a class="pagina" href="${urlPaginacion}pagina=1">1</a>
                <c:if test="${desde > 2}"><span class="pagina-puntos">…</span></c:if>
            </c:if>

            <c:forEach var="n" begin="${desde}" end="${hasta}">
                <a class="pagina ${n == paginaActual ? 'activa' : ''}"
                   href="${urlPaginacion}pagina=${n}"
                   ${n == paginaActual ? 'aria-current="page"' : ''}>${n}</a>
            </c:forEach>

            <c:if test="${hasta < totalPaginas}">
                <c:if test="${hasta < totalPaginas - 1}"><span class="pagina-puntos">…</span></c:if>
                <a class="pagina" href="${urlPaginacion}pagina=${totalPaginas}">${totalPaginas}</a>
            </c:if>

            <a class="pagina ${paginaActual == totalPaginas ? 'desactivada' : ''}"
               href="${urlPaginacion}pagina=${paginaActual + 1}" aria-label="Página siguiente">
                <i class="bi bi-chevron-right"></i>
            </a>
        </div>
    </nav>
</c:if>
