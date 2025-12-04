/**
 * Script principal para la interfaz de usuario
 */

// Variable para almacenar el perfil actual
let perfilActual = null;

document.addEventListener('DOMContentLoaded', function() {
    cargarDatos();
    setupFormListener();
    setupFormularioPerfil();
});

/**
 * Configura el listener para la carga de archivos de foto
 */
function setupFormularioPerfil() {
    const fotoInput = document.getElementById('foto-input');
    const perfilForm = document.getElementById('perfil-form');
    
    if (fotoInput) {
        fotoInput.addEventListener('change', manejarCambioFoto);
    }
    
    if (perfilForm) {
        perfilForm.addEventListener('submit', manejarSubmitPerfil);
    }
}

/**
 * Carga el perfil y las habilidades al iniciar
 */
async function cargarDatos() {
    try {
        await cargarPerfil();
        await cargarHabilidades();
    } catch (error) {
        console.error('Error al cargar datos:', error);
        mostrarError('Error al cargar los datos');
    }
}

/**
 * Carga y muestra el perfil
 */
async function cargarPerfil() {
    try {
        const perfil = await obtenerPerfil();
        mostrarPerfil(perfil);
    } catch (error) {
        console.error('Error al cargar perfil:', error);
        document.getElementById('perfil-content').innerHTML = 
            '<p style="color: #ef4444;">No hay perfil disponible.</p>';
    }
}

/**
 * Muestra el perfil en la interfaz
 */
function mostrarPerfil(perfil) {
    const perfilContent = document.getElementById('perfil-content');
    
    // Guardar el perfil actual en memoria
    perfilActual = perfil;
    
    if (!perfil) {
        perfilContent.innerHTML = '<p>No hay perfil disponible.</p>';
        return;
    }

    let html = `<div class="perfil-display">`;
    
    if (perfil.fotoPerfil) {
        html += `<div class="foto-perfil-display">
                    <img src="${perfil.fotoPerfil}" alt="Foto de perfil">
                 </div>`;
    }
    
    html += `<div class="perfil-details">
                <h3>${perfil.nombre || 'Sin nombre'}</h3>
                <div class="perfil-info">`;

    if (perfil.bio) {
        html += `<p><strong>Bio:</strong> ${perfil.bio}</p>`;
    }

    if (perfil.experiencia) {
        html += `<p><strong>Experiencia:</strong> ${perfil.experiencia}</p>`;
    }

    if (perfil.contacto) {
        html += `<p><strong>Contacto:</strong> ${perfil.contacto}</p>`;
    }

    html += `</div></div></div>`;
    perfilContent.innerHTML = html;
}
/**
 * Carga y muestra las habilidades
 */
async function cargarHabilidades() {
    try {
        const habilidades = await obtenerHabilidades();
        mostrarHabilidades(habilidades);
    } catch (error) {
        console.error('Error al cargar habilidades:', error);
        document.getElementById('habilidades-content').innerHTML = 
            '<p style="color: #ef4444;">Error al cargar las habilidades.</p>';
    }
}

/**
 * Muestra las habilidades en la interfaz
 */
function mostrarHabilidades(habilidades) {
    const habilidadesContent = document.getElementById('habilidades-content');

    if (!habilidades || habilidades.length === 0) {
        habilidadesContent.innerHTML = '<p>No hay habilidades registradas.</p>';
        return;
    }

    let html = '';

    habilidades.forEach(habilidad => {
        const nivelClase = habilidad.nivel.toLowerCase();
        html += `
            <div class="habilidad-card">
                <h4>${habilidad.nombre}</h4>
                <div class="habilidad-info">
                    <p><strong>Nivel:</strong> 
                        <span class="nivel-badge ${nivelClase}">${habilidad.nivel}</span>
                    </p>
                    <p><strong>Experiencia:</strong> ${habilidad.experienciaAnios} años</p>
                    ${habilidad.descripcion ? `<p><strong>Descripción:</strong> ${habilidad.descripcion}</p>` : ''}
                </div>
                <div class="habilidad-actions">
                    <button class="btn btn-secondary btn-small" onclick="editarHabilidad('${habilidad.id}')">
                        Editar
                    </button>
                    <button class="btn btn-danger btn-small" onclick="eliminarHabilidadUI('${habilidad.id}')">
                        Eliminar
                    </button>
                </div>
            </div>
        `;
    });

    habilidadesContent.innerHTML = html;
}

