<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hogar 360</title>

        <!-- Google Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500&family=Poppins:wght@500;600;700&display=swap" rel="stylesheet">

        <!-- Bootstrap 5 (CDN) -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
         <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

        <!-- Estilos propios -->
        <link href="css/estilo.css" rel="stylesheet">
    </head>
    <body>

          <!-- Barra de navegación -->
        <nav class="navbar navbar-expand-lg navbar-hogar">
            <div class="container">
                <a class="navbar-brand fw-bold fs-4" href="index.jsp">
                    Hogar <span style="color:var(--azul);">3</span><span style="color:var(--ambar);">6</span><span style="color:var(--oscuro);">0</span>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuPrincipal">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="menuPrincipal">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" href="index.jsp">Inicio</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="catalogo">Catálogo</a>
                        </li>
                    </ul>
                    <div class="d-flex gap-2">
                        <a href="acceso.jsp" class="btn btn-outline-claro">Iniciar sesión</a>
                        <a href="acceso.jsp?panelActivo=registro" class="btn btn-coral">Registrarse</a>
                    </div>
                </div>
            </div>
        </nav>

                <!-- Hero -->
        <section class="py-5">
            <div class="container">
                <div class="row align-items-center g-5">
                    <div class="col-lg-6">
                        <h1 class="display-5 mb-3" style="color:var(--oscuro);">
                            Tu próximo hogar está más cerca de lo que crees
                        </h1>
                        <p class="lead mb-4" style="color:#5A6472;">
                            Explora casas, apartamentos, oficinas y terrenos verificados,
                            y agenda tu visita en minutos.
                        </p>
                        <div class="d-flex gap-3 mb-4">
                            <a href="catalogo" class="btn btn-coral btn-lg">Ver catálogo</a>
                            <a href="acceso.jsp?panelActivo=registro" class="btn btn-outline-secondary btn-lg">Crear cuenta</a>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <img src="https://images.pexels.com/photos/7587880/pexels-photo-7587880.jpeg?auto=compress&cs=tinysrgb&w=900"
                             alt="Casa moderna"
                             class="img-fluid rounded-4 shadow-sm w-100"
                             style="object-fit:cover; max-height:420px;">
                    </div>
                </div>

                <!-- Buscador rápido -->
                <div class="card border-0 shadow-sm rounded-4 p-4 mt-4" style="margin-top:-40px;">
                    <form action="catalogo" method="get" class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label class="form-label fw-semibold">Ciudad</label>
                            <select name="ciudad" class="form-select">
                                <option selected>Cualquier ciudad</option>
                                <option>Bogotá</option>
                                <option>Medellín</option>
                                <option>Bucaramanga</option>
                                <option>Cali</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-semibold">Tipo de propiedad</label>
                            <select name="tipo" class="form-select">
                                <option selected>Cualquier tipo</option>
                                <option>Casa</option>
                                <option>Apartamento</option>
                                <option>Oficina</option>
                                <option>Local</option>
                                <option>Terreno</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-semibold">Presupuesto máx.</label>
                            <input type="number" name="precioMax" class="form-control" placeholder="Ej. 300000000">
                        </div>
                        <div class="col-md-1 d-grid">
                            <button type="submit" class="btn btn-coral">
                                <i class="bi bi-search"></i> Ir
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </section>

        <!-- Propiedades destacadas -->
        <section class="py-5">
            <div class="container">
                <h2 class="mb-4">Propiedades destacadas</h2>
                <div class="row g-4">

                    <div class="col-md-4">
                        <div class="card h-100 border-0 shadow-sm rounded-4 overflow-hidden">
                            <img src="https://images.pexels.com/photos/7587880/pexels-photo-7587880.jpeg?auto=compress&cs=tinysrgb&w=600"
                                 class="card-img-top" alt="Casa moderna en Bucaramanga" style="height:200px; object-fit:cover;">
                            <div class="card-body">
                                <span class="badge mb-2" style="background-color:var(--azul);">Casa</span>
                                <h5 class="card-title">Casa campestre moderna</h5>
                                <p class="card-text text-muted mb-1">Bucaramanga, Santander</p>
                                <p class="card-text fw-bold fs-5">$ 480.000.000</p>
                                <a href="detalle-propiedad" class="btn btn-outline-claro w-100">Ver detalle</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card h-100 border-0 shadow-sm rounded-4 overflow-hidden">
                            <img src="https://images.pexels.com/photos/6588599/pexels-photo-6588599.jpeg?auto=compress&cs=tinysrgb&w=600"
                                 class="card-img-top" alt="Apartamento moderno en Medellín" style="height:200px; object-fit:cover;">
                            <div class="card-body">
                                <span class="badge mb-2" style="background-color:var(--azul);">Apartamento</span>
                                <h5 class="card-title">Apartamento vista panorámica</h5>
                                <p class="card-text text-muted mb-1">Medellín, Antioquia</p>
                                <p class="card-text fw-bold fs-5">$ 320.000.000</p>
                                <a href="detalle-propiedad" class="btn btn-outline-claro w-100">Ver detalle</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card h-100 border-0 shadow-sm rounded-4 overflow-hidden">
                            <img src="https://images.pexels.com/photos/37293743/pexels-photo-37293743.jpeg?auto=compress&cs=tinysrgb&w=600"
                                 class="card-img-top" alt="Oficina moderna en Bogotá" style="height:200px; object-fit:cover;">
                            <div class="card-body">
                                <span class="badge mb-2" style="background-color:var(--azul);">Oficina</span>
                                <h5 class="card-title">Oficina corporativa</h5>
                                <p class="card-text text-muted mb-1">Bogotá, Cundinamarca</p>
                                <p class="card-text fw-bold fs-5">$ 210.000.000</p>
                                <a href="detalle-propiedad" class="btn btn-outline-claro w-100">Ver detalle</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </section>
        
        
                <!-- Footer -->
        <footer class="py-5 mt-4" style="background-color:var(--oscuro);">
            <div class="container">
                <div class="row g-4">
                    <div class="col-md-4">
                        <h5 class="text-white">
                            Hogar <span style="color:var(--azul);">3</span><span style="color:var(--ambar);">6</span><span class="text-white">0</span>
                        </h5>
                        <p class="text-white-50">
                            Encuentra, publica y gestiona propiedades de forma simple y segura.
                        </p>
                    </div>
                    <div class="col-md-4">
                        <h6 class="text-white mb-3">Enlaces rápidos</h6>
                        <ul class="list-unstyled">
                            <li><a href="index.jsp" class="text-white-50 text-decoration-none">Inicio</a></li>
                            <li><a href="catalogo" class="text-white-50 text-decoration-none">Catálogo</a></li>
                            <li><a href="acceso.jsp" class="text-white-50 text-decoration-none">Iniciar sesión</a></li>
                            <li><a  accesskey=""href="acceso.jsp?panelActivo=registro" class="text-white-50 text-decoration-none">Registrarse</a></li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                        <h6 class="text-white mb-3">Contacto</h6>
                        <p class="text-white-50 mb-1">Bucaramanga, Santander</p>
                        <p class="text-white-50 mb-1">contacto@hogar360.com</p>
                        <p class="text-white-50">+57 300 000 0000</p>
                    </div>
                </div>
                <hr class="border-secondary my-4">
                <p class="text-white-50 text-center mb-0" style="font-size:0.85rem;">
                    &copy; 2026 Hogar 360. Proyecto académico — Programación Java, UTS.
                </p>
            </div>
        </footer>
        
                        
        <!-- Bootstrap JS (CDN) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/transicion.js"></script>
    </body>
</html>