/**
 * Configura el listener del formulario
 */
function setupFormListener() {
    const form = document.getElementById('habilidad-form');
    form.addEventListener('submit', manejarSubmitFormulario);
}

/**
 * Maneja el envío del formulario
 */
async function manejarSubmitFormulario(event) {
    event.preventDefault();

    const id = document.getElementById('habilidad-id').value;
    const nombre = document.getElementById('nombre').value;
    const nivel = document.getElementById('nivel').value;
    const experiencia = parseInt(document.getElementById('experiencia').value);
    const descripcion = document.getElementById('descripcion').value;

    const habilidad = {
        nombre,
        nivel,
        experienciaAnios: experiencia,
        descripcion
    };

    try {
        if (id) {
            // Modo edición: actualizar habilidad existente
            habilidad.id = id;
            const resultado = await actualizarHabilidad(habilidad);
            mostrarExito('¡Habilidad actualizada correctamente!');
        } else {
            // Modo creación: crear nueva habilidad
            if (isNaN(experiencia) || experiencia < 0 || experiencia > 99) {
                mostrarError('Por favor ingresa un número válido de años de experiencia (0-99).');
                return;
            }
            const resultado = await crearHabilidad(habilidad);
            mostrarExito('¡Habilidad creada correctamente!');
        }
        
        limpiarFormulario();
        await cargarHabilidades();
    } catch (error) {
        console.error('Error al guardar habilidad:', error);
        mostrarError('Error al guardar la habilidad. Por favor intenta de nuevo.');
    }
}

/**
 * Carga una habilidad en el formulario para editar
 */
async function editarHabilidad(id) {
    try {
        const habilidad = await obtenerHabilidad(id);
        
        // Llenar el formulario con los datos de la habilidad
        document.getElementById('habilidad-id').value = habilidad.id;
        document.getElementById('nombre').value = habilidad.nombre;
        document.getElementById('nivel').value = habilidad.nivel;
        document.getElementById('experiencia').value = habilidad.experienciaAnios;
        document.getElementById('descripcion').value = habilidad.descripcion || '';
        
        // Cambiar título y botón del formulario
        document.getElementById('formulario-titulo').textContent = 'Editar Habilidad';
        document.getElementById('btn-submit').textContent = 'Actualizar Habilidad';
        document.getElementById('btn-cancelar').style.display = 'inline-block';
        
        // Scroll al formulario
        document.getElementById('formulario-section').scrollIntoView({ behavior: 'smooth' });
    } catch (error) {
        console.error('Error al obtener habilidad para editar:', error);
        mostrarError('Error al cargar la habilidad para editar');
    }
}

/**
 * Cancela la edición y limpia el formulario
 */
function cancelarEdicion() {
    limpiarFormulario();
}

/**
 * Limpia el formulario y lo prepara para crear una nueva habilidad
 */
function limpiarFormulario() {
    document.getElementById('habilidad-form').reset();
    document.getElementById('habilidad-id').value = '';
    document.getElementById('formulario-titulo').textContent = 'Agregar Nueva Habilidad';
    document.getElementById('btn-submit').textContent = 'Agregar Habilidad';
    document.getElementById('btn-cancelar').style.display = 'none';
}

/**
 * Elimina una habilidad desde la UI
 */
async function eliminarHabilidadUI(id) {
    if (confirm('¿Estás seguro de que deseas eliminar esta habilidad?')) {
        try {
            const resultado = await eliminarHabilidad(id);
            mostrarExito('Habilidad eliminada correctamente');
            await cargarHabilidades();
        } catch (error) {
            console.error('Error al eliminar habilidad:', error);
            mostrarError('Error al eliminar la habilidad');
        }
    }
}

/**
 * Muestra mensaje de éxito
 */
function mostrarExito(mensaje) {
    mostrarMensaje(mensaje, 'success');
}

/**
 * Muestra mensaje de error
 */
function mostrarError(mensaje) {
    mostrarMensaje(mensaje, 'error');
}

/**
 * Muestra un mensaje temporal
 */
function mostrarMensaje(mensaje, tipo) {
    const alertClass = tipo === 'success' ? 'alert-success' : 'alert-error';
    const alertHTML = `<div class="alert ${alertClass}">${mensaje}</div>`;
    
    // Insertar antes del main
    const main = document.querySelector('main');
    const alertElement = document.createElement('div');
    alertElement.innerHTML = alertHTML;
    main.parentNode.insertBefore(alertElement.firstChild, main);

    // Eliminar después de 5 segundos
    setTimeout(() => {
        const alert = document.querySelector('.alert');
        if (alert) {
            alert.remove();
        }
    }, 5000);
}

/**
 * Muestra el formulario de edición de perfil
 */
function mostrarFormularioPerfil() {
    const section = document.getElementById('editar-perfil-section');
    
    // Primero quitar display none
    section.style.display = 'block';
    
    // Forzar un reflow para que el navegador registre el cambio
    section.offsetHeight;
    
    // Ahora agregar la clase show para activar la transición
    requestAnimationFrame(() => {
        section.classList.add('show');
    });
    
    // Scroll suave hacia el formulario
    setTimeout(() => {
        section.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }, 100);
}

function ocultarFormularioPerfil() {
    const section = document.getElementById('editar-perfil-section');
    section.classList.remove('show');
    
    // Ocultar completamente después de la animación
    setTimeout(() => {
        section.style.display = 'none';
        document.getElementById('perfil-form').reset();
        const fotoImg = document.getElementById('foto-img');
        const fotoPlaceholder = document.getElementById('foto-placeholder');
        const btnEliminar = document.getElementById('btn-eliminar-foto');
        
        if (fotoImg) fotoImg.style.display = 'none';
        if (fotoPlaceholder) fotoPlaceholder.style.display = 'block';
        if (btnEliminar) btnEliminar.style.display = 'none';
    }, 400); // Debe coincidir con la duración de la transición CSS
}
/** Auto resize textarea */
function autoResize(textarea) {
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
}


/**
 * Maneja el cambio de foto de perfil
 */
function manejarCambioFoto(event) {
    const file = event.target.files[0];
    
    if (!file) {
        return;
    }
    
    // Validar que sea una imagen
    if (!file.type.startsWith('image/')) {
        mostrarError('Por favor selecciona un archivo de imagen');
        return;
    }
    
    // Validar tamaño máximo (5MB)
    const maxSize = 5 * 1024 * 1024;
    if (file.size > maxSize) {
        mostrarError('La imagen no debe pesar más de 5MB');
        event.target.value = '';
        return;
    }
    
    // Convertir a Base64
    const reader = new FileReader();
    reader.onload = function(e) {
        const base64 = e.target.result;
        
        // Mostrar preview
        document.getElementById('foto-img').src = base64;
        document.getElementById('foto-img').style.display = 'block';
        document.getElementById('foto-placeholder').style.display = 'none';
        document.getElementById('btn-eliminar-foto').style.display = 'inline-block';
        
        // Guardar la imagen en memoria
        perfilActual.fotoPerfil = base64;
    };
    reader.readAsDataURL(file);
}

/**
 * Elimina la foto de perfil
 */
function eliminarFotoPerfil() {
    document.getElementById('foto-input').value = '';
    document.getElementById('foto-img').src = '';
    document.getElementById('foto-img').style.display = 'none';
    document.getElementById('foto-placeholder').style.display = 'block';
    document.getElementById('btn-eliminar-foto').style.display = 'none';
    
    if (perfilActual) {
        perfilActual.fotoPerfil = '';
    }
}

/**
 * Maneja el envío del formulario de perfil
 */
async function manejarSubmitPerfil(event) {
    event.preventDefault();
    
    const nombre = document.getElementById('perfil-nombre').value;
    const bio = document.getElementById('perfil-bio').value;
    const experiencia = document.getElementById('perfil-experiencia').value;
    const contacto = document.getElementById('perfil-contacto').value;
    
    const perfil = {
        nombre,
        bio,
        experiencia,
        contacto,
        fotoPerfil: perfilActual?.fotoPerfil || '',
        habilidades: perfilActual?.habilidades || []
    };
    
    try {
        const resultado = await actualizarPerfil(perfil);
        mostrarExito('Perfil actualizado correctamente');
        
        // Recargar perfil
        await cargarPerfil();
        ocultarFormularioPerfil();
    } catch (error) {
        console.error('Error al actualizar perfil:', error);
        mostrarError('Error al actualizar el perfil');
    }
}
// Crear contenedor de notificaciones al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    if (!document.querySelector('.notification-container')) {
        const container = document.createElement('div');
        container.className = 'notification-container';
        document.body.appendChild(container);
    }
});

/**
 * Muestra una notificación pop-up
 * @param {string} mensaje - Mensaje a mostrar
 * @param {string} tipo - Tipo de notificación: 'success', 'error', 'info'
 * @param {number} duracion - Duración en milisegundos (default: 5000)
 */
function mostrarNotificacion(mensaje, tipo = 'info', duracion = 5000) {
    const container = document.querySelector('.notification-container') || crearContenedorNotificaciones();
    
    // Crear elemento de notificación
    const notification = document.createElement('div');
    notification.className = `notification ${tipo}`;
    
    // Determinar el icono según el tipo
    let icono = '';
    switch(tipo) {
        case 'success':
            icono = '✓';
            break;
        case 'error':
            icono = '✕';
            break;
        case 'info':
            icono = 'i';
            break;
    }
    
    // Construir el HTML de la notificación
    notification.innerHTML = `
        <div class="notification-icon">${icono}</div>
        <div class="notification-content">${mensaje}</div>
        <button class="notification-close" onclick="cerrarNotificacion(this)">×</button>
        <div class="notification-progress"></div>
    `;
    
    // Agregar al contenedor
    container.appendChild(notification);
    
    // Forzar reflow para activar la animación
    notification.offsetHeight;
    
    // Activar animación de entrada
    requestAnimationFrame(() => {
        notification.classList.add('show');
    });
    
    // Auto-cerrar después de la duración especificada
    const timeout = setTimeout(() => {
        cerrarNotificacion(notification);
    }, duracion);
    
    // Guardar el timeout en el elemento para poder cancelarlo si es necesario
    notification.dataset.timeout = timeout;
    
    return notification;
}
/**
 * Cierra una notificación
 * @param {HTMLElement|Event} elemento - Elemento de notificación o evento del botón
 */
function cerrarNotificacion(elemento) {
    const notification = elemento instanceof HTMLElement && elemento.classList.contains('notification') 
        ? elemento 
        : elemento.parentElement;
    
    if (!notification) return;
    
    // Cancelar el timeout de auto-cierre si existe
    if (notification.dataset.timeout) {
        clearTimeout(parseInt(notification.dataset.timeout));
    }
    
    // Activar animación de salida
    notification.classList.remove('show');
    notification.classList.add('hide');
    
    // Remover del DOM después de la animación
    setTimeout(() => {
        notification.remove();
    }, 300);
}

/**
 * Crea el contenedor de notificaciones si no existe
 */
function crearContenedorNotificaciones() {
    const container = document.createElement('div');
    container.className = 'notification-container';
    document.body.appendChild(container);
    return container;
}

/**
 * Muestra mensaje de éxito
 */
function mostrarExito(mensaje, duracion = 5000) {
    mostrarNotificacion(mensaje, 'success', duracion);
}

/**
 * Muestra mensaje de error
 */
function mostrarError(mensaje, duracion = 5000) {
    mostrarNotificacion(mensaje, 'error', duracion);
}

/**
 * Muestra mensaje informativo
 */
function mostrarInfo(mensaje, duracion = 5000) {
    mostrarNotificacion(mensaje, 'info', duracion);
}

/**
 * Limpia todas las notificaciones activas
 */
function limpiarNotificaciones() {
    const container = document.querySelector('.notification-container');
    if (container) {
        const notifications = container.querySelectorAll('.notification');
        notifications.forEach(notification => {
            cerrarNotificacion(notification);
        });
    }
